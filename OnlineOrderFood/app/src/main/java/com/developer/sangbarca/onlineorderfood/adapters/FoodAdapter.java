package com.developer.sangbarca.onlineorderfood.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.sangbarca.onlineorderfood.R;
import com.developer.sangbarca.onlineorderfood.model.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SANG BARCA on 5/8/2017.
 */

public class FoodAdapter  extends ArrayAdapter<Food> {

    Activity context;
    int resource;
    List<Food> objects;

    public FoodAdapter(Activity context, int resource, List<Food> objects) {
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

        ImageView imgFood = (ImageView) row.findViewById(R.id.imgFood);
        TextView txtFood = (TextView) row.findViewById(R.id.txtFood);
        TextView txtPrice = (TextView) row.findViewById(R.id.txtPrice);

        Picasso.with(context).load(objects.get(position).getImage()).into(imgFood);
        txtFood.setText(objects.get(position).getFood_Name());
        txtPrice.setText(Float.toString(objects.get(position).getPrice()) + "k");

        return row;
    }
}
