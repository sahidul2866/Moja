package cse2216.cse.univdhaka.edu.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity  {

    Button button;
    private ListView listView;
    private List<foodPrice> list;
    private Intent intent;

    DatabaseReference databaseHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseHome = FirebaseDatabase.getInstance().getReference("Home");

        listView=findViewById(R.id.HomeList);

        String id = databaseHome.push().getKey();
        foodPrice newRow = new foodPrice(id, "Delhi Darbar Khilgaon","45");
        databaseHome.child(id).setValue(newRow);

        list = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                foodPrice price =list.get(i);
                intent = new Intent(Home.this,Menu.class);
                intent.putExtra("resName",price.getFoodName());
                startActivity(intent);
                Toast.makeText(Home.this,price.getFoodName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        databaseHome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();

                for(DataSnapshot reviewSnapShot: dataSnapshot.getChildren()){
                    foodPrice Addreview = reviewSnapShot.getValue(foodPrice.class);
                    list.add(Addreview);
                }

                AdapterHome adapter = new AdapterHome(Home.this,list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
