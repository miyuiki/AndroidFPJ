package com.example.jay.androidfpj;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jay on 2017/6/13.
 */

public class Login extends AppCompatActivity {

    private static final String FILENAME = "account.txt";
    private Button login, create;
    private EditText account, password;
    private SharedPreferences sp;
    private String[] info ;
    public static boolean logined = false;
    public static String user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button) findViewById(R.id.loginbtn);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(loginListener);
    }
    private Button.OnClickListener loginListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            FileInputStream fp = null;
            BufferedInputStream bf = null;
            StringBuffer sb = new StringBuffer();
            try {
                fp = openFileInput(FILENAME);
                bf = new BufferedInputStream(fp);
                byte[] buffbyte = new byte[20];
                do {
                    int flag = bf.read(buffbyte);
                    if (flag == -1)
                        break;
                    else {
                        sb.append(new String(buffbyte));
                        info = sb.toString().split("\\n");
                        user = info[2];
                    }
                }while (true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (info[0].equals(account.getText().toString()) && info[1].equals(password.getText().toString())){
                Toast.makeText(getApplicationContext(), "登入成功，歡迎你"+info[2], Toast.LENGTH_SHORT).show();
                logined = true;
                Intent intent = new Intent();
                intent.setClass(Login.this,MainActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(), "登入失敗", Toast.LENGTH_SHORT).show();

        }
    };
}
