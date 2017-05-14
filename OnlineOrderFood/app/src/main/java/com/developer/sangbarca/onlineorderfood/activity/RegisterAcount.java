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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterAcount extends AppCompatActivity {

    EditText txtEmail, txtPhone, txtUsername, txtPassword;
    Button btnRegister, btnCancel;

    DatabaseReference mDatabase;
    ArrayList<Customer> arrCustomer;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$",Pattern.CASE_INSENSITIVE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acount);
        addControls();
        addEvents();

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
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtEmail.getText().toString();
                //Toast.makeText(RegisterAcount.this, "" + email, Toast.LENGTH_SHORT).show();
                String phone = txtPhone.getText().toString();
                final String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                Matcher matcher_email = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
                Matcher matcher_phone = VALID_PHONE_NUMBER_REGEX.matcher(phone);
                if(email.equals("") || email.equals("") || username.equals("") || password.equals(""))
                    Toast.makeText(RegisterAcount.this, "Please complete all the required fields!",
                            Toast.LENGTH_SHORT).show();
                else if(matcher_email.find()==false)
                    Toast.makeText(RegisterAcount.this, "Invalid email address!", Toast.LENGTH_SHORT).show();
                else if(matcher_phone.find()==false)
                    Toast.makeText(RegisterAcount.this, "Invalid phone number!", Toast.LENGTH_SHORT).show();
                else if(password.length()<6)
                    Toast.makeText(RegisterAcount.this, "Password must be at least 6 characters long!",
                            Toast.LENGTH_SHORT).show();
                else
                {
                    boolean checkEmail = true;
                    boolean checkUsername = true;
                    for(Customer c:arrCustomer){
                        if(email.equals(c.getEmail()))
                        {
                            checkEmail = false;
                            break;
                        }
                        if(username.equals(c.getUsername())) {
                            checkUsername = false;
                            break;
                        }
                    }
                    if(checkEmail==false)
                        Toast.makeText(RegisterAcount.this, "The email address you entered is already in use!",
                                Toast.LENGTH_SHORT).show();
                    else if(checkUsername == false)
                        Toast.makeText(RegisterAcount.this, "The username you entered is already in use!",
                                Toast.LENGTH_SHORT).show();
                    else{
                        // Write a message to the database
                        Customer customer = new Customer(1, username, password, email, phone);
                        mDatabase.child("Customer").push().setValue(customer, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError==null)
                                {
                                    finish();
                                    Toast.makeText(RegisterAcount.this, "New account has been created",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void addControls() {
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }
}
