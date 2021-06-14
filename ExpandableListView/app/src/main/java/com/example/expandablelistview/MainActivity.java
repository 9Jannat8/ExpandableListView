package com.example.expandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;

    private CustomAdapter customAdapter;
    List<String> listDataHeader;//sob header k aksathe rakhar jonno ai list declared hoyeche..
    HashMap<String, List<String>> listDataChild;//child gulo rakhar jonno hashMap use hoyeche..<header, child>

    private int lastExpandablePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareListData();

       expandableListView = findViewById(R.id.expandableLVId);
       customAdapter = new CustomAdapter(this, listDataHeader, listDataChild);
       expandableListView.setAdapter(customAdapter);

       expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
           @Override
           public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
               String groupName = listDataHeader.get(groupPosition);

               Toast.makeText(getApplicationContext(), groupName, Toast.LENGTH_SHORT).show();
               return false;
           }
       });

       expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
           @Override
           public void onGroupCollapse(int groupPosition) {
               String groupName = listDataHeader.get(groupPosition);

               Toast.makeText(getApplicationContext(), groupName + " is collapsed", Toast.LENGTH_SHORT).show();
           }
       });

       expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
           @Override
           public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

               String childString = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
               Toast.makeText(getApplicationContext(), childString, Toast.LENGTH_SHORT).show();

               return false;
           }
       });

       expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
           @Override
           public void onGroupExpand(int groupPosition) {
               if (lastExpandablePosition != -1 && lastExpandablePosition != groupPosition)
               {
                   expandableListView.collapseGroup(lastExpandablePosition);
               }
               lastExpandablePosition = groupPosition;
           }
       });

    }

    public void prepareListData() {
        String[] headerString = getResources().getStringArray(R.array.abbreviations_list_header);
        String[] childString = getResources().getStringArray(R.array.description);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        //akhne akta akta kore header headerString theke listDataHeader String a add hobe
        //akhne akta akta kore child childString theke listDataChild String a add hobe
        //a kajta akta loop er modde korte hobe
        for(int i=0; i < headerString.length; i++)
        {
            //adding header data
            listDataHeader.add(headerString[i]);
            //adding child data
            List<String> child = new ArrayList<>();//child data khuje neyar jonno list
            child.add(childString[i]);//child add kora holo
            listDataChild.put(listDataHeader.get(i), child); //prottekta header er jonno ak akta child set kora holof
        }
    }
}