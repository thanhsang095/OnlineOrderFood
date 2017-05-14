package com.developer.sangbarca.onlineorderfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.developer.sangbarca.onlineorderfood.R;
import com.developer.sangbarca.onlineorderfood.model.Information;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SANG BARCA on 5/8/2017.
 */

public class InformationAdapter extends BaseAdapter {

    Context context;
    int myLayout;
    List<Information> arrInfo;

    public InformationAdapter(Context context, int myLayout, List<Information> arrInfo) {
        this.context = context;
        this.myLayout = myLayout;
        this.arrInfo = arrInfo;
    }

    @Override
    public int getCount() {
        return arrInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return arrInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        ImageView imageView;
        if(row == null){
            row = inflater.inflate(myLayout, null);
            imageView = (ImageView) row.findViewById(R.id.imgItemHome);
            row.setTag(imageView);
        }else{
            imageView = (ImageView) row.getTag();
        }

        Picasso.with(context).load(arrInfo.get(position).getLink()).into(imageView);
        return row;
    }
}
