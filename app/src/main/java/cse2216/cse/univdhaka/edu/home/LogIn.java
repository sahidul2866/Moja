package cse2216.cse.univdhaka.edu.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity implements View.OnClickListener{

    TextView User;
    TextView Password;
    Button Login;
    Button Register;

    DatabaseReference databaseLogin;

    String name;
    String pass;

    long timer;
    Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        User = (TextView) findViewById(R.id.userLogId);
        Password = (TextView) findViewById(R.id.passLogId);
        Login = (Button) findViewById(R.id.login_btn);
        Register = (Button) findViewById(R.id.register_btn);

        Login.setOnClickListener(this);
        Register.setOnClickListener(this);

        databaseLogin = FirebaseDatabase.getInstance().getReference("UsersInfo");
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.login_btn) {
            if(!isConnected()) Toast.makeText(LogIn.this,"Network Unavailable",Toast.LENGTH_LONG).show();
            name = User.getText().toString();
            pass = Password.getText().toString();
            databaseLogin.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                    int success=0;

                    for(DataSnapshot reviewSnapShot1: dataSnapshot1.getChildren()){
                        AddRegister addRegister = reviewSnapShot1.getValue(AddRegister.class);
                        if(addRegister.getUname().equals(name) && addRegister.getPass().equals(pass)) {
                            SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user",name);
                            editor.putString("type",addRegister.getType());
                            editor.putString("resName",addRegister.getResName());
                            editor.putBoolean("checker",true);
                            editor.commit();

                            Intent intent = new Intent(LogIn.this,Home.class);
                            intent.putExtra("name",name);
                            intent.putExtra("type", addRegister.getType());
                            startActivity(intent);
                            success = 1;
                            break;
                        }
                    }
                    if(success==0) Toast.makeText(LogIn.this, "Log In Failed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if (view.getId()==R.id.register_btn){
            Intent intent = new Intent(LogIn.this,RegisterActivity.class);
            startActivity(intent);
        }
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
            backToast = Toast.makeText(LogIn.this,"Press back again to exit",Toast.LENGTH_LONG);
            backToast.show();
        }
        timer = System.currentTimeMillis();
    }

    public boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null &&  networkInfo.isConnected();
    }
}
