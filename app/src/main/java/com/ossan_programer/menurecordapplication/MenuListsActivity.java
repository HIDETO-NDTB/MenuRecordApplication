package com.ossan_programer.menurecordapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class MenuListsActivity extends AppCompatActivity {

    TextView tv_Month;
    ListView lv_MenuLists;

    //リストビューに表示する文字列を格納するアダプター
    ArrayAdapter<String> adapter;
    //リストビューを表示する文字列
    String[] lvStr;

    //カレンダー関係
    Calendar cl;
    int int_Days;
    DateFormat df;

    //Realm関係
    Realm realm;
    RealmResults<MenuDB> results;
    int intLength;
    ArrayList<String> menuList;
    //int int_DayTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lists);

        tv_Month = findViewById(R.id.textViewMonth);
        lv_MenuLists = findViewById(R.id.listViewMenuLists);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Realm関係
        realm = Realm.getDefaultInstance();
        results = realm.where(MenuDB.class).findAll().sort("strDate");
        intLength = results.size();
        menuList = new ArrayList<String>();

        //カレンダーのオブジェクトを取得
        cl = Calendar.getInstance();

        //月の日数取得
        int_Days = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
        //リストビューを表示する文字列を設定
        lvStr = new String[int_Days];

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvStr) {

            @SuppressLint("SampleDateFormat")
            public View getView(int pos, View convView, ViewGroup parent) {
                //日付の表示形式を指定
                //df = new SimpleDateFormat("dd日（E）");
                df = new SimpleDateFormat("yyyy年M月d日");

                //今月1日から表示
                cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), 1);

                //テキストビューに年・月を表示
                tv_Month.setText(cl.get(Calendar.YEAR) + "年" + (cl.get(Calendar.MONTH)+1) +"月");

                //今日の日付に行数を足し表示する日付を取得
                cl.add(Calendar.DATE, pos);

                //取得した日付をtmpTvに格納
                TextView tmpTv = new TextView(getBaseContext());

                for(int i = 0; i < intLength; i++) {
                    //int_DayTotalPrice += results.get(i).getIntTotalPrice();

                }



                    //うまくいかない
//                    if(df.format(cl.getTime())  == results.get(i).getStrDate()) {
//                        int_DayTotalPrice += results.get(i).getIntTotalPrice();
//                    }


                //tmpTvに日付と合計金額を格納 + int_DayTotalPrice +"円"
                tmpTv.setText(df.format(cl.getTime()));

                //テキストサイズ設定
                tmpTv.setTextSize(16);

                //テキストカラー設定
                tmpTv.setTextColor(Color.BLACK);

                //日曜日ならばテキストカラー変更
                if(cl.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    tmpTv.setTextColor(Color.RED);
                }

                convView = tmpTv;
                return  convView;

            }
        };


        //リストビューにアダプターを設定
        lv_MenuLists.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(realm != null) {
            realm.close();
        }
    }
}
