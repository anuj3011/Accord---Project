package com.example.accord;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import android.os.Bundle;

public class ServicesOption extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    Toast.makeText(getApplicationContext(),
                            listDataHeader.get(groupPosition) + " Expanded",
                            Toast.LENGTH_SHORT).show();
                }
            });

            // Listview Group collasped listener
            expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            listDataHeader.get(groupPosition) + " Collapsed",
                            Toast.LENGTH_SHORT).show();

                }
            });

            // Listview on child click listener
            expListView.setOnChildClickListener(new OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    // TODO Auto-generated method stub
                    Toast.makeText(
                            getApplicationContext(),
                            listDataHeader.get(groupPosition)
                                    + " : "
                                    + listDataChild.get(
                                    listDataHeader.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            });
        }

        private void prepareListData() {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
            listDataHeader.add("Category1");
            listDataHeader.add("Category2");
            listDataHeader.add("Category3");
            listDataHeader.add("Category4");
            listDataHeader.add("Category5");

            // Adding child data
            List<String> Category1 = new ArrayList<String>();
            Category1.add("SubCategory");
            Category1.add("SubCategory");
            Category1.add("SubCategory");
            Category1.add("SubCategory");
            Category1.add("SubCategory");

            List<String> Category2 = new ArrayList<String>();
            Category2.add("SubCategory");
            Category2.add("SubCategory");
            Category2.add("SubCategory");
            Category2.add("SubCategory");
            Category2.add("SubCategory");
            Category2.add("SubCategory");

            List<String> Category3 = new ArrayList<String>();
            Category3.add("SubCategory");
            Category3.add("SubCategory");
            Category3.add("SubCategory");
            Category3.add("SubCategory");
            Category3.add("SubCategory");

            List<String> Category4 = new ArrayList<String>();
            Category4.add("SubCategory");
            Category4.add("SubCategory");
            Category4.add("SubCategory");
            Category4.add("SubCategory");
            Category4.add("SubCategory");

            List<String> Category5 = new ArrayList<String>();
            Category5.add("SubCategory");
            Category5.add("SubCategory");
            Category5.add("SubCategory");
            Category5.add("SubCategory");
            Category5.add("SubCategory");

            listDataChild.put(listDataHeader.get(0),Category1);
            listDataChild.put(listDataHeader.get(1), Category2);
            listDataChild.put(listDataHeader.get(2), Category3);
            listDataChild.put(listDataHeader.get(3), Category4);
            listDataChild.put(listDataHeader.get(4), Category5);
        }
}



