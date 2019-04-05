package com.example.mp8_arash_abdollahzadeh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button createButton;
    Button loginButton;

    EditText userName;
    EditText passwordName;

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;

    FirebaseAuth mAuth;
    FirebaseUser user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();

        createButton=findViewById(R.id.CreateUserButton);
        loginButton=findViewById(R.id.LoginButton);
        userName=findViewById(R.id.UsernameEditText);
        passwordName=findViewById(R.id.PasswordEditText);

        mAuth = FirebaseAuth.getInstance();

    }
    public void CreateClick(View view) {

        String email = userName.getText().toString();
        String password = passwordName.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Created Account",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The newly created user is already signed in
                            openPull();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void LoginClick(View view) {

        String email = userName.getText().toString();
        String password = passwordName.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The user is signed in
                            openPull();
                        } else {
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void LogoutClick(View view) {
        mAuth.signOut();
        user =null;
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
    }
    public void openPull(){
        Intent intent = new Intent(this, PullActivity.class);
        startActivity(intent);
    }

//    public void gradeClick(View view) {
//
//        if(user!=null) {
//
//            int studID = Integer.parseInt(IDText.getText().toString());
//
//            DatabaseReference gradeKey = dbrf.child("simpsons/grades/");
//
//            Query query = gradeKey.orderByChild("student_id").equalTo(studID);
//            query.addListenerForSingleValueEvent(valueEventListener);
//
//        }
//
//        else{
//
//            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
//        }
//
//    }
//    ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            if (dataSnapshot.exists()) {
//                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Grade grade = snapshot.getValue(Grade.class);
//                    if(grade.getcourse_name().equals("Math")) {
//                        Toast.makeText(getApplicationContext(),"Math Grade is " + grade.getgrade(),Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            //log error
//
//        }
//    };
}
