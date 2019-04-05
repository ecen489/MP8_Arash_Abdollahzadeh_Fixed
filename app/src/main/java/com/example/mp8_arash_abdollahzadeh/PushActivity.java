package com.example.mp8_arash_abdollahzadeh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PushActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = null;

    int studentID;
    EditText id;
    EditText name;
    EditText course_grade;

    Button pushButton;

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;

    int radioID = R.id.rad_ralph;
    int dbID = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        pushButton = (Button) findViewById(R.id.PushButton);

        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGrade();
            }
        });

        user = mAuth.getCurrentUser();
        if (user == null){
            Toast.makeText(getApplicationContext(),"Something Wrong with user sign in!",Toast.LENGTH_SHORT).show();
        }

        dbrf = FirebaseDatabase.getInstance().getReference("simpsons/grades/");
    }


    public void addGrade(){
        String someID = dbrf.push().getKey();

        id = (EditText) findViewById(R.id.course_id);
        name = (EditText) findViewById(R.id.course_name);
        course_grade = (EditText) findViewById(R.id.course_grade_push);

        Grade grade = new Grade(Integer.parseInt(id.getText().toString()), name.getText().toString(), course_grade.getText().toString(), dbID);

        dbrf.child(someID).setValue(grade);

        Toast.makeText(this, "Grade added!", Toast.LENGTH_SHORT).show();

        backToPull();

    }

    public void  backToPull(){
        Intent intent = new Intent(this, PullActivity.class);
        startActivity(intent);
    }

    public void radioClick(View view) {
        radioID = view.getId();
        if(radioID==R.id.rad_bart){dbID = 123;}
        if(radioID==R.id.rad_ralph){dbID = 404;}
        if(radioID==R.id.rad_milhouse){dbID = 456;}
        if(radioID==R.id.rad_lisa){dbID = 888;}
    }
}
