package com.example.jay.androidfpj;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jay on 2017/6/20.
 */

public class Stock extends AppCompatActivity {
    public static String[] stock_name = new String[50];
    public static String[] up = new String[50];
    ArrayList<String> list=new ArrayList<String>();
    private String input_num;
    private ArrayAdapter<String> listAdapter;
    private String stock_num = "";

    private ListView lst;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock);

        lst = (ListView) findViewById(R.id.stock_info);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("NUM");
        for (int i = 0;i < str.length(); i++){
            if (str.charAt(i)>=48 && str.charAt(i)<=57){
                stock_num+=str.charAt(i);
            }
        }

        new doit().execute();
        lst.setAdapter(listAdapter);

    }
    public class doit extends AsyncTask<Void,Void,Void> {
        Elements price;
        Elements title;
        Elements buy;
        Elements news;


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://tw.stock.yahoo.com/q/q?s="+stock_num).get();

                price = doc.select("b");
                title = doc.select("table").select("td").select("a");
                buy = doc.select("td[bgcolor='#FFFfff']");
                news = doc.select("div.bd.quote").select("a");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            stock_name = title.text().split("\\s+");
            up  = buy.text().split("\\s+");
            list.clear();
            list.add("股票名稱:"+stock_name[1]+"\n\n"+
                    "股價:"+price.text()+"\n"+
                    "時間:"+up[0]+"\n"+
                    "買進:"+up[2]+"\n"+
                    "賣出:"+up[3]+"\n"+
                    "漲跌:"+up[4]+"\n"+
                    "張:"+up[5]+"\n"
            );

            String news_href[]  = {news.get(0).attr("href"),news.get(1).attr("href"),news.get(2).attr("href")};
            listAdapter.notifyDataSetChanged();
        }
    }
}
