package com.condorhero89.nightguardian.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.condorhero89.nightguardian.R;
import com.condorhero89.nightguardian.model.MyContact;

public class MyContactAdapter extends ArrayAdapter<MyContact> {
    private LayoutInflater inflater;

    public MyContactAdapter(Context context, List<MyContact> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            
            convertView = inflater.inflate(R.layout.my_contact_item, null);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            holder.imgImportant = (ImageView) convertView.findViewById(R.id.imgImportant);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        final MyContact myContact = getItem(position);
        holder.txtName.setText(myContact.getName());
        holder.txtPhoneNumber.setText(myContact.getPhoneNumber());
        if (myContact.isImportant()) {
            holder.imgImportant.setImageResource(R.drawable.heart);
        } else {
            holder.imgImportant.setImageResource(R.drawable.gray_heart);
        }
        
        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtPhoneNumber;
        ImageView imgImportant;
    }
}
