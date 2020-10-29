package com.example.myapl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapl.tasks.getUserList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapl.utils.NetworkUtils.generateURL;
import static com.example.myapl.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;
    private Button favouritesButton;
    private RecyclerView itemsList;
    // идентификатор запроса
    Intent intent = new Intent();
    static final String AGE_KEY = "AGE";
    static final String ACCESS_MESSAGE="ACCESS_MESSAGE";
    private static  final int REQUEST_ACCESS_TYPE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Связываем компонент, объявляем менеджер слоев, и устанавливаем его
        itemsList = findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        itemsList.setLayoutManager(layoutManager);

        //Связываем переменные и кнопки в xml
        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search);
        favouritesButton = findViewById(R.id.b_favourites);

        //Создаем новую прослушку, и привязываем её к кнопке
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL generatedURL = generateURL("getFollowers",searchField.getText().toString());
                Log.d("mLog","url = " + generatedURL);
                Thread getuserlist = new Thread(new getUserList(getApplicationContext(),itemsList,generatedURL,"getFollowers"));
                getuserlist.start();
            }
        };
        //Привязываем прослушку к кнопке
        searchButton.setOnClickListener(onClickListener);

        //Создаем новую прослушку, и привязываем её к кнопке
        View.OnClickListener fonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(), Stars.class);
                intent.putExtra(AGE_KEY, searchField.getText().toString());
                startActivityForResult(intent, REQUEST_ACCESS_TYPE);

            }
        };
        //Привязываем прослушку к кнопке
        favouritesButton.setOnClickListener(fonClickListener);
    }
    @Override
    protected void onResume()
    {
        URL generatedURL = generateURL("getFollowers",searchField.getText().toString());
        Thread getuserlist = new Thread(new getUserList(getApplicationContext(),itemsList,generatedURL,"getFollowers"));
        getuserlist.start();
        super.onResume();
    }
}





