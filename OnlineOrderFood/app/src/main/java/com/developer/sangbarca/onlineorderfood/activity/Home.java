package com.developer.sangbarca.onlineorderfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.developer.sangbarca.onlineorderfood.model.Bill;
import com.developer.sangbarca.onlineorderfood.model.Customer;
import com.developer.sangbarca.onlineorderfood.model.Food;
import com.developer.sangbarca.onlineorderfood.model.Information;
import com.developer.sangbarca.onlineorderfood.model.Table;
import com.developer.sangbarca.onlineorderfood.R;
import com.developer.sangbarca.onlineorderfood.adapters.BillAdapter;
import com.developer.sangbarca.onlineorderfood.adapters.CurrentBillAdapter;
import com.developer.sangbarca.onlineorderfood.adapters.FoodAdapter;
import com.developer.sangbarca.onlineorderfood.adapters.InformationAdapter;
import com.developer.sangbarca.onlineorderfood.adapters.TableAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    String username="";

    TextView txtEmail, txtPhone;

    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;

    ActionBarDrawerToggle actionBarDrawerToggle;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    //tab information
    ListView lvInformation;
    ArrayList<Information> arrInfo = new ArrayList<>();
    InformationAdapter adapterInfo = null;

    //tab menu
    GridView gvFavouriteFood;
    ArrayList<Food> arrFood = new ArrayList<>();
    FoodAdapter adapterFavouriteFood = null;

    GridView gvStarters;
    ArrayList<Food> arrStarters= new ArrayList<>();
    FoodAdapter adapterStarters = null;

    GridView gvMainDishes;
    ArrayList<Food> arrMainDishes= new ArrayList<>();
    FoodAdapter adapterMainDishes = null;

    GridView gvSoups;
    ArrayList<Food> arrSoups= new ArrayList<>();
    FoodAdapter adapterSoups = null;

    GridView gvDrinks;
    ArrayList<Food> arrDrinks= new ArrayList<>();
    FoodAdapter adapterDrinks= null;

    //tab table
    GridView gvFloor1;
    ArrayList<Table> arrTable1 = new ArrayList<>();
    TableAdapter adapterTable = null;

    GridView gvFloor2;
    ArrayList<Table> arrTable2 = new ArrayList<>();
    TableAdapter adapterTable2 = null;

    Bill bill;
    ArrayList<String> listKey = new ArrayList<>();

    //tab History
    ListView lv_history_order;
    ArrayList<Bill> arrBill = new ArrayList<>();
    BillAdapter adapterBill = null;

    ListView lv_current_order;
    ArrayList<Bill> arrCurrentBill = new ArrayList<>();
    CurrentBillAdapter adapterCurrentBill = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        config();
        addControl();
        loadInformation();
        adapterInfo = new InformationAdapter(this, R.layout.item_home, arrInfo);
        lvInformation.setAdapter(adapterInfo);

        loadFood();
        adapterFavouriteFood = new FoodAdapter(this, R.layout.item_menu, arrFood);
        gvFavouriteFood.setAdapter(adapterFavouriteFood);

        adapterStarters=new FoodAdapter(this, R.layout.item_menu, arrStarters);
        gvStarters.setAdapter(adapterStarters);

        adapterMainDishes=new FoodAdapter(this, R.layout.item_menu, arrMainDishes);
        gvMainDishes.setAdapter(adapterMainDishes);

        adapterSoups=new FoodAdapter(this, R.layout.item_menu, arrSoups);
        gvSoups.setAdapter(adapterSoups);

        adapterDrinks=new FoodAdapter(this, R.layout.item_menu, arrDrinks);
        gvDrinks.setAdapter(adapterDrinks);

        loadTable();
        adapterTable= new TableAdapter(this, R.layout.item_table, arrTable1);
        gvFloor1.setAdapter(adapterTable);

        adapterTable2 = new TableAdapter(this, R.layout.item_table, arrTable2);
        gvFloor2.setAdapter(adapterTable2);

        loadBill();
        adapterBill = new BillAdapter(this, R.layout.item_order_history, arrBill);
        lv_history_order.setAdapter(adapterBill);

        adapterCurrentBill = new CurrentBillAdapter(this, R.layout.item_order_current, arrCurrentBill);
        lv_current_order.setAdapter(adapterCurrentBill);

        addEvents();


    }

    private void addEvents() {
    }

    private void addControl() {
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        lvInformation = (ListView) findViewById(R.id.lvInformation);

        gvFavouriteFood = (GridView) findViewById(R.id.gvFavouriteFood);
        gvStarters = (GridView) findViewById(R.id.gvStarters);
        gvMainDishes = (GridView) findViewById(R.id.gvMainDishes);
        gvSoups = (GridView) findViewById(R.id.gvSoups);
        gvDrinks = (GridView) findViewById(R.id.gvDrinks);
        gvFloor1 = (GridView) findViewById(R.id.gvFloor1);
        gvFloor2 = (GridView) findViewById(R.id.gvFloor2);
        lv_history_order = (ListView) findViewById(R.id.lv_history_order);
        lv_current_order = (ListView) findViewById(R.id.lv_current_order);

        Intent intent = getIntent();
        Customer customer = (Customer) intent.getSerializableExtra("CUSTOMER");
        txtEmail.setText(customer.getEmail());
        txtPhone.setText(customer.getPhone());
        username = customer.getUsername();

        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        final TabHost.TabSpec tab1 = tabHost.newTabSpec("Home");
        tab1.setIndicator("", getResources().getDrawable(R.drawable.home));
        tab1.setContent(R.id.tab_home);
        tabHost.addTab(tab1);
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.color.tab_selected);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("Menu");
        tab2.setIndicator("", getResources().getDrawable(R.drawable.menu));
        tab2.setContent(R.id.tab_menu);
        tabHost.addTab(tab2);
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.color.tab_unselected);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("Table");
        tab3.setIndicator("", getResources().getDrawable(R.drawable.table));
        tab3.setContent(R.id.tab_table);
        tabHost.addTab(tab3);
        tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.color.tab_unselected);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("History");
        tab4.setIndicator("", getResources().getDrawable(R.drawable.history));
        tab4.setContent(R.id.tab_history);
        tabHost.addTab(tab4);
        tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.color.tab_unselected);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for(int i= 0; i< tabHost.getTabWidget().getChildCount();i++)
                {
                    tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.tab_unselected);
                }
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.color.tab_selected);
            }
        });
    }

    private  void config(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);

        //create listener for drawer layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_opened, R.string.drawer_closed)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void logoutAccount(View view) {
        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private  void loadInformation(){
        mDatabase.child("Information").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Information information = dataSnapshot.getValue(Information.class);
                arrInfo.add(new Information(information.getId(),
                        information.getName(), information.getLink()));
                adapterInfo.notifyDataSetChanged();
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

    private void loadTable() {
        mDatabase.child("Table").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listKey.add(dataSnapshot.getKey());
                Table t = dataSnapshot.getValue(Table.class);
                Table table = new Table(t.getId(), t.getTableName(), t.getFloor(), t.getStatus());
                if(table.getFloor()==1)
                    arrTable1.add(table);
                if(table.getFloor()==2)
                    arrTable2.add(table);
                adapterTable.notifyDataSetChanged();
                adapterTable2.notifyDataSetChanged();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bill = null;
        if(requestCode==100 && resultCode == 1000){
            bill = (Bill) data.getSerializableExtra("bill");
            bill.setUsername(username);
            mDatabase.child("Bill").push().setValue(bill);
            //arrCurrentBill.add(bill);
            adapterCurrentBill.notifyDataSetChanged();
        }
        if(bill !=null)
        {
            //Toast.makeText(this, "" + bill.getDate(), Toast.LENGTH_SHORT).show();
            Table table = null;
            for(Table t:arrTable1){
                if(t.getId()==bill.getId_table())
                {
                    table = t;
                    t.setStatus(1);
                    adapterTable.notifyDataSetChanged();
                    break;
                }
            }
            for (Table t:arrTable2){
                if(t.getId()==bill.getId_table()){
                    table = t;
                    t.setStatus(1);
                    adapterTable2.notifyDataSetChanged();
                    break;
                }
            }
            mDatabase.child("Table").child(listKey.get(bill.getId_table()-1)).setValue(table);
        }


    }

    private void loadBill() {
        mDatabase.child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if(bill.getUsername().equals(username)&&bill.getStatus()==0){
                    arrCurrentBill.add(new Bill(1, 0, bill.getDate(), bill.getListFood(), bill.getArrNumber(),
                            bill.getTotal_Money(), bill.getId_table(), bill.getUsername()));
                }
                if(bill.getUsername().equals(username) && bill.getStatus()==1){
                    arrBill.add(new Bill(1, 1, bill.getDate(), bill.getListFood(), bill.getArrNumber(),
                            bill.getTotal_Money(), bill.getId_table(), bill.getUsername()));
                }
                adapterBill.notifyDataSetChanged();
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
