package com.example.myapl.utils;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String VK_API_BASE_URL  = "https://api.vk.com/";
    private static final String VK_API_USERS_GETFOLLOWERS  = "method/users.getFollowers";
    private static final String VK_API_USERS  = "method/users.get";

    private static final String VK_API_PARAM_USER_ID  = "user_id";
    private static final String VK_API_PARAM_FIELDS  = "fields";

    private static final String VK_API_PARAM_USER  = "user_ids";

    private static final String VK_API_PARAM_VERSION  = "v";
    private static final String VK_API_PARAM_TOKEN  = "access_token";

    public static URL generateURL(String queryType, String userId){
        Uri builtUri;
        if(queryType.equals("getFollowers")) {
            builtUri = Uri.parse(VK_API_BASE_URL+VK_API_USERS_GETFOLLOWERS)
                    .buildUpon()
                    .appendQueryParameter(VK_API_PARAM_USER_ID,userId)
                    .appendQueryParameter(VK_API_PARAM_FIELDS,"photo_100,first_name")
                    .appendQueryParameter(VK_API_PARAM_VERSION,"5.89")
                    .appendQueryParameter(VK_API_PARAM_TOKEN,"8a4ae14f8a4ae14f8a4ae14f7c8a3e789388a4a8a4ae14fd5dc09b976dad69975ce5671")
                    .build();
        } else if(queryType.equals("getUsers")) {
            builtUri = Uri.parse(VK_API_BASE_URL+VK_API_USERS)
                    .buildUpon()
                    .appendQueryParameter(VK_API_PARAM_USER,userId)
                    .appendQueryParameter(VK_API_PARAM_FIELDS,"photo_100")
                    .appendQueryParameter(VK_API_PARAM_VERSION,"5.89")
                    .appendQueryParameter(VK_API_PARAM_TOKEN,"8a4ae14f8a4ae14f8a4ae14f7c8a3e789388a4a8a4ae14fd5dc09b976dad69975ce5671")
                    .build();
        } else {
            builtUri = null;
        }

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

