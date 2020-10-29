package com.example.myapl.tasks;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapl.ListAdapter;
import com.example.myapl.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapl.utils.NetworkUtils.getResponseFromURL;

public class getUserList implements Runnable {

    private RecyclerView itemsList;
    private URL url;
    private Context mcontext;
    private String queryType;

    public getUserList(Context mcontext, RecyclerView itemsList, URL url, String queryType) {
        this.queryType = queryType;
        this.mcontext = mcontext;
        this.itemsList = itemsList;
        this.url = url;
    }
    public List<Object> loadJSON(String queryType, URL url) {
        List<Object> testItems = new ArrayList<>();
        String response = null;
        try {
            response = getResponseFromURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(queryType.equals("getFollowers")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonResponse = jsonObject.getJSONObject("response");
                JSONArray jsonArray = jsonResponse.getJSONArray("items");
                JSONObject userInfo;
                int countFollowers =  Integer.parseInt(jsonResponse.getString("count"));
                for(int i=0;i<countFollowers;i++) {
                    userInfo= jsonArray.getJSONObject(i);
                    int id = Integer.parseInt(userInfo.getString("id"));
                    String name = userInfo.getString("first_name") +  " " + userInfo.getString("last_name");
                    String ava = userInfo.getString("photo_100");
                    User users = new User(id,name,ava);
                    testItems.add(users);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                JSONObject userInfo;
                int countFollowers =  jsonArray.length();
                for(int i=0;i<countFollowers;i++) {
                    userInfo= jsonArray.getJSONObject(i);
                    int id = Integer.parseInt(userInfo.getString("id"));
                    String name = userInfo.getString("first_name") +  " " + userInfo.getString("last_name");
                    String ava = userInfo.getString("photo_100");
                    User users = new User(id,name,ava);
                    testItems.add(users);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return testItems;
    }

    @Override
    public void run() {
        final List<Object> tet = loadJSON(queryType,url);
        itemsList.post(new Runnable() {
            @Override
            public void run() {
                ListAdapter listAdapter = new ListAdapter(mcontext,tet,queryType);
                itemsList.setAdapter(listAdapter);
            }
        });
    }

}

/*private RecyclerView itemsList;
    private URL url;
    private Context mcontext;

    public getUserList(Context mcontext, RecyclerView itemsList, URL url) {
        this.mcontext = mcontext;
        this.itemsList = itemsList;
        this.url = url;
    }
    public List<Object> loadJSON(URL url) {
        List<Object> testItems = new ArrayList<>();
        String response = null;
        try {
            response = getResponseFromURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(response);
            //JSONObject jsonResponse = jsonObject.getJSONObject("response");
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            JSONObject userInfo;
            int countFollowers = 1 ;
            for(int i=0;i<countFollowers;i++) {
                userInfo= jsonArray.getJSONObject(i);
                int id = Integer.parseInt(userInfo.getString("id"));
                String name = userInfo.getString("first_name") +  " " + userInfo.getString("last_name");
                String ava = userInfo.getString("photo_100");
                User users = new User(id,name,ava);
                testItems.add(users);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testItems;
    }

    @Override
    public void run() {
        final List<Object> tet = loadJSON(url);
        itemsList.post(new Runnable() {
            @Override
            public void run() {
                ListAdapter listAdapter = new ListAdapter(mcontext,tet);
                itemsList.setAdapter(listAdapter);
            }
        });
    }*/