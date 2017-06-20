package com.example.jay.androidfpj;

import android.content.DialogInterface;
import android.icu.util.RangeValueIterator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jay on 2017/6/17.
 */

public class Search extends AppCompatActivity {

    private EditText search;
    private Button dosearch;
    private ListView listView;
    ArrayList<String> list=new ArrayList<String>();
    private ArrayAdapter<String> listAdapter;
    private String input_num;
    private TextView news_link1,news_link2,news_link3;
    private String yahoo_stock="https://tw.stock.yahoo.com";
    private String FILENAME = "fav.txt";
    private String t;
    public static ArrayList<String> fav = new ArrayList<>();
    public static String[] stock_name = new String[50];
    public static String[] up = new String[50];
    public static String prizzz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        search = (EditText) findViewById(R.id.search_bar);
        dosearch = (Button) findViewById(R.id.do_search);
        listView = (ListView)findViewById(R.id.list_view);
        news_link1 = (TextView)findViewById(R.id.tvlink);
        news_link2 = (TextView)findViewById(R.id.tvlink2);
        news_link3 = (TextView)findViewById(R.id.tvlink3);
        dosearch.setOnClickListener(doSearchListener);

        listView = (ListView)findViewById(R.id.list_view);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Login.logined){
                    new AlertDialog.Builder(Search.this).setTitle("確認頁面")
                            .setMessage("是否將此加入我的最愛?")
                            .setPositiveButton("好哇", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fav.add(t);
                                }
                            })
                            .setNegativeButton("不要",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .show();
                }
                else {
                    new AlertDialog.Builder(Search.this).setTitle("提醒")
                            .setMessage("請先登入")
                            .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .show();
                }
            }
        });

    }
    public Button.OnClickListener doSearchListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            input_num = search.getText().toString();
            new doit().execute();
            listView.setAdapter(listAdapter);
        }
    };

    public class doit extends AsyncTask<Void,Void,Void>{
        Elements price;
        Elements title;
        Elements buy;
        Elements news;


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://tw.stock.yahoo.com/q/q?s="+input_num).get();

                price = doc.select("b");
                title = doc.select("table").select("td").select("a");
                buy = doc.select("td[bgcolor='#FFFfff']");
                news = doc.select("div.bd.quote").select("a");
                prizzz = price.text();

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
            t = stock_name[1];

            String news_href[]  = {news.get(0).attr("href"),news.get(1).attr("href"),news.get(2).attr("href")};
            news_link1.setText(Html.fromHtml("<a href="+yahoo_stock+news_href[0]+">" + news.get(0).text()));
            news_link1.setMovementMethod(LinkMovementMethod.getInstance());
            news_link2.setText(Html.fromHtml("<a href="+yahoo_stock+news_href[1]+">" + news.get(1).text()));
            news_link2.setMovementMethod(LinkMovementMethod.getInstance());
            news_link3.setText(Html.fromHtml("<a href="+yahoo_stock+news_href[2]+">" + news.get(2).text()));
            news_link3.setMovementMethod(LinkMovementMethod.getInstance());
            listAdapter.notifyDataSetChanged();
        }
    }
}
