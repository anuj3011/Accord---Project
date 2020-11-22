package com.example.accord.ServiceMainMenu;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.example.accord.ExpandableListAdapter;
import com.example.accord.UserMainMenu.OrderPage;
import com.example.accord.R;

public class ServicesOption extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_option);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                    /*Toast.makeText(
                            getApplicationContext(),
                            listDataHeader.get(groupPosition)
                                    + " : "
                                    + listDataChild.get(
                                    listDataHeader.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                            .show();*/
                List<String> childCategory = listDataChild.get(listDataHeader.get(groupPosition));


                String category = listDataHeader.get(groupPosition) + ":"+childCategory.get(childPosition);

                Intent intent = new Intent(getApplicationContext(), OrderPage.class);
                intent.putExtra("category",category);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Electrical");
        listDataHeader.add("Wood Work");
        listDataHeader.add("Plumbing");
        listDataHeader.add("Cleaning");
        listDataHeader.add("Others");

        // Adding child data
        List<String> Category1 = new ArrayList<String>();
        Category1.add("AC Servicing");
        Category1.add("Washing Machine Servicing");
        Category1.add("TV Servicing");
        Category1.add("Basic Electrical Services ");
        Category1.add("Other");

        List<String> Category2 = new ArrayList<String>();
        Category2.add("Carpentry");


        List<String> Category3 = new ArrayList<String>();
        Category3.add("Toiletries");
        Category3.add("Water Pipes");
        Category3.add("Sewage");

        List<String> Category4 = new ArrayList<String>();
        Category4.add("House Cleaning");
        Category4.add("Laundry");
        Category4.add("Car Cleaning");

        List<String> Category5 = new ArrayList<String>();
        Category5.add("Pest Control");
        Category5.add("Grooming");
        Category5.add("Pet Services");
        Category5.add("Barber");
        Category5.add("Chef/Cook");

        listDataChild.put(listDataHeader.get(0), Category1);
        listDataChild.put(listDataHeader.get(1), Category2);
        listDataChild.put(listDataHeader.get(2), Category3);
        listDataChild.put(listDataHeader.get(3), Category4);
        listDataChild.put(listDataHeader.get(4), Category5);
    }
}



