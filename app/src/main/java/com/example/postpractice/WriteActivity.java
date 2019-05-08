package com.example.postpractice;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class WriteActivity extends AppCompatActivity {
    Editor editor;
    Button button, cancelButton;
    EditText editText, priceText;
    Spinner spinPeriod, spinLocation;
    RadioGroup radioGroup;
    int selected;
    String gender, price, period, location;
    String title;
    String content;
    FirebaseFirestore db;
    Board board;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        editText = findViewById(R.id.title);
        priceText = findViewById(R.id.price);
        spinPeriod = findViewById(R.id.spinPeriod);
        spinLocation = findViewById(R.id.spinLocation);
        radioGroup = findViewById(R.id.radioGender);

        editor = findViewById(R.id.editor);
        editor.setEditorListener(new EditorListener() {
            @Override
            public void onTextChanged(EditText editText, Editable text) {
                content = text.toString();
            }

            @Override
            public void onUpload(Bitmap image, String uuid) {

            }

            @Override
            public View onRenderMacro(String name, Map<String, Object> props, int index) {
                return null;
            }
        });
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editText.getText().toString();
                long date = System.currentTimeMillis();
                price = priceText.getText().toString();
                selected = radioGroup.getCheckedRadioButtonId();
                Log.d("LOG", Integer.toString(selected));
                if(selected == 1) gender = "남자";
                else gender = "여자";
                location = spinLocation.getSelectedItem().toString();
                period = spinPeriod.getSelectedItem().toString();
                board = new Board(title, content, date, period, price, location, gender);
                showDialog();
            }
        });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog2();
            }
        });
    }
    public void showDialog(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(WriteActivity.this);
        alertdialog.setTitle("게시");
        alertdialog.setMessage("이대로 게시하시겠습니까?");
        // 게시할 때
        alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = FirebaseFirestore.getInstance();
                db.collection("Board")
                        .add(board)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(WriteActivity.this, "게시가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WriteActivity.this, ListActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(WriteActivity.this, "네트워크 오류를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        // 게시 안하는 경우
        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(WriteActivity.this, "'취소'버튼을 눌러서 돌아갑니다.", Toast.LENGTH_SHORT).show();
            }
        });
        // 다이얼로그 보여줌
        alertdialog.show();
    }
    public void showDialog2(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(WriteActivity.this);
        alertdialog.setTitle("취소");
        alertdialog.setMessage("작성을 취소하시겠습니까?");
        // 게시할 때
        alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(WriteActivity.this, "작성을 취소했습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        // 게시 안하는 경우
        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        // 다이얼로그 보여줌
        alertdialog.show();
    }
}
