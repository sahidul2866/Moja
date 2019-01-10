package cse2216.cse.univdhaka.edu.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogIn extends AppCompatActivity implements View.OnClickListener{

    TextView User;
    TextView Password;
    Button Login;
    Button Register;

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
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login_btn) {
        //Do check
        }
        else if (view.getId()==R.id.register_btn){
            Intent intent = new Intent(LogIn.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}
