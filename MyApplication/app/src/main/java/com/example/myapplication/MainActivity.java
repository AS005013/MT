package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toast mToast;
    public int idItemFirst = 0;
    public ImageButton btnFirst;
    public int idItemSecond = 0;
    public ImageButton btnSecond;
    boolean canopen = true;
    public int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game();
    }

    public List fill() {
        List<Product> products = new ArrayList<Product>();
        int[] fas = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8};
        for(int i =1;i<9;i++) {
            products.add(new Product(fas[i-1], i));
            products.add(new Product(fas[i-1], i));
        }
        Collections.shuffle(products);
        return products;
    }

    public void game() {
        final List<Product> products = fill();
        final GridView gridView;
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ProductAdapter(this, products));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Product item = (Product)parent.getAdapter().getItem(position);
                ImageButton pressedButton = parent.findViewById(position);
                if(canopen == true) {
                    pressedButton.setImageResource(item.getpicItem());
                }
                if(idItemFirst > 0 && canopen == true) {
                    idItemSecond = item.getidItem();
                    btnSecond = pressedButton;
                    if(idItemFirst == idItemSecond) {
                        if(mToast != null) { mToast.cancel();}
                        mToast = Toast.makeText(getApplicationContext(), "Правильно", Toast.LENGTH_LONG); mToast.show(); //creates the new toast.
                        idItemFirst = 0; idItemSecond = 0;
                        btnFirst.setVisibility(View.INVISIBLE); btnSecond.setVisibility(View.INVISIBLE);
                        score++;
                        if(score == 8) {
                            score = 0;
                            game();
                        }
                    } else {
                        canopen = false;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btnFirst.setImageResource(R.drawable.pic0);btnSecond.setImageResource(R.drawable.pic0);
                                canopen = true;
                                idItemFirst = 0;idItemSecond = 0;
                            }
                        }, 1500);
                    }
                } else {
                    if(canopen == true) {
                        idItemFirst = item.getidItem();
                        btnFirst = pressedButton;
                    }
                }
            }
        });
    }

}