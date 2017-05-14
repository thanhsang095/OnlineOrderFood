package com.developer.sangbarca.onlineorderfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.sangbarca.onlineorderfood.R;
import com.developer.sangbarca.onlineorderfood.adapters.MenuAdapter;
import com.developer.sangbarca.onlineorderfood.model.Bill;
import com.developer.sangbarca.onlineorderfood.model.Food;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SelectMenu extends AppCompatActivity {

    int id;
    Intent intent;

    Button btnConfirm;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    ListView lvFavouriteFood;
    ArrayList<Food> arrFood = new ArrayList<>();
    MenuAdapter adapterFavouriteFood = null;

    ListView lvStarters;
    ArrayList<Food> arrStarters= new ArrayList<>();
    MenuAdapter adapterStarters = null;

    ListView lvMainDishes;
    ArrayList<Food> arrMainDishes= new ArrayList<>();
    MenuAdapter adapterMainDishes = null;

    ListView lvSoups;
    ArrayList<Food> arrSoups= new ArrayList<>();
    MenuAdapter adapterSoups = null;

    ListView lvDrinks;
    ArrayList<Food> arrDrinks= new ArrayList<>();
    MenuAdapter adapterDrinks= null;

    ArrayList<String> arrSelectedFood;
    ArrayList<Integer> arrNumberFood;
    ArrayList<String> arrPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);
        addControls();
        addEvents();

        loadFood();
        adapterFavouriteFood = new MenuAdapter(this, R.layout.item_select_menu, arrFood);
        lvFavouriteFood.setAdapter(adapterFavouriteFood);

        adapterStarters = new MenuAdapter(this, R.layout.item_select_menu, arrStarters);
        lvStarters.setAdapter(adapterStarters);

        adapterMainDishes = new MenuAdapter(this, R.layout.item_select_menu, arrMainDishes);
        lvMainDishes.setAdapter(adapterMainDishes);

        adapterSoups = new MenuAdapter(this, R.layout.item_select_menu, arrSoups);
        lvSoups.setAdapter(adapterSoups);

        adapterDrinks = new MenuAdapter(this, R.layout.item_select_menu, arrDrinks);
        lvDrinks.setAdapter(adapterDrinks);

    }

    private void addEvents() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtFood;
                arrSelectedFood = new ArrayList<String>();
                arrNumberFood = new ArrayList<Integer>();
                arrPrice = new ArrayList<String>();
                for(int i =0; i<lvFavouriteFood.getCount();i++)
                {
                    txtFood = (TextView) lvFavouriteFood.getChildAt(i).findViewById(R.id.txtNumberFood);
                    if(Integer.parseInt(txtFood.getText().toString())>0)
                    {
                        arrNumberFood.add(Integer.parseInt(txtFood.getText().toString()));
                        TextView txtFoodName = (TextView) lvFavouriteFood.getChildAt(i).findViewById(R.id.txtFoodName);
                        arrSelectedFood.add(txtFoodName.getText().toString());
                        TextView txtFoodPrice = (TextView) lvFavouriteFood.getChildAt(i).findViewById(R.id.txtFoodPrice);
                        arrPrice.add(txtFoodPrice.getText().toString());
                    }


                }

                for(int i =0; i<lvStarters.getCount();i++)
                {
                    txtFood = (TextView) lvStarters.getChildAt(i).findViewById(R.id.txtNumberFood);
                    if(Integer.parseInt(txtFood.getText().toString())>0)
                    {
                        arrNumberFood.add(Integer.parseInt(txtFood.getText().toString()));
                        TextView txtFoodName = (TextView) lvStarters.getChildAt(i).findViewById(R.id.txtFoodName);
                        arrSelectedFood.add(txtFoodName.getText().toString());
                        TextView txtFoodPrice = (TextView) lvStarters.getChildAt(i).findViewById(R.id.txtFoodPrice);
                        arrPrice.add(txtFoodPrice.getText().toString());
                    }

                }

                for(int i =0; i<lvMainDishes.getCount();i++)
                {
                    txtFood = (TextView) lvMainDishes.getChildAt(i).findViewById(R.id.txtNumberFood);
                    if(Integer.parseInt(txtFood.getText().toString())>0)
                    {
                        arrNumberFood.add(Integer.parseInt(txtFood.getText().toString()));
                        TextView txtFoodName = (TextView) lvMainDishes.getChildAt(i).findViewById(R.id.txtFoodName);
                        arrSelectedFood.add(txtFoodName.getText().toString());
                        TextView txtFoodPrice = (TextView) lvMainDishes.getChildAt(i).findViewById(R.id.txtFoodPrice);
                        arrPrice.add(txtFoodPrice.getText().toString());
                    }

                }

                for(int i =0; i<lvSoups.getCount();i++)
                {
                    txtFood = (TextView) lvSoups.getChildAt(i).findViewById(R.id.txtNumberFood);
                    if(Integer.parseInt(txtFood.getText().toString())>0)
                    {
                        arrNumberFood.add(Integer.parseInt(txtFood.getText().toString()));
                        TextView txtFoodName = (TextView) lvSoups.getChildAt(i).findViewById(R.id.txtFoodName);
                        arrSelectedFood.add(txtFoodName.getText().toString());
                        TextView txtFoodPrice = (TextView) lvSoups.getChildAt(i).findViewById(R.id.txtFoodPrice);
                        arrPrice.add(txtFoodPrice.getText().toString());
                    }

                }

                for(int i =0; i<lvDrinks.getCount();i++)
                {
                    txtFood = (TextView) lvDrinks.getChildAt(i).findViewById(R.id.txtNumberFood);
                    if(Integer.parseInt(txtFood.getText().toString())>0)
                    {
                        arrNumberFood.add(Integer.parseInt(txtFood.getText().toString()));
                        TextView txtFoodName = (TextView) lvDrinks.getChildAt(i).findViewById(R.id.txtFoodName);
                        arrSelectedFood.add(txtFoodName.getText().toString());
                        TextView txtFoodPrice = (TextView) lvDrinks.getChildAt(i).findViewById(R.id.txtFoodPrice);
                        arrPrice.add(txtFoodPrice.getText().toString());
                    }

                }

                if(arrSelectedFood.size()==0)
                    Toast.makeText(SelectMenu.this, "You have not selected any food!", Toast.LENGTH_SHORT).show();
                else{
                    float total_Money = 0;
                    total_Money = calculateTotalMoney(arrNumberFood, arrPrice);
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                    Date date = new Date();
                    //Toast.makeText(SelectMenu.this, "" + dateFormat.format(date), Toast.LENGTH_SHORT).show();

                    // Trả kết quả về màn hình home
                    Bill bill = new Bill(1, 0, dateFormat.format(date), arrSelectedFood, arrNumberFood, total_Money, id, "");
                    //mDatabase.child("Bill").push().setValue(bill);
                    intent.putExtra("bill", bill);
                    setResult(1000, intent);
                    finish();
                }
            }
        });
    }

    private float calculateTotalMoney(ArrayList<Integer> arrNumberFood, ArrayList<String> arrPrice) {
        float sum = 0;
        for(int i =0;i<arrNumberFood.size();i++){
            int length = arrPrice.get(i).length();
            sum += arrNumberFood.get(i)* Float.parseFloat(arrPrice.get(i).substring(7,length-1));
        }
        return sum;
    }

    private void addControls() {
        lvFavouriteFood = (ListView) findViewById(R.id.lvFavouriteFood);
        lvStarters = (ListView) findViewById(R.id.lvStarters);
        lvMainDishes = (ListView) findViewById(R.id.lvMainDishes);
        lvSoups = (ListView) findViewById(R.id.lvSoups);
        lvDrinks = (ListView) findViewById(R.id.lvDrinks);

        btnConfirm = (Button) findViewById(R.id.btnConfirm);

        intent = getIntent();
        id = intent.getIntExtra("id", -1);
    }

    public void exitSelectMenu(View view) {
        finish();
    }

    private void loadFood() {
        mDatabase.child("Food").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Food f = dataSnapshot.getValue(Food.class);
                Food food = new Food(f.getId(), f.getFood_Name(), f.getKind(),
                        f.getPrice(), f.getAccumulation(), f.getImage());
                if(arrFood.size()<4)
                    arrFood.add(food);
                if(food.getKind().equals("starters"))
                    arrStarters.add(food);
                if(food.getKind().equals("maindishes"))
                    arrMainDishes.add(food);
                if(food.getKind().equals("soup"))
                    arrSoups.add(food);
                if(food.getKind().equals("drinks"))
                    arrDrinks.add(food);

                adapterFavouriteFood.notifyDataSetChanged();
                adapterStarters.notifyDataSetChanged();
                adapterMainDishes.notifyDataSetChanged();
                adapterSoups.notifyDataSetChanged();
                adapterDrinks.notifyDataSetChanged();
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
