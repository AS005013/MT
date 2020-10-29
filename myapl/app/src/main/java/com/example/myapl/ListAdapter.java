package com.example.myapl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapl.tasks.downloadAndSetImage;
import com.example.myapl.tasks.getUserList;
import com.example.myapl.utils.DBHelper;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.example.myapl.utils.NetworkUtils.generateURL;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<Object> USERLIST;
    private Context mcontext;
    private String queryType;
    // идентификатор запроса
    Intent intent = new Intent();

    public ListAdapter(Context mcontext, List<Object> listtst,String queryType) {
        this.mcontext = mcontext;
        this.USERLIST = listtst;
        this.queryType = queryType;
    }

    @NonNull
    @Override
    public  ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForListItem,parent,false);
        ListViewHolder viewHolder = new ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        User users = (User) USERLIST.get(position);
        try {
            holder.bind(users.getName(),users.getImageUrl(),users.getId(),position);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return USERLIST.size();
    }

    //Обертка для элемента списка
    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView listItem_ID; // Первый компонент
        ImageView listItem_image;
        Button listItem_button;
        CardView card;
        DBHelper dbHelper = new DBHelper(mcontext,null,null,1);
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            listItem_ID = itemView.findViewById(R.id.tv_list_item_id);
            listItem_image = itemView.findViewById(R.id.iv_list_item);
            listItem_button = itemView.findViewById(R.id.b_list_item_fav);
            card = itemView.findViewById(R.id.cv_item);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void bind(String test, String test2, int id, int pos) throws IOException {
            listItem_button.setBackgroundResource(R.drawable.test);
            listItem_button.setTextColor(Color.parseColor("#495464"));
            listItem_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bbbfca")));
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            //Read
            Cursor cursor = database.query(DBHelper.TABLE_FAVOUTRITES,null,null,null,null,null,null);
            if(cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_USER_ID);
                do {
                    if(cursor.getInt(idIndex) == id) {
                        listItem_button.setTextColor(Color.parseColor("#F0C808"));
                        listItem_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#495464")));
                    }
                    Log.d("mLog","id = " + cursor.getInt(idIndex));
                } while (cursor.moveToNext());
            } else {
                Log.d("mLog","O rows");
            }
            cursor.close();
            listItem_ID.setText(test);
            Thread setImage = new Thread(new downloadAndSetImage(listItem_image,test2));
            setImage.start();
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ButtonHexColor = String.format("#%06X", (0xFFFFFF & listItem_button.getCurrentTextColor()));
                    if(ButtonHexColor.equals("#495464") ) {
                        listItem_button.setTextColor(Color.parseColor("#F0C808"));
                        listItem_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#495464")));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBHelper.KEY_USER_ID, id);
                        database.insert(DBHelper.TABLE_FAVOUTRITES, null,contentValues);
                    } else {
                        if(queryType.equals("getUsers")) {
                            USERLIST.remove(pos);
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos,USERLIST.size());
                        } else {
                            listItem_button.setTextColor(Color.parseColor("#495464"));
                            listItem_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#bbbfca")));
                        }
                        database.delete(DBHelper.TABLE_FAVOUTRITES, "user_id=?", new String[]{Integer.toString(id)});

                    }
                    Toast toast = Toast.makeText(mcontext,
                            "test " + id, Toast.LENGTH_SHORT);
                    toast.show();

                }
            };
            //Привязываем прослушку к кнопке
            listItem_button.setOnClickListener(onClickListener);
            //Создаем новую прослушку, и привязываем её к кнопке
            View.OnClickListener fonClickListener2 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(mcontext, UserInfo.class);
                    intent.putExtra("avaurl", test2);
                    intent.putExtra("username", test);
                    intent.putExtra("userid", id);
                    mcontext.startActivity(intent);
                }
            };
            //Привязываем прослушку к кнопке
            card.setOnClickListener(fonClickListener2);
        }
    }
}
