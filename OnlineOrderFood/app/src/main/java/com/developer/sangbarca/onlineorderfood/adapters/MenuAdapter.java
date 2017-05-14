package com.developer.sangbarca.onlineorderfood.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.sangbarca.onlineorderfood.R;
import com.developer.sangbarca.onlineorderfood.model.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SANG BARCA on 5/8/2017.
 */

public class MenuAdapter extends ArrayAdapter<Food> {
    Activity context;
    int resource;
    List<Food> objects;

    public MenuAdapter(Activity context, int resource, List<Food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);

        ImageView imgFoodName = (ImageView) row.findViewById(R.id.imgFoodName);
        TextView txtFoodName = (TextView) row.findViewById(R.id.txtFoodName);
        TextView txtFoodPrice = (TextView) row.findViewById(R.id.txtFoodPrice);

        Picasso.with(context).load(objects.get(position).getImage()).into(imgFoodName);
        txtFoodName.setText(objects.get(position).getFood_Name());
        txtFoodPrice.setText("Price: " + Float.toString(objects.get(position).getPrice()) + "k");

        ImageButton btnAdd = (ImageButton) row.findViewById(R.id.btnAdd);
        ImageButton btnSub = (ImageButton) row.findViewById(R.id.btnSub);
        final TextView txtNumberFood = (TextView) row.findViewById(R.id.txtNumberFood);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(txtNumberFood.getText().toString());
                i++;
                txtNumberFood.setText(i+ "");
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(txtNumberFood.getText().toString());
                if(i!=0)
                    i--;
                txtNumberFood.setText(i+ "");
            }
        });


        return row;
    }
}
