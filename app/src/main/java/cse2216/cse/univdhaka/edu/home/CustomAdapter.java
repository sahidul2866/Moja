package cse2216.cse.univdhaka.edu.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<foodPrice> {
    private List<foodPrice> list;
    Context context;
    private LayoutInflater inflater;

    CustomAdapter(Context context, List<foodPrice> list){
        super(context, R.layout.menu_sample_layout,list);
        this.context=context;
        this.list = list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_sample_layout,parent,false);
        }
        TextView textView= convertView.findViewById(R.id.menuId);
        TextView textView1=convertView.findViewById(R.id.priceId);

        foodPrice price =list.get(position);
        textView.setText(price.getFoodName());
        textView1.setText(price.getFoodPrice());
        return convertView;
    }
}
