package cse2216.cse.univdhaka.edu.home;

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

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    String[] spin;
    private Spinner spinner;
    TextView username,password,confirm_password,phone_no,address,restaurentName,restaurentAddress;
    DatabaseReference databaseRegister;
    DatabaseReference databaseLogin;
    LinearLayout layout;
    DatabaseReference databaseHome;

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

        spin=getResources().getStringArray(R.array.spin);
        spinner=findViewById(R.id.spinnerId);
        ArrayAdapter<String> spinAdapter=new ArrayAdapter<String>(this,R.layout.spinner,R.id.spinnerSampleId,spin);
        spinner.setAdapter(spinAdapter);
        layout = findViewById(R.id.adminextra);


        Button createButton = (Button) findViewById(R.id.create_btn);
        createButton.setOnClickListener(this);

        databaseRegister = FirebaseDatabase.getInstance().getReference("UsersInfo");
        databaseLogin = FirebaseDatabase.getInstance().getReference("LoginInfo");
        databaseHome = FirebaseDatabase.getInstance().getReference("Home");
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.create_btn){
            String uname = username.getText().toString();
            String pass = password.getText().toString();
            String con_pass = confirm_password.getText().toString();
            String phn = phone_no.getText().toString();
            String addr = address.getText().toString();
            String id = databaseRegister.push().getKey();
            if(pass.equals(con_pass)  ) {
                String type = spinner.getSelectedItem().toString();
                if (type.equals("Admin")) {
                    layout.setVisibility(View.VISIBLE);
                    String resName = restaurentName.getText().toString().trim();
                    String resAddress = restaurentAddress.getText().toString().trim();
                    AddRegister newRow = new AddRegister(id, uname, pass, phn, addr, resName, resAddress);
                    databaseRegister.child(id).setValue(newRow);
                    if (!resName.equals("")) {
                        String id1 = databaseHome.push().getKey();
                        databaseHome.child(id1).setValue(new foodPrice(id1, resName, resAddress));
                    }
                }
            else if(type.equals("User")){
                    layout.setVisibility(View.GONE);
                    AddRegister newRow = new AddRegister(id, uname, pass, phn, addr);
                    databaseRegister.child(id).setValue(newRow);
                }
            }
        }

    }
}
