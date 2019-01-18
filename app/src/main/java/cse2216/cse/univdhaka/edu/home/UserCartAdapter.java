package cse2216.cse.univdhaka.edu.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserCartAdapter extends ArrayAdapter<AddUserCart> {


    List<AddUserCart> list;
    Context context;
    private LayoutInflater inflater;
    TextView Quantity,Total,ItemPrice;
    Double total=0.0,itemprice=0.0;
    int quantity =0;

    Button Increaser,Decreaser;
    ImageView Remover;
    TextView foodName,foodPrice,foodQuantity,foodTotal;
    AddUserCart Item;

    UserCartAdapter(Context context, List<AddUserCart> list)
    {
        super(context, R.layout.cart_sample,list);
        this.context = context;
        this.list = list;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_sample,parent,false);
        }
        foodName= convertView.findViewById(R.id.usercartname);
        foodPrice= convertView.findViewById(R.id.usercartprice);
        foodQuantity= convertView.findViewById(R.id.usercartquantity);
        foodTotal= convertView.findViewById(R.id.usercarttotal);
        Item =list.get(position);
        foodName.setText(Item.getFoodName());
        foodPrice.setText(Item.getPrice()+"");
        foodQuantity.setText(Item.getQuantity()+"");
        foodTotal.setText(Item.getTotal()+"");

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.Increaser = (Button)convertView.findViewById(R.id.incrementButtonID);
        viewHolder.Decreaser = (Button)convertView.findViewById(R.id.decrementButtonID);
        viewHolder.Remover = (ImageView) convertView.findViewById(R.id.itemRemoveID);

        viewHolder.Increaser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item.setQuantityIncrease();
                foodQuantity.setText(Item.getQuantity()+"");
                Item.setTotalIncrease();
                foodTotal.setText(Item.getTotal()+"");
            }
        });

        viewHolder.Decreaser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item.setQuantityDecrease();
                foodQuantity.setText(Item.getQuantity()+"");
                Item.setTotalDecrease();
                foodTotal.setText(Item.getTotal()+"");
            }
        });

        viewHolder.Remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
            }
        });
        convertView.setTag(viewHolder);

        return convertView;
    }
}
