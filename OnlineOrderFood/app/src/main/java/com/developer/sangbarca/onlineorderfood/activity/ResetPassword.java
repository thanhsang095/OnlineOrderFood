package com.developer.sangbarca.onlineorderfood.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.sangbarca.onlineorderfood.R;
import com.developer.sangbarca.onlineorderfood.model.Customer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ResetPassword extends AppCompatActivity {

    EditText txtEmail;
    Button btnSubmit, btnCancel;

    DatabaseReference mDatabase;
    ArrayList<Customer> arrCustomer;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        arrCustomer = new ArrayList<Customer>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Customer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Customer c = dataSnapshot.getValue(Customer.class);
                arrCustomer.add(c);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = "";
                String email = txtEmail.getText().toString();
                if(email.equals(""))
                    Toast.makeText(ResetPassword.this, "Please complete all the required fields!",
                            Toast.LENGTH_SHORT).show();
                else{
                    for(Customer c:arrCustomer)
                    {
                        if(email.equals(c.getEmail())){
                            password = c.getPassword();
                            break;
                        }
                    }
                    if(password.equals(""))
                        Toast.makeText(ResetPassword.this, "The email is not registered",
                                Toast.LENGTH_SHORT).show();
                    else
                        sendEmail();
                }
            }
        });
    }

    private void sendEmail() {
        //Getting content for email
        String email = txtEmail.getText().toString();
        String subject = "Get Password";
        String message = "Mật khẩu của bạn tại app BKFood là: " + password;

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
        txtEmail.setText("");
    }

    private void addControls() {
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);

    }
}
