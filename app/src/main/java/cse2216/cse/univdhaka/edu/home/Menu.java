package cse2216.cse.univdhaka.edu.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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

import static java.util.List.*;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    int flag;
    private Button menuButton;
    private Button reviewButton;
    private Button infoButton;
    private Intent intent;

    String user;
    String typee;
    String resName;

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
    DatabaseReference databaseHome;
    DatabaseReference databaseOrder;
    DatabaseReference databaseUser;

    ListView ReviewListView;
    List<AddReview> reviewList;

    LinearLayout add_layout,Mlayout,Rlayout,Ilayout;

    Toolbar toolbar;

    TextView InfoName;
    TextView InfoAddress;
    TextView InfoCuisines;
    TextView InfoMin;
    String address,mobile;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        toolbar = findViewById(R.id.toolbarID);

        setSupportActionBar(toolbar);

        final android.app.ActionBar actionBar =getActionBar();

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

        final Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user");
        typee = bundle.getString("type");
        resName = bundle.getString("resName");
        address = bundle.getString("address");
        mobile = bundle.getString("mobile");
        System.out.println(user+typee+resName);

        databaseMenu = FirebaseDatabase.getInstance().getReference(bundle.getString("resName") + "Menu");
        databaseReview = FirebaseDatabase.getInstance().getReference(bundle.getString("resName") + "Review");
        databaseHome = FirebaseDatabase.getInstance().getReference("Home");
        databaseOrder = FirebaseDatabase.getInstance().getReference("Orders");
        databaseUser = FirebaseDatabase.getInstance().getReference("UsersInfo");

        listViewMenu=findViewById(R.id.menuListID);
        ReviewListView = findViewById(R.id.ReviewListView);

        listMenu = new ArrayList<>();
        reviewList = new ArrayList<>();

        Mlayout = (LinearLayout) findViewById(R.id.menu_layout);
        Rlayout = (LinearLayout) findViewById(R.id.review_layout);
        Ilayout = (LinearLayout) findViewById(R.id.info_layout);

        InfoName = (TextView) findViewById(R.id.infoname);
        InfoAddress = (TextView) findViewById(R.id.infoaddress);
        InfoCuisines = (TextView) findViewById(R.id.infocuisines);
        InfoMin = (TextView) findViewById(R.id.infodelivery);

        databaseHome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot reviewSnapShot: dataSnapshot.getChildren()){
                    RestaurentList Addreview = reviewSnapShot.getValue(RestaurentList.class);
                    if(Addreview.getName()!=null && Addreview.getName().equals(resName)) {
                        InfoName.setText(Addreview.getName());
                        InfoAddress.setText(Addreview.getAddress());
                        InfoCuisines.setText(Addreview.getCuisines());
                        InfoMin.setText(Addreview.getMinOrder());

                        if (Addreview.getAdmin().equals(user)) {
                            System.out.println(bundle.getString("type") + bundle.getString("user"));
                            add_button.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isConnected()) Toast.makeText(Menu.this,"Network Unavailable",Toast.LENGTH_LONG).show();
                if(!typee.equals("Admin")) {
                    foodPrice price = listMenu.get(i);

                    String id = databaseOrder.push().getKey();
                    Orders newRow = new Orders(id, user, price.getFoodName(), Double.parseDouble(price.getFoodPrice()), 1, Double.parseDouble(price.getFoodPrice()), resName,address,mobile);
                    databaseOrder.child(id).setValue(newRow);
                    Toast.makeText(Menu.this, "Item Added to the Cart", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(Menu.this, "You are an admin", Toast.LENGTH_LONG).show();
            }
        });


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

            Mlayout.setVisibility(LinearLayout.VISIBLE);
            Rlayout.setVisibility(LinearLayout.GONE);
            Ilayout.setVisibility(LinearLayout.GONE);
        }

        if(view.getId()==R.id.Reviews){
            Mlayout.setVisibility(LinearLayout.GONE);
            Rlayout.setVisibility(LinearLayout.VISIBLE);
            Ilayout.setVisibility(LinearLayout.GONE);

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
            Mlayout.setVisibility(LinearLayout.GONE);
            Rlayout.setVisibility(LinearLayout.GONE);
            Ilayout.setVisibility(LinearLayout.VISIBLE);
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

    public boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null &&  networkInfo.isConnected();
    }

}
