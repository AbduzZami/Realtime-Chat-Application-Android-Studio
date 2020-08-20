package com.example.pijeonchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText user_nameedittext, emailedittext, passwordedittext;
    private TextView textView;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference ;
    private ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_nameedittext = findViewById(R.id.editTextTextPersonName);
        emailedittext = findViewById(R.id.editTextTextPersonName2);
        passwordedittext = findViewById(R.id.editTextTextPassword);
        textView = findViewById(R.id.textView2);
        loginButton = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressbarlregister);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = user_nameedittext.getText().toString();
                String email = emailedittext.getText().toString().trim();
                String password = passwordedittext.getText().toString();

                if (user_name.isEmpty())
                {
                    user_nameedittext.setError("Enter a username");
                    user_nameedittext.requestFocus();
                    return;
                }

                if (email.isEmpty())
                {
                    emailedittext.setError("Enter an email address");
                    emailedittext.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    emailedittext.setError("Enter a valid email address");
                    emailedittext.requestFocus();
                    return;
                }

                if (password.isEmpty())
                {
                    passwordedittext.setError("Enter password");
                    passwordedittext.requestFocus();
                    return;
                }

                if (password.length()<6)
                {
                    passwordedittext.setError("Minimum length is 6 digits");
                    passwordedittext.requestFocus();
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);

                register(user_name,email,password);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }

    public void register (final String user_name , String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username",user_name);
                    hashMap.put("imageurl","dafault");

                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else
                                Toast.makeText(getApplicationContext(),"You can't register with this email and password",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}