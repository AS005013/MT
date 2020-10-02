package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageButton button;
        if (convertView == null) {
            button = new ImageButton(context);
            button.setImageResource(R.drawable.pic0);
            button.setClickable(false);
            button.setFocusable(false);
            button.setFocusableInTouchMode(false);
        } else {
            button = (ImageButton) convertView;
        }
        button.setId(position);

        return button;
    }
}