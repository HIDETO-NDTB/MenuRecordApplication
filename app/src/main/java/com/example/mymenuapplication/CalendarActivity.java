package com.example.mymenuapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class CalendarActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    TextView tvMonth, tvMonthTotalPrice;
    ListView lvCalendar;
    Spinner spMonth;
    Button btMonthChange, btnMonthTotalPrice;

    //Spinner用
    //ArrayAdapter<String> spAdapterYear;
    ArrayAdapter<String> spAdapterMonth;
    String strSPMonth; //Spinnerで選択したものを入れる箱
    int intYear;
    int intMonth;

    //Calendar関係
    Calendar cl;
    int intDays;
    DateFormat df;
    DateFormat df2; //合計金額表示の為の日付表示
    String[] lvStr; //Calendar表示のビュー
    ArrayAdapter<String> adapter;

    //Realm関係
    Realm realm;
    RealmResults<MenuDB> results;
    int intLength;
    int intDayTotalPrice = 0;

    int intMonthTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        tvMonth = findViewById(R.id.textViewMonth);
        tvMonthTotalPrice = findViewById(R.id.textViewMonthTotalPrice);
        lvCalendar = findViewById(R.id.listViewCalendar);
        spMonth = findViewById(R.id.spinnerMonth);
        btMonthChange = findViewById(R.id.buttonMonthChange);
        btnMonthTotalPrice = findViewById(R.id.buttonMonthTotalPrice);
    }

    @Override
    protected void onResume() {
        super.onResume();

        realm = Realm.getDefaultInstance();

        //Spinner用
        spAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        for(intYear = 2020; intYear <=2021; intYear++) {
            for (intMonth = 1; intMonth <=12; intMonth++) {
                spAdapterMonth.add(intYear +"年" + intMonth + "月");
            }
        }
        spMonth.setAdapter(spAdapterMonth);

        //Calendarのオブジェクト取得
        cl = Calendar.getInstance();
        //月の日数取得
        intDays = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
        //リストビューに表示する文字列を設定,
        lvStr = new String[intDays];

        //Realm関係
        //String[] names = {"strDate", "strTime", "strMenuNumber"};
        //Sort[] sorts = {Sort.ASCENDING, Sort.DESCENDING, Sort.ASCENDING};
        results = realm.where(MenuDB.class).findAll();
        intLength = results.size();

        //月合計計算
        for (int i = 0; i < intLength; i++) {
            //月が同じなら・・
            if (tvMonth.getText().equals(results.get(i).getStrDate().substring(0,7)) || tvMonth.getText().equals(results.get(i).getStrDate().substring(0,8))) {

                //月の合計金額計算
                intMonthTotalPrice += results.get(i).getIntTotalPrice();
            }
        }
        tvMonthTotalPrice.setText(String.valueOf(intMonthTotalPrice));
        intMonthTotalPrice = 0;


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvStr) {

            @SuppressLint("SampleDateFormat")
            public View getView(int pos, View convView, ViewGroup parent) {

                //日付の表示形式を設定
                df = new SimpleDateFormat("dd日（E）");
                df2 = new SimpleDateFormat("yyyy年M月d日");
                //今月1日から表示
                cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), 1);
                //テキストビューに年・月を表示
                tvMonth.setText(cl.get(Calendar.YEAR) + "年" + (cl.get(Calendar.MONTH) + 1) + "月");
                //今日の日付に行数を足し表示する日付を取得
                cl.add(Calendar.DATE, pos);
                //取得した日付をtmpTvに格納
                TextView tmpTv = new TextView(getBaseContext());
                //tmpTvに日付を格納
                tmpTv.setText(df.format(cl.getTime()));
                //テキストサイズ設定
                tmpTv.setTextSize(16);
                //テキストカラー設定
                tmpTv.setTextColor(Color.BLACK);
                //日曜日ならばテキストカラー変更
                if (cl.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    tmpTv.setTextColor(Color.RED);
                }

                for (int i = 0; i < intLength; i++) {

                    //Dateが同じなら・・
                    if ((df2.format(cl.getTime()).equals(results.get(i).getStrDate()))) {

                        //合計金額計算
                        intDayTotalPrice += results.get(i).getIntTotalPrice();

                        if (intDayTotalPrice > 0) {
                            tmpTv.setText(df.format(cl.getTime()) + ":" + " " + String.valueOf(intDayTotalPrice) + "円");

//                       } else {
//                           tmpTv.setText(df.format(cl.getTime()) + ":" + "0" + "円");
                        }
                    }
                }
                intDayTotalPrice = 0;

                convView = tmpTv;
                return convView;
            }
        };

        lvCalendar.setAdapter(adapter);
        lvCalendar.setOnItemClickListener(this);

        //Button処理
        btMonthChange.setOnClickListener(this);
        btnMonthTotalPrice.setOnClickListener(this);

    }

    @Override
    public void onClick (final View v){

        int intId = v.getId();
        switch (intId) {
            case R.id.buttonMonthChange:
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvStr) {

                    @SuppressLint("SampleDateFormat")
                    public View getView(int pos, View convView, ViewGroup parent) {

                        //日付の表示形式を設定
                        df = new SimpleDateFormat("dd日（E）");
                        df2 = new SimpleDateFormat("yyyy年M月d日");
                        //選択した月の表示
                        int intPosition = spMonth.getSelectedItemPosition();
                        if(intPosition <= 12) {
                            cl.set(2020, intPosition, 1);
                        } else {
                            cl.set(2021, intPosition-12, 1);
                        }
                        //テキストビューに年・月を表示
                        strSPMonth = (String) spMonth.getSelectedItem();
                        tvMonth.setText(strSPMonth);
                        //今日の日付に行数を足し表示する日付を取得
                        cl.add(Calendar.DATE, pos);
                        //取得した日付をtmpTvに格納
                        TextView tmpTv = new TextView(getBaseContext());
                        //tmpTvに日付を格納
                        tmpTv.setText(df.format(cl.getTime()));
                        //テキストサイズ設定
                        tmpTv.setTextSize(16);
                        //テキストカラー設定
                        tmpTv.setTextColor(Color.BLACK);
                        //日曜日ならばテキストカラー変更
                        if (cl.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                            tmpTv.setTextColor(Color.RED);
                        }

                        for (int i = 0; i < intLength; i++) {

                            //Dateが同じなら・・
                            if ((df2.format(cl.getTime()).equals(results.get(i).getStrDate()))) {

                                //合計金額計算
                                intDayTotalPrice += (results.get(i).getIntTotalPrice());

                                if (intDayTotalPrice > 0) {
                                    tmpTv.setText(df.format(cl.getTime()) + ":" + " " + String.valueOf(intDayTotalPrice) + "円");
//                        } else {
//                            tmpTv.setText(df.format(cl.getTime()) + ":" + "0" + "円");
                                }

                            }
                        }
                        intDayTotalPrice = 0;

                        convView = tmpTv;
                        return convView;
                    }
                };

                lvCalendar.setAdapter(adapter);

                break;

            case R.id.buttonMonthTotalPrice:
                //月合計計算
                for (int i = 0; i < intLength; i++) {
                    //月が同じなら・・
                    if (tvMonth.getText().equals(results.get(i).getStrDate().substring(0,7)) || tvMonth.getText().equals(results.get(i).getStrDate().substring(0,8))) {

                        //月の合計金額計算
                        intMonthTotalPrice += results.get(i).getIntTotalPrice();
                    }
                }
                tvMonthTotalPrice.setText(String.valueOf(intMonthTotalPrice));
                intMonthTotalPrice = 0;
                break;
        }

    }

        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){

            Intent intent = new Intent(CalendarActivity.this, MenuListActivity.class);
            strSPMonth = (String) spMonth.getSelectedItem();
            intent.putExtra(getString(R.string.intent_item_month), tvMonth.getText());
            intent.putExtra(getString(R.string.intent_item_date), position);
            startActivity(intent);
        }

    @Override
    protected void onPause () {
        super.onPause();

        if (realm != null) {
            realm.close();
        }
    }

    }