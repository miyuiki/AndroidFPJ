package com.example.jay.androidfpj;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Jay on 2017/6/13.
 */

public class Account extends AppCompatActivity{

    private Button create_account;
    private Button login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        login = (Button) findViewById(R.id.login_btn);
        create_account = (Button) findViewById(R.id.create_btn);

        login.setOnClickListener(loginListener);
        create_account.setOnClickListener(create_accountListener);
    }
    private Button.OnClickListener loginListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(Account.this,Login.class);
            startActivity(intent);
        }
    };
    private Button.OnClickListener create_accountListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {

            Intent intent = new Intent();
            intent.setClass(Account.this,Create.class);
            startActivity(intent);
        }
    };
}
