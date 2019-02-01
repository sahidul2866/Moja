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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCart extends AppCompatActivity {

    private String user;
    private String type,resName;
    private android.support.v7.widget.Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Intent intent,intent1;


    ListView UserCartListView;
    List<Orders> UserCartList;
    DatabaseReference databaseOrders,databaseRegister;
    String address,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_admincart);


        final Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user");
        type = bundle.getString("type");
        resName = bundle.getString("resName");

        toolbar = findViewById(R.id.toolbarID);
        navigationView = findViewById(R.id.navigationViewID);
        drawerLayout = findViewById(R.id.drawerLayoutID);
        UserCartListView = findViewById(R.id.admincart);
        resName = bundle.getString("resName");

        TextView HeaderName,HeaderMobile;
        View headerLayout = navigationView.getHeaderView(0);
        HeaderName = headerLayout.findViewById(R.id.headername);
        HeaderMobile = headerLayout.findViewById(R.id.headermobile);
        HeaderMobile.setText(bundle.getString("mobile"));
        HeaderName.setText(user);
        setSupportActionBar(toolbar);

        final ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.nav_restaurantID)
                {
                    displayMassege("Restaurant is selected");
                    intent1 = new Intent(AdminCart.this,Home.class);
                    drawerLayout.closeDrawers();
                    startActivity(intent1);
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_pastOrderID)
                {
                    displayMassege("Your Cart is selected");
                    drawerLayout.closeDrawers();
                    if(type.equals("User")) {
                        intent1 = new Intent(AdminCart.this, CartActivity.class);
                        intent1.putExtra("resName",resName);
                        intent1.putExtra("user", user);
                        intent1.putExtra("type",type);
                        startActivity(intent1);
                    }
                    else {

                    }
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_profileID)
                {
                    displayMassege("Profile is selected");
                    drawerLayout.closeDrawers();
                    intent1 = new Intent(AdminCart.this, ActivityProfile.class);
                    intent1.putExtra("resName",resName);
                    intent1.putExtra("user", user);
                    intent1.putExtra("type",type);
                    startActivity(intent1);
                    return  true;
                }
                else  if(menuItem.getItemId() == R.id.nav_aboutUsID)
                {
                    displayMassege("About_Us is selected");
                    drawerLayout.closeDrawers();
                    intent1 = new Intent(AdminCart.this, AboutActivity.class);
                    intent1.putExtra("resName",resName);
                    intent1.putExtra("user", user);
                    intent1.putExtra("type",type);
                    startActivity(intent1);
                    return  true;
                }
                else  if(menuItem.getItemId() == R.id.nav_signOutID)
                {
                    displayMassege("sign Out is selected");
                    drawerLayout.closeDrawers();
                    SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user","");
                    editor.putString("type","");
                    editor.putBoolean("checker",false);
                    editor.commit();
                    intent = new Intent(AdminCart.this,LogIn.class);
                    startActivity(intent);
                    return  true;
                }


                return false;
            }
        });


        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        databaseRegister = FirebaseDatabase.getInstance().getReference("UsersInfo");


        UserCartList = new ArrayList<>();

        UserCartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("list view");
            }
        });

    }

    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        databaseOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserCartList.clear();
                isConnected();
                for(DataSnapshot reviewSnapShot: dataSnapshot.getChildren()) {
                    Orders food = reviewSnapShot.getValue(Orders.class);
                    if (food.getReName() != null && food.getReName().equals(resName)) {
                        
                        UserCartList.add(new Orders(food.getId(), food.getUser(), food.getName(), food.getQuantity(), food.getAddress(), food.getMobile()));
                    }
                }

                AdminCartAdapter adapter = new AdminCartAdapter(AdminCart.this,UserCartList);
                UserCartListView.setAdapter(adapter);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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

    public void isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (!(networkInfo != null &&  networkInfo.isConnected())){
            Toast.makeText(AdminCart.this,"Network Unavailable",Toast.LENGTH_LONG).show();

        }
    }
}
