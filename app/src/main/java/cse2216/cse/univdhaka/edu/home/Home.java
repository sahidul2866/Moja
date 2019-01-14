package cse2216.cse.univdhaka.edu.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getSystemService;

public class Home extends AppCompatActivity  {

    Button button;
    private ListView listView;
    private List<foodPrice> list;
    private Intent intent;
    Button login;
    String user,type;
    private long timer;
    private Toast backToast;

    SharedPreferences sharedPreferences;
    DatabaseReference databaseHome;

    private android.support.v7.widget.Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurents);

        toolbar = findViewById(R.id.toolbarID);
        navigationView = findViewById(R.id.navigationViewID);
        drawerLayout = findViewById(R.id.drawerLayoutID);

        setSupportActionBar(toolbar);

        final ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);



        if(!isConnected()) Toast.makeText(Home.this,"Network Unavailable",Toast.LENGTH_LONG).show();

        sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("checker",false);
        if(check == true){
             user = sharedPreferences.getString("user","Not Found");
            type = sharedPreferences.getString("type","Not Found");
            System.out.println(user+type);
        }
        else {
            intent = new Intent(Home.this, LogIn.class);
            startActivity(intent);
        }



        listView=findViewById(R.id.HomeList);
        databaseHome = FirebaseDatabase.getInstance().getReference("Home");
        list = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isConnected()) Toast.makeText(Home.this,"Network Unavailable",Toast.LENGTH_LONG).show();
                foodPrice price =list.get(i);
                intent = new Intent(Home.this,Menu.class);
                intent.putExtra("resName",price.getFoodName());
                intent.putExtra("user",user);
                intent.putExtra("type",type);
                startActivity(intent);
                Toast.makeText(Home.this,price.getFoodName(),Toast.LENGTH_LONG).show();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.nav_restaurantID)
                {
                    displayMassege("Restaurant is selected");
                    drawerLayout.closeDrawers();
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_pastOrderID)
                {
                    displayMassege("Past Order is selected");
                    drawerLayout.closeDrawers();
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_profileID)
                {
                    displayMassege("Profile is selected");
                    drawerLayout.closeDrawers();
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_addressID)
                {
                    displayMassege("Address is selected");
                    drawerLayout.closeDrawers();
                    return  true;
                }
                else  if(menuItem.getItemId() == R.id.nav_reviewsID)
                {
                    displayMassege("Review is selected");
                    drawerLayout.closeDrawers();
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_aboutUsID)
                {
                    displayMassege("About_Us is selected");
                    drawerLayout.closeDrawers();
                    return  true;
                }
                else  if(menuItem.getItemId() == R.id.nav_signOutID)
                {
                    displayMassege("sign Out is selected");
                    drawerLayout.closeDrawers();
                    if(!isConnected()) Toast.makeText(Home.this,"Network Unavailable",Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user","");
                    editor.putString("type","");
                    editor.putBoolean("checker",false);
                    editor.commit();
                    intent = new Intent(Home.this,LogIn.class);
                    startActivity(intent);
                    return  true;
                }


                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(timer + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else {
            backToast = Toast.makeText(Home.this,"Press back again to exit",Toast.LENGTH_LONG);
            backToast.show();
        }
        timer = System.currentTimeMillis();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if(!isConnected()) Toast.makeText(Home.this,"Network Unavailable",Toast.LENGTH_LONG).show();

        databaseHome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();

                for(DataSnapshot reviewSnapShot: dataSnapshot.getChildren()){
                    RestaurentList Addreview = reviewSnapShot.getValue(RestaurentList.class);
                    foodPrice newRow = new foodPrice(Addreview.getId(),Addreview.getName(),Addreview.getAddress());
                    list.add(newRow);
                }

                AdapterHome adapter = new AdapterHome(Home.this,list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null &&  networkInfo.isConnected();
    }


    private  void displayMassege(String massege)
    {
        Toast.makeText(this, massege, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}
