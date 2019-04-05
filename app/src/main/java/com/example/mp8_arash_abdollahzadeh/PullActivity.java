package com.example.mp8_arash_abdollahzadeh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;

public class PullActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = null;

    int studentID;
    EditText studentTextIDEditText;

    Button query_1;
    Button query_2;
    Button pushButton;
    Button signOutButton;

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;

    RecyclerView recyclerView;
    GradeAdapter adapter;

    List<Grade> grade_list;
    List<Grade> gradesListTemp;
    List<Student> studentListTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();

        studentTextIDEditText = (EditText) findViewById(R.id.StudentID_Pull);
        signOutButton = (Button) findViewById(R.id.Sign_Out);
        pushButton = (Button) findViewById(R.id.PushButton);

        grade_list = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gradesListTemp = new ArrayList<>();
        studentListTemp = new ArrayList<>();
        // Add items

        user = mAuth.getCurrentUser();
        if (user == null){
            Toast.makeText(getApplicationContext(),"Something Wrong with user sign in!",Toast.LENGTH_SHORT).show();
        }



        adapter = new GradeAdapter(this, grade_list);
        recyclerView.setAdapter(adapter);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                grade_list.clear();
                Toast.makeText(PullActivity.this, "Signed Out!", Toast.LENGTH_SHORT).show();
            }
        });

        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPush();
            }
        });

    }

    public void openPush(){
        Intent intent = new Intent(this, PushActivity.class);
        startActivity(intent);
    }


    public void pull_data_q1(View view) {

        studentID = Integer.parseInt(studentTextIDEditText.getText().toString());
        DatabaseReference passID = dbrf.child("simpsons/grades/");

        passID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                gradesListTemp.clear();

                for (DataSnapshot grade: data.getChildren()){
                    Grade grade1 = grade.getValue(Grade.class);

                    gradesListTemp.add(grade1);
                }

                // adding relevant info to final list

                grade_list.clear();

                for (Grade a: gradesListTemp){
                    if (a.getstudent_id() == studentID){
                        grade_list.add(a);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //log error
            }
        });
        DatabaseReference passID_2 = dbrf.child("simpsons/students/");
        passID_2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                studentListTemp.clear();

                for (DataSnapshot student: data.getChildren()){
                    Student student1 = student.getValue(Student.class);

                    studentListTemp.add(student1);
                }

                // adding relevant info to final list

                if (grade_list.size() != 0){
                    for (Grade a: grade_list){
                        int student_id_temp = a.getstudent_id();
                        String name = "";
                        // find name:
                        for (Student b: studentListTemp){
                            if (b.getID() == student_id_temp){
                                name = b.getName();
                            }
                        }
                        a.student_name = name;
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //log error
            }
        });

        if (grade_list.size() == 0){
            Toast.makeText(this, "There were no entries under that condition!", Toast.LENGTH_SHORT).show();
        }
        else{
            this.adapter = new GradeAdapter(this, grade_list);
            recyclerView.setAdapter(adapter);
        }


    }

    public void pull_data_q2(View view) {


        studentID = Integer.parseInt(studentTextIDEditText.getText().toString());
        DatabaseReference passID = dbrf.child("simpsons/grades/");

        passID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                gradesListTemp.clear();

                for (DataSnapshot grade: data.getChildren()){
                    Grade grade1 = grade.getValue(Grade.class);

                    gradesListTemp.add(grade1);
                }

                // adding relevant info to final list

                grade_list.clear();

                for (Grade a: gradesListTemp){
                    if (a.getstudent_id() >= studentID){
                        grade_list.add(a);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //log error
            }
        });
        DatabaseReference passID_2 = dbrf.child("simpsons/students/");
        passID_2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                studentListTemp.clear();

                for (DataSnapshot student: data.getChildren()){
                    Student student1 = student.getValue(Student.class);

                    studentListTemp.add(student1);
                }

                // adding relevant info to final list

                if (grade_list.size() != 0){
                    for (Grade a: grade_list){
                        int student_id_temp = a.getstudent_id();
                        String name = "";
                        // find name:
                        for (Student b: studentListTemp){
                            if (b.getID() == student_id_temp){
                                name = b.getName();
                            }
                        }
                        a.student_name = name;
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //log error
            }
        });

        if (grade_list.size() == 0){
            Toast.makeText(this, "There were no entries under that condition!", Toast.LENGTH_SHORT).show();
        }
        else{
            this.adapter = new GradeAdapter(this, grade_list);
            recyclerView.setAdapter(adapter);
        }


    }
}
