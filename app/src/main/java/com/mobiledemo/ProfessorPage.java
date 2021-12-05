package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfessorPage extends AppCompatActivity {
    private FirebaseFirestore db;
    private String professorName;
    private String regNo;
    private TextView professorNameView, professorTakeAgainView, professorQualityView, professorDifficultyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_page);
        Intent courseIntent = getIntent();
        regNo = courseIntent.getStringExtra("regNo");
        professorName = courseIntent.getStringExtra("professor");

        CollectionReference docRef = db.collection("professors");
        professorNameView = findViewById(R.id.professorNameView);
        professorDifficultyView = findViewById(R.id.professorDifficultyView);
        professorQualityView = findViewById(R.id.professorQualityView);
        professorTakeAgainView = findViewById(R.id.professorTakeAgainView);
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot query = task.getResult();
                    boolean hasProfessor = false;
                    for (DocumentSnapshot docus : query) {
                        String this_name = (String) docus.getData().get("name");
                        String take_again;
                        if (docus.getData().get("take_again") instanceof Double) {
                            take_again = Double.toString ((double) docus.getData().get("take_again"));
                        } else if (docus.getData().get("take_again") instanceof String) {
                            take_again = (String) docus.getData().get("take_again");
                        } else {
                            take_again = Long.toString ((long) docus.getData().get("take_again"));
                        }
                        String quality;
                        if (docus.getData().get("quality") instanceof Double) {
                            quality = Double.toString ((double) docus.getData().get("quality"));
                        } else if (docus.getData().get("quality") instanceof String) {
                            quality = (String) docus.getData().get("quality");
                        } else {
                            quality = Long.toString ((long) docus.getData().get("quality"));
                        }
                        String difficulty;
                        if (docus.getData().get("diffculty") instanceof Double) {
                            difficulty = Double.toString ((double) docus.getData().get("diffculty"));
                        } else if (docus.getData().get("diffculty") instanceof String) {
                            difficulty = (String) docus.getData().get("diffculty");
                        } else {
                            difficulty = Long.toString ((long) docus.getData().get("diffculty"));
                        }
                        String[] temp = this_name.split(" ");
                        boolean correct = true;

                        for (int i = 0; i < temp.length; i++) {
                            if (!professorName.contains(temp[i])) {
                                correct = false;
                                break;
                            }
                        }
                        if (correct) {
                            professorNameView.setText(this_name);
                            if (!take_again.equals("-1")) {
                                professorTakeAgainView.setText(take_again);
                            } else {
                                professorTakeAgainView.setText("N/A");
                            }
                            if (!quality.equals("0")) {
                                professorQualityView.setText(quality);
                            } else {
                                professorQualityView.setText("N/A");
                            }
                            if (!difficulty.equals("0")) {
                                professorDifficultyView.setText(difficulty);
                            } else {
                                professorDifficultyView.setText("N/A");
                            }
                            hasProfessor = true;
                            break;
                        }
                    }
                    if (!hasProfessor) {
                        professorNameView.setText("No Such Professor");
                        professorTakeAgainView.setText("");
                        professorQualityView.setText("");
                        professorDifficultyView.setText("");
                        Toast.makeText(ProfessorPage.this, "No such professor", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void onInfoButton(View view) {
        Intent info = new Intent(ProfessorPage.this, InfoPage.class);
        startActivity(info);
    }

    public void onSearchButton(View view) {
        Intent search = new Intent(ProfessorPage.this, SearchClassPage.class);
        startActivity(search);
    }

    public void onSettingButton(View view) {
        Intent setting = new Intent(ProfessorPage.this, SettingPage.class);
        startActivity(setting);
    }
    public void onCourseListButton(View view) {
        Intent courseList = new Intent(ProfessorPage.this, CourseListPage.class);
        startActivity(courseList);
    }
}