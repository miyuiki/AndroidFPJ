package com.example.jay.androidfpj;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jay on 2017/6/13.
 */

public class Create extends AppCompatActivity {
    private EditText new_account, new_password, name, email;
    private Button enter;
    private static final String FILENAME = "account.txt";
    public static boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        new_account = (EditText) findViewById(R.id.new_account);
        new_password = (EditText) findViewById(R.id.new_password);
        name = (EditText) findViewById(R.id.name) ;
        email = (EditText) findViewById(R.id.email);
        enter = (Button) findViewById(R.id.check);
        enter.setOnClickListener(enterListener);

    }

    private Button.OnClickListener enterListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            String newa = new_account.getText().toString();
            String newp = new_password.getText().toString();
            String newname = name.getText().toString();
            String newemail = email.getText().toString();

            FileOutputStream fp = null;
            BufferedOutputStream bf = null;
            try{
                fp = openFileOutput(FILENAME, MODE_PRIVATE );
                bf = new BufferedOutputStream(fp);
                bf.write(newa.getBytes());
                bf.write("\n".getBytes());
                bf.write(newp.getBytes());
                bf.write("\n".getBytes());
                bf.write(newname.getBytes());
                bf.write("\n".getBytes());
                bf.write(newemail.getBytes());
                bf.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new_account.setText("");
            new_password.setText("");
            name.setText("");
            email.setText("");
            flag = true;

            finish();
        }
    };
}