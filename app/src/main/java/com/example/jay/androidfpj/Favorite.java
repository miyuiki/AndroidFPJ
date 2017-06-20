package com.example.jay.androidfpj;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jay on 2017/6/13.
 */

public class Favorite extends AppCompatActivity{

    private ListView listView;
    private Button clean;
    ArrayList<String> list=new ArrayList<String>();
    private ArrayAdapter listAdapter;
    private static final String FILENAME = "fav.txt";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);
        listView = (ListView)findViewById(R.id.list_view);
        clean = (Button) findViewById(R.id.clean_btn);

        clean.setOnClickListener(cleanListener);

        if (Login.logined){
            listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,Search.fav);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(Favorite.this, Stock.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("NUM",listView.getItemAtPosition(position).toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
        else{
            list.add("請先登入");
            listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
            listView.setAdapter(listAdapter);
        }
    }
    public Button.OnClickListener cleanListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            Search.fav.clear();
            listView.setAdapter(null);
        }
    };
}
