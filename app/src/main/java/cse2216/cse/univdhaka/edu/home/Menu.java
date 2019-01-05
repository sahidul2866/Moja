package cse2216.cse.univdhaka.edu.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private Button menuButton;
    private Button reviewButton;
    private Button infoButton;
    private Intent intent;

    private ListView listViewMenu;
    private List<foodPrice> listMenu;

    TextView title;
    TextView details;
    RatingBar star;
    Button submit;
    Button add_button;
    Button add_submit;
    TextView add_name;
    TextView add_price;
    DatabaseReference databaseReview;
    DatabaseReference databaseMenu;

    ListView ReviewListView;
    List<AddReview> reviewList;

    LinearLayout add_layout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        menuButton = findViewById(R.id.Menu);
        reviewButton = findViewById(R.id.Reviews);
        infoButton = findViewById(R.id.Info);
        add_button = findViewById(R.id.add_button);
        add_submit = findViewById(R.id.add_submit);

        title = (TextView) findViewById(R.id.review_title);
        details = (TextView) findViewById(R.id.review_details);
        star = (RatingBar) findViewById(R.id.star);
        submit = (Button) findViewById(R.id.review_submit);
        add_name = (TextView) findViewById(R.id.add_name);
        add_price = (TextView) findViewById(R.id.add_price);

        menuButton.setOnClickListener(this);
        reviewButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);

        submit.setOnClickListener(this);
        add_button.setOnClickListener(this);
        add_submit.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        databaseMenu = FirebaseDatabase.getInstance().getReference(bundle.getString("resName") + "Menu");
        databaseReview = FirebaseDatabase.getInstance().getReference(bundle.getString("resName") + "Review");

        listViewMenu=findViewById(R.id.menuListID);
        ReviewListView = findViewById(R.id.ReviewListView);

        listMenu = new ArrayList<>();
        reviewList = new ArrayList<>();

        add_button.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listMenu.clear();

                for(DataSnapshot reviewSnapShot: dataSnapshot.getChildren()){
                    foodPrice food = reviewSnapShot.getValue(foodPrice.class);
                    listMenu.add(food);
                }

                CustomAdapter adapter = new CustomAdapter(Menu.this,listMenu);
                listViewMenu.setAdapter(adapter);

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.Menu) {

            LinearLayout layout = (LinearLayout) findViewById(R.id.review_layout);
            layout.setVisibility(LinearLayout.GONE);
            LinearLayout Mlayout = (LinearLayout) findViewById(R.id.menu_layout);
            Mlayout.setVisibility(LinearLayout.VISIBLE);
        }

        if(view.getId()==R.id.Reviews){
           //intent = new Intent(Menu.this,Review.class);
           // startActivity(intent);

            LinearLayout layout = (LinearLayout) findViewById(R.id.review_layout);
            layout.setVisibility(LinearLayout.VISIBLE);
             LinearLayout Mlayout = (LinearLayout) findViewById(R.id.menu_layout);
            Mlayout.setVisibility(LinearLayout.GONE);

            databaseReview.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                    reviewList.clear();

                    for(DataSnapshot reviewSnapShot1: dataSnapshot1.getChildren()){
                        AddReview Addreview = reviewSnapShot1.getValue(AddReview.class);
                        reviewList.add(Addreview);
                    }

                    ReviewList adapter = new ReviewList(Menu.this,reviewList);
                    ReviewListView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        else if(view.getId() == R.id.review_submit) {
           Toast.makeText(Menu.this, "Done", Toast.LENGTH_LONG).show();
            this.review();
       }
        else if(view.getId()==R.id.Info){
            intent = new Intent(Menu.this,Info.class);
            startActivity(intent);
        }

        else if(view.getId()==R.id.add_button){
            add_layout = (LinearLayout) findViewById(R.id.add_layout);
            add_layout.setVisibility(View.VISIBLE);
        }

        else if(view.getId() == R.id.add_submit) {
            Toast.makeText(Menu.this, "Done", Toast.LENGTH_LONG).show();
            this.addItem();
        }


    }

    public void review(){
        String review_title = title.getText().toString().trim();
        Float review_star = star.getRating();
        String review_details = details.getText().toString().trim();
        Toast.makeText(Menu.this,"Review",Toast.LENGTH_LONG).show();
        if (!TextUtils.isEmpty(title.getText())) {
            String id = databaseReview.push().getKey();
            AddReview newRow = new AddReview(id, review_title, review_star, review_details);
            databaseReview.child(id).setValue(newRow);
        } else {
            Toast.makeText(Menu.this, "Title Can't be Empty", Toast.LENGTH_LONG).show();
        }
    }

    public void addItem(){
        String name = add_name.getText().toString().trim();
        String price = add_price.getText().toString().trim();
        if (!TextUtils.isEmpty(add_name.getText())) {
            String id = databaseMenu.push().getKey();
            foodPrice newRow = new foodPrice(id, name,price);
            databaseMenu.child(id).setValue(newRow);
        } else {
            Toast.makeText(Menu.this, "Title Can't be Empty", Toast.LENGTH_LONG).show();
        }

        add_layout.setVisibility(View.GONE);
    }

}
