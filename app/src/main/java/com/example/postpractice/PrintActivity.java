package com.example.postpractice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PrintActivity extends AppCompatActivity {
    private Button returnButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView Viewtitle, Viewdate, Viewcontent, ViewGender, ViewPrice, ViewPeriod, ViewLocation;
    private String title, date, content, gender, period, price;
    private Long time;
    private String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrintActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
        Viewtitle = findViewById(R.id.Viewtitle);
        Viewdate = findViewById(R.id.Viewdate);
        Viewcontent = findViewById(R.id.Viewcontent);
        ViewGender = findViewById(R.id.Viewgender);
        ViewPeriod = findViewById(R.id.Viewperiod);
        ViewPrice = findViewById(R.id.Viewprice);

        Intent getIntent = getIntent();
        docId = getIntent.getStringExtra("docId");
        db.collection("Board").document(docId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            title = document.getString("title") + " (" + document.getString("location")+")";
                            time = document.getLong("date");
                            date = TimeString.formatTimeString(time);
                            content = document.getString("content");
                            gender = document.getString("gender");
                            period = document.getString("period");
                            price = document.getString("price");

                            Viewtitle.setText(title);
                            Viewdate.setText(date);
                            Viewcontent.setText(content);
                            ViewPeriod.setText(period);
                            ViewPrice.setText(price);
                            ViewGender.setText(gender);
                        }
                    }
                });

    }
}
