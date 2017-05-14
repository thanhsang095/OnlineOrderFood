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

import java.util.List;

/**
 * Created by SANG BARCA on 5/8/2017.
 */

public class BillAdapter extends ArrayAdapter<Bill> {
    Activity context;
    int resource;
    List<Bill> objects;
    public BillAdapter(Activity context, int resource, List<Bill> objects) {
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
        TextView txtStt = (TextView) row.findViewById(R.id.txtStt);
        TextView txtDate = (TextView) row.findViewById(R.id.txtDate);
        TextView txtPrice = (TextView) row.findViewById(R.id.txtPrice);

        txtStt.setText("" + (position+1));
        txtDate.setText(objects.get(position).getDate().substring(9));
        txtPrice.setText(Float.toString(objects.get(position).getTotal_Money()) + "k");
        return row;
    }
}
