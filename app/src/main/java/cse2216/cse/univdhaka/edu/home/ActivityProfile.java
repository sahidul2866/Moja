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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityProfile extends AppCompatActivity {

    private DatabaseReference databaseLogin;
    TextView uname, name, address, mobile, email;

    private String user,mobilee;
    private String type,resName;
    private android.support.v7.widget.Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Intent intent,intent1;

    private TextView HeaderName,HeaderMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_profile);

        final Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user");
        type = bundle.getString("type");
        resName = bundle.getString("resName");

        toolbar = findViewById(R.id.toolbarID);
        navigationView = findViewById(R.id.navigationViewID);
        drawerLayout = findViewById(R.id.drawerLayoutID);
        resName = bundle.getString("resName");
        mobilee = bundle.getString("mobile");

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
                    intent1 = new Intent(ActivityProfile.this,Home.class);
                    drawerLayout.closeDrawers();
                    startActivity(intent1);
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_pastOrderID)
                {
                    displayMassege("Your Cart is selected");
                    drawerLayout.closeDrawers();
                    if(type.equals("User")) {
                        intent1 = new Intent(ActivityProfile.this, CartActivity.class);
                        intent1.putExtra("resName",resName);
                        intent1.putExtra("user", user);
                        intent1.putExtra("type",type);
                        intent1.putExtra("mobile",mobilee);
                        startActivity(intent1);
                    }
                    else {
                        intent1 = new Intent(ActivityProfile.this, AdminCart.class);
                        intent1.putExtra("resName",resName);
                        intent1.putExtra("user", user);
                        intent1.putExtra("type",type);
                        intent1.putExtra("mobile",mobilee);
                        startActivity(intent1);
                    }
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_profileID)
                {
                    displayMassege("Profile is selected");
                    drawerLayout.closeDrawers();
                    return  true;
                }
                else  if(menuItem.getItemId() == R.id.nav_aboutUsID)
                {
                    displayMassege("About_Us is selected");
                    drawerLayout.closeDrawers();
                    intent1 = new Intent(ActivityProfile.this, AboutActivity.class);
                    intent1.putExtra("resName",resName);
                    intent1.putExtra("user", user);
                    intent1.putExtra("type",type);

                    intent1.putExtra("mobile",mobilee);
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
                    intent = new Intent(ActivityProfile.this,LogIn.class);
                    startActivity(intent);
                    return  true;
                }


                return false;
            }
        });


        databaseLogin = FirebaseDatabase.getInstance().getReference("UsersInfo");

        uname = findViewById(R.id.profileuname);
        name = findViewById(R.id.profilename);
        mobile = findViewById(R.id.profilemobile);
        address = findViewById(R.id.profileaddress);
        email = findViewById(R.id.profileemail);

        databaseLogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                int success = 0;
                isConnected();
                for (DataSnapshot reviewSnapShot1 : dataSnapshot1.getChildren()) {
                    AddRegister addRegister = reviewSnapShot1.getValue(AddRegister.class);
                    if (addRegister.getUname().equals(user)) {
                        uname.setText(addRegister.getUname());
                        name.setText(addRegister.getUname());
                        mobile.setText(addRegister.getMobile());
                        address.setText(addRegister.getAddress());
                    }
                }
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
            Toast.makeText(ActivityProfile.this,"Network Unavailable",Toast.LENGTH_LONG).show();

        }
    }
}