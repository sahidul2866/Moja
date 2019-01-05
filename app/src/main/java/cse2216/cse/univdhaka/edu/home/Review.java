package cse2216.cse.univdhaka.edu.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class Review extends AppCompatActivity implements View.OnClickListener {

    TextView title;
    TextView details;
    RatingBar star;
    Button submit;
    DatabaseReference databaseReview;

    ListView ReviewListView;
    List<AddReview> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        databaseReview = FirebaseDatabase.getInstance().getReference("Review");

        title = (TextView) findViewById(R.id.review_title);
        details = (TextView) findViewById(R.id.review_details);
        star = (RatingBar) findViewById(R.id.star);
        submit = (Button) findViewById(R.id.review_submit);
        ReviewListView = (ListView) findViewById(R.id.ReviewListView);

        reviewList = new ArrayList<>();

        submit.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        databaseReview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                reviewList.clear();

                for(DataSnapshot reviewSnapShot: dataSnapshot.getChildren()){
                    AddReview Addreview = reviewSnapShot.getValue(AddReview.class);
                    reviewList.add(Addreview);
                }

                ReviewList adapter = new ReviewList(Review.this,reviewList);
                ReviewListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }

}
