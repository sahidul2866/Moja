package cse2216.cse.univdhaka.edu.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    private String user;
    private String type,resName;
    private android.support.v7.widget.Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Intent intent,intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_about);

        final Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user");
        type = bundle.getString("type");
        resName = bundle.getString("resName");

        toolbar = findViewById(R.id.toolbarID);
        navigationView = findViewById(R.id.navigationViewID);
        drawerLayout = findViewById(R.id.drawerLayoutID);

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
                    intent1 = new Intent(AboutActivity.this,Home.class);
                    drawerLayout.closeDrawers();
                    startActivity(intent1);
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_pastOrderID)
                {
                    displayMassege("Your Cart is selected");
                    drawerLayout.closeDrawers();
                    if(type.equals("User")) {
                        intent1 = new Intent(AboutActivity.this, CartActivity.class);
                        intent1.putExtra("resName",resName);
                        intent1.putExtra("user", user);
                        intent1.putExtra("type",type);
                        startActivity(intent1);
                    }
                    else {
                        intent1 = new Intent(AboutActivity.this, AdminCart.class);
                        intent1.putExtra("resName",resName);
                        intent1.putExtra("user", user);
                        intent1.putExtra("type",type);
                        startActivity(intent1);
                    }
                    return  true;
                }else  if(menuItem.getItemId() == R.id.nav_profileID)
                {
                    displayMassege("Profile is selected");
                    drawerLayout.closeDrawers();
                    intent1 = new Intent(AboutActivity.this, ActivityProfile.class);
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
                    intent = new Intent(AboutActivity.this,LogIn.class);
                    startActivity(intent);
                    return  true;
                }


                return false;
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
}
