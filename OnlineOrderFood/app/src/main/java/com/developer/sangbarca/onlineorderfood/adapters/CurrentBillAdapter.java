package com.developer.sangbarca.onlineorderfood.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developer.sangbarca.onlineorderfood.R;
import com.developer.sangbarca.onlineorderfood.model.Bill;
import com.developer.sangbarca.onlineorderfood.model.Table;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by SANG BARCA on 5/8/2017.
 */

public class CurrentBillAdapter extends ArrayAdapter<Bill> {

    Activity context;
    int resource;
    List<Bill> objects;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public CurrentBillAdapter(Activity context, int resource, List<Bill> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        final View row = inflater.inflate(this.resource, null);
        final TextView txtNameTable = (TextView) row.findViewById(R.id.txtNameTable);
        final TextView txtFloor = (TextView) row.findViewById(R.id.txtFloor);
        final TextView txtTotalMoney = (TextView) row.findViewById(R.id.txtTotalMoney);

        final int id = objects.get(position).getId_table();

        mDatabase.child("Table").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Table t = dataSnapshot.getValue(Table.class);
                if(t.getId()== id){
                    txtNameTable.setText(t.getTableName());
                    txtFloor.setText(Integer.toString(t.getFloor()));
                }
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
        txtTotalMoney.setText(Float.toString(objects.get(position).getTotal_Money()) + "k");

        return row;
    }
}
