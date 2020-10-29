package com.example.myapl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapl.tasks.downloadAndSetImage;

public class UserInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        Bundle arguments = getIntent().getExtras();
        String avaurl = arguments.get("avaurl").toString();
        String userid = arguments.get("userid").toString();
        String username = arguments.get("username").toString();
        ImageView ava = (ImageView)findViewById(R.id.iv_userinfo_ava);
        TextView idsnik = (TextView) findViewById(R.id.tv_userinfo_id);
        TextView userame = (TextView) findViewById(R.id.tv_userinfo_username);
        Thread setImage = new Thread(new downloadAndSetImage(ava,avaurl));
        setImage.start();
        idsnik.setText("id: "+userid);
        userame.setText(username);
    }
}
