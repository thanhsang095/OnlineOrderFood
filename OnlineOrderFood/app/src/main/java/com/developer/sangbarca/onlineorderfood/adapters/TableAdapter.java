package com.developer.sangbarca.onlineorderfood.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.sangbarca.onlineorderfood.R;
import com.developer.sangbarca.onlineorderfood.activity.SelectMenu;
import com.developer.sangbarca.onlineorderfood.model.Bill;
import com.developer.sangbarca.onlineorderfood.model.Table;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SANG BARCA on 5/8/2017.
 */

public class TableAdapter extends ArrayAdapter<Table> {
    Activity context;
    int resource;
    List<Table> objects;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public TableAdapter(Activity context, int resource, List<Table> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        ImageButton imageTable = (ImageButton) row.findViewById(R.id.imgTable);
        TextView txtNumberTable = (TextView) row.findViewById(R.id.txtNumberTable);

        txtNumberTable.setText(objects.get(position).getTableName());
        if(objects.get(position).getStatus()==0)
            imageTable.setImageResource(R.drawable.whitetable);
        else if(objects.get(position).getStatus()==1)
            imageTable.setImageResource(R.drawable.pinktable);
        else imageTable.setImageResource(R.drawable.greentable);

        final int i = objects.get(position).getId();

        imageTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(objects.get(position).getStatus()==0){
                    Intent intent =new Intent(context, SelectMenu.class);
                    intent.putExtra("id", objects.get(position).getId());
                    context.startActivityForResult(intent, 100);
                }else{
                    //<code></code>
                    final ArrayList<String> order = new ArrayList<String>();
                    final Dialog alertDialog = new Dialog(context);
                    LayoutInflater inflater = context.getLayoutInflater();

                    final View convertView = (View) inflater.inflate(R.layout.dialog_table, null);
                    alertDialog.setContentView(convertView);
                    final TextView txtTime = (TextView) alertDialog.findViewById(R.id.txtTime);
                    alertDialog.setTitle("Order");
                    alertDialog.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();

                    mDatabase.child("Bill").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            //Toast.makeText(context, "" + dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                            Bill bill = dataSnapshot.getValue(Bill.class);
                            if(bill.getId_table()==objects.get(position).getId()&&bill.getStatus()==0){
                                txtTime.setText(bill.getDate());
                                for(int i =0;i<bill.getArrNumber().size();i++)
                                    order.add("(+"+bill.getArrNumber().get(i) + ") " + bill.getListFood().get(i));
                                ListView lv = (ListView) convertView.findViewById(R.id.lvListFoodOrder);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,order);
                                lv.setAdapter(adapter);
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
                }
            }
        });

        return row;
    }


}
