package com.example.myapl;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapl.tasks.getUserList;
import com.example.myapl.utils.DBHelper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapl.utils.NetworkUtils.generateURL;

public class Stars extends Activity {
    private RecyclerView itemsList;
    DBHelper dbHelper = new DBHelper(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String age = extras.getString(MainActivity.AGE_KEY);
        }
        //Связываем компонент, объявляем менеджер слоев, и устанавливаем его
        itemsList = findViewById(R.id.rv_list_2);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        itemsList.setLayoutManager(layoutManager);

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //Read
        List<Integer> userIds = new ArrayList<Integer>();
        Cursor cursor = database.query(DBHelper.TABLE_FAVOUTRITES,null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_USER_ID);
            do {
                userIds.add(cursor.getInt(idIndex));
                Log.d("mLog","id = " + cursor.getInt(idIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog","O rows");
        }
        cursor.close();
        String userIdcom = "";
        if(userIds.size() > 0) {
            userIdcom = String.valueOf(userIds.get(0));
            if(userIds.size() > 1) {
                for(int i = 1;i<userIds.size();i++) {
                    userIdcom = userIdcom + ", " + userIds.get(i);
                }
            }
        }
        URL generatedURL = generateURL("getUsers",userIdcom);
        Log.d("mLog","url = " + generatedURL);

        Thread getuserlist = new Thread(new getUserList(getApplicationContext(),itemsList,generatedURL,"getUsers"));
        getuserlist.start();


    }
}
