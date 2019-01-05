package cse2216.cse.univdhaka.edu.home;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ReviewList extends ArrayAdapter<AddReview> {
    private Activity context;
    private List<AddReview> list;

    public ReviewList(Activity context,List<AddReview> list) {
        super(context, R.layout.reviewlist,list);
        this.context = context;
        this.list = list ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItems = inflater.inflate(R.layout.reviewlist,null,true);

        TextView ShowTitle = (TextView) listViewItems.findViewById(R.id.showTitle);
        RatingBar ShowStar = (RatingBar) listViewItems.findViewById(R.id.showStar);
        TextView ShowDetails = (TextView) listViewItems.findViewById(R.id.showDetails);

        AddReview Review = list.get(position);
        ShowTitle.setText(Review.getTitle());
        ShowStar.setRating(Review.getStar());
        ShowStar.setIsIndicator(true);
        ShowDetails.setText(Review.getDetails());

        return listViewItems;
    }
}
