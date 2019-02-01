package cse2216.cse.univdhaka.edu.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdminCartAdapter extends ArrayAdapter<Orders> {
    List<Orders> list;
    Context context;
    private LayoutInflater inflater;

   public AdminCartAdapter(Context context,List<Orders> list)
   {
       super(context,R.layout.admincart_sample,list);
       this.context=context;
       this.list= list;
   }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.admincart_sample, parent, false);
        }
        TextView userName = convertView.findViewById(R.id.userNameID);
        TextView itemName = convertView.findViewById(R.id.adminItemID);
        TextView Quantity = convertView.findViewById(R.id.adminquantityID);
        TextView address = convertView.findViewById(R.id.adminaddressID);
        TextView phone_no = convertView.findViewById(R.id.adminPhoneID);

        Orders Item = list.get(position);
        userName.setText(Item.getUser());
        itemName.setText(Item.getName());
        Quantity.setText(Item.getQuantity()+"");
        address.setText(Item.getAddress());
        phone_no.setText(Item.getMobile());

        return convertView;
    }
}
