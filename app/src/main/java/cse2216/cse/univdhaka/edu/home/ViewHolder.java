package cse2216.cse.univdhaka.edu.home;

import android.widget.Button;
import android.widget.ImageView;

public class ViewHolder {
    Button Increaser,Decreaser;
    ImageView Remover;

    public ViewHolder() {
    }

    public ViewHolder(Button increaser, Button decreaser, ImageView remover) {
        Increaser = increaser;
        Decreaser = decreaser;
        Remover = remover;
    }

    public Button getIncreaser() {
        return Increaser;
    }

    public Button getDecreaser() {
        return Decreaser;
    }

    public ImageView getRemover() {
        return Remover;
    }
}
