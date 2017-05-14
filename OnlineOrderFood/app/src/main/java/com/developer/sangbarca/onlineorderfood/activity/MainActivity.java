package com.developer.sangbarca.onlineorderfood.activity;

import android.content.Intent;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnSignIn,btnSignUp;
    StringBuilder a = new StringBuilder();


    DatabaseReference mDatabase;
    ArrayList<Customer> arrCustomer = new ArrayList<Customer>();;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://onlineorderfood-fac79.appspot.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void getData(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Customer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(MainActivity.this, "" +dataSnapshot.getValue(Customer.class).getUsername() , Toast.LENGTH_SHORT).show();
                arrCustomer.add(dataSnapshot.getValue(Customer.class));
                a.append(dataSnapshot.getValue(Customer.class).getUsername());
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

    }

    private void addControls() {
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

    }

    public void openRegisterScreen(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterAcount.class);
        startActivity(intent);
    }

    public void loginAccount(View view) {
        //Toast.makeText(this, "" + a, Toast.LENGTH_SHORT).show();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        Customer customer;
        if(username.equals("")||password.equals(""))
            Toast.makeText(this, "Please complete all the required fields!", Toast.LENGTH_SHORT).show();
        else{
            boolean checkAccout = false;
            for(Customer c:arrCustomer)
            {
                if(username.equals(c.getUsername())&& password.equals(c.getPassword()))
                {
                    customer = new Customer(1, c.getUsername(),c.getPassword(), c.getEmail(),c.getPhone());
                    checkAccout = true;
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("CUSTOMER", customer);
                    startActivity(intent);
                    this.finish();
                    break;
                }
            }
            if(checkAccout==false)
                Toast.makeText(this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
        }

    }

    public void openGetPassword(View view) {
        Intent intent = new Intent(MainActivity.this, ResetPassword.class);
        startActivity(intent);
    }
}
