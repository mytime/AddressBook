package com.jikexueyuan.addressbook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 适配器：listView
 */
public class ContactAdapter extends BaseAdapter {

    private Context context;
    private List<ContactBean> contacts;

    public ContactAdapter(Context context, List<ContactBean> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public ContactBean getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_contact,null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ContactBean contact = getItem(position);
        holder.tv_name.setText(contact.getName());
        holder.tv_phone.setText(contact.getPhone());
        return convertView;
    }

    static class ViewHolder{
        TextView tv_name;
        TextView tv_phone;
    }
}
