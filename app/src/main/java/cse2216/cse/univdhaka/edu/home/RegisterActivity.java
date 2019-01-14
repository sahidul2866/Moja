package cse2216.cse.univdhaka.edu.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    String[] spin;
    private Spinner spinner;
    TextView username, password, confirm_password, phone_no, address, restaurentName, restaurentAddress, restaurentCuisines, restaurentMin;
    DatabaseReference databaseRegister;
    DatabaseReference databaseLogin;
    LinearLayout layout;
    DatabaseReference databaseHome;
    Intent intent;
    String uname;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.userRegId);
        password = findViewById(R.id.passRegId);
        confirm_password = findViewById(R.id.conPassRegId);
        phone_no = findViewById(R.id.phoneId);
        address = findViewById(R.id.addressId);
        restaurentName = findViewById(R.id.restaurentname);
        restaurentAddress = findViewById(R.id.restaurentaddress);
        restaurentCuisines = findViewById(R.id.cuisines);
        restaurentMin = findViewById(R.id.min_order);

        spin = getResources().getStringArray(R.array.spin);
        spinner = findViewById(R.id.spinnerId);
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, R.layout.spinner, R.id.spinnerSampleId, spin);
        spinner.setAdapter(spinAdapter);
        layout = findViewById(R.id.adminextra);


        Button createButton = (Button) findViewById(R.id.create_btn);
        createButton.setOnClickListener(this);

        databaseRegister = FirebaseDatabase.getInstance().getReference("UsersInfo");
        databaseHome = FirebaseDatabase.getInstance().getReference("Home");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_btn) {
             uname = username.getText().toString();
            String pass = password.getText().toString();
            String con_pass = confirm_password.getText().toString();
            String phn = phone_no.getText().toString();
            String addr = address.getText().toString();

            final String resName = restaurentName.getText().toString().trim();
            String resAddress = restaurentAddress.getText().toString().trim();
            String resCuisine = restaurentCuisines.getText().toString().trim();
            String resMinOrder = restaurentMin.getText().toString().trim();
            databaseRegister.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot reviewSnapShot1: dataSnapshot.getChildren()) {
                        AddRegister addRegister = reviewSnapShot1.getValue(AddRegister.class);
                        if(addRegister.getUname().equals(uname)){
                            flag=1;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if(flag == 1){
                Toast.makeText(RegisterActivity.this,"Username Already Exists",Toast.LENGTH_LONG).show();
                flag = 0;
            }

            else if(flag == 2){
                Toast.makeText(RegisterActivity.this,"Restaurant Name Already Exists",Toast.LENGTH_LONG).show();
                flag = 0;
            }

            else if (uname.equals("") || pass.equals("") || phn.equals("") || addr.equals("")) {
                Toast.makeText(RegisterActivity.this, "Field(s) can't be empty", Toast.LENGTH_SHORT).show();
            }
            else if (!pass.equals(con_pass))
                Toast.makeText(RegisterActivity.this, "Password didn't match", Toast.LENGTH_LONG).show();
            else if(pass.length()<6) {
                Toast.makeText(RegisterActivity.this,"Password length should be 6 or more",Toast.LENGTH_LONG).show();
            }
            else if (pass.equals(con_pass)) {
                String type = spinner.getSelectedItem().toString();
                if (type.equals("Admin")) {
                    layout.setVisibility(View.VISIBLE);

                    if (resName.equals("") || resName.equals("") || resName.equals("") || resName.equals("")) {
                        Toast.makeText(RegisterActivity.this, "Field(s) can't be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        String id1 = databaseHome.push().getKey();
                        databaseHome.child(id1).setValue(new RestaurentList(id1, resName, resAddress, resCuisine, resMinOrder,uname));
                        String id = databaseRegister.push().getKey();
                        AddRegister newRow = new AddRegister(id, uname, pass, phn, addr, resName, resAddress, type);
                        databaseRegister.child(id).setValue(newRow);
                        Toast.makeText(RegisterActivity.this,"Account Created",Toast.LENGTH_SHORT).show();
                        intent = new Intent(RegisterActivity.this, LogIn.class);
                        startActivity(intent);

                    }
                } else if (type.equals("User")) {
                    layout.setVisibility(View.GONE);
                    String id = databaseRegister.push().getKey();
                    AddRegister newRow = new AddRegister(id, uname, pass, phn, addr, type);
                    databaseRegister.child(id).setValue(newRow);
                    Toast.makeText(RegisterActivity.this,"Account Created",Toast.LENGTH_SHORT).show();
                    intent = new Intent(RegisterActivity.this, LogIn.class);
                    startActivity(intent);
                }
            }
        }
    }
}

