package cse2216.cse.univdhaka.edu.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity{

    ListView UserCartListView;
    List<AddUserCart> UserCartList;
    DatabaseReference databaseOrders;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercart);

        final Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user");
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");

        UserCartListView = findViewById(R.id.usercart);
        UserCartList = new ArrayList<>();

        UserCartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("list view");
            }
        });
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        databaseOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserCartList.clear();

                for(DataSnapshot reviewSnapShot: dataSnapshot.getChildren()){
                    Orders food = reviewSnapShot.getValue(Orders.class);
                    if(food.getUser()!=null && food.getUser().equals(user)) {
                        UserCartList.add(new AddUserCart(food.getId(), food.getName(), food.getPrice(), food.getQuantity(), food.getTotal()));
                    }
                }

                UserCartAdapter adapter = new UserCartAdapter(CartActivity.this,UserCartList);
                UserCartListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}


