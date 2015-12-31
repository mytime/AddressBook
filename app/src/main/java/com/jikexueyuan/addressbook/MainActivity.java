package com.jikexueyuan.addressbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn_add;
    private ListView lv_contacts;

    private ContactAdapter adapter;
    private List<ContactBean> contacts = new ArrayList<ContactBean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = (Button) findViewById(R.id.btn_add);
        lv_contacts = (ListView) findViewById(R.id.lv_contacts);

        adapter = new ContactAdapter(this,contacts);
        lv_contacts.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();

                setContactsData();
            }
        });

        lv_contacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ContactBean contact = adapter.getItem(position);
                showUpdateDialog(contact);

                setContactsData();

                return false;
            }
        });

        lv_contacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ContactBean contact = adapter.getItem(position);
                ContactManager.deleteContact(MainActivity.this,contact);

                setContactsData();

                return true;
            }
        });

        setContactsData();
    }

    private void setContactsData(){
        List<ContactBean> contacData =  ContactManager.getContacts(this);
        contacts.clear();
        contacts.addAll(contacData);
        adapter.notifyDataSetChanged();
    }
    //add
    private void showAddDialog(){
        View view = View.inflate(this, R.layout.dialog_contact, null);

        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);

        new AlertDialog.Builder(this)
                .setTitle("添加联系人")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactBean contact = new ContactBean();
                        contact.setName(et_name.getText()+"");
                        contact.setPhone(et_phone.getText() + "");

                        ContactManager.addContact(MainActivity.this,contact);
                        setContactsData();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }
    //update
    private void showUpdateDialog(final ContactBean oldContact){
        View view = View.inflate(this,R.layout.dialog_contact,null);

        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);

        et_name.setText(oldContact.getName());
        et_phone.setText(oldContact.getPhone());

        new AlertDialog.Builder(this)
                .setTitle("修改联系人")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactBean contact = new ContactBean();

                        contact.setRawContactId(oldContact.getRawContactId());
                        contact.setName(et_name.getText()+"");
                        contact.setPhone(et_phone.getText() + "");

                        ContactManager.updateContact(MainActivity.this, contact);
                        setContactsData();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }
}
