package com.example.mymenuapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MenuListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView lvMenu;
    TextView tvDate, tvTotalPrice;

    //Intent関係
    Intent intent;
    int intIntentDate;
    String strIntentMonth;

    //Calendar関係
    Calendar cl;
    DateFormat df;

    //Realm関係
    Realm realm;
    RealmResults<MenuDB> results; //オリジナルDB
    RealmResults<MenuDB> results2; //onItemClick用のDB
    ArrayAdapter<String> adapter;
    int intLength;
    ArrayList<String> menuList; //表示用DB
    //String[] lvStr; //カレンダー表示のビュー

    int intDayTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        lvMenu = findViewById(R.id.listViewMenu);
        tvDate = findViewById(R.id.textViewDate);
        tvTotalPrice = findViewById(R.id.textViewTotalPrice);

        //Calendar関係
        cl = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy年M月d日");

        intent = getIntent();
        Bundle bundle = intent.getExtras();

        strIntentMonth = bundle.getString(getString(R.string.intent_item_month));
        intIntentDate = bundle.getInt(getString(R.string.intent_item_date));
        tvDate.setText(strIntentMonth + (intIntentDate + 1) + "日");
        //tvDate.setText(df.format(intDate));
    }

    @Override
    protected void onResume() {
        super.onResume();

        realm = Realm.getDefaultInstance();
        String[] names = {"strDate", "strTime", "strMenuNumber"};
        Sort[] sorts = {Sort.ASCENDING, Sort.DESCENDING, Sort.ASCENDING};
        results = realm.where(MenuDB.class).sort(names, sorts).findAll();
        //results = realm.where(MenuDB.class).findAll().sort("strDate");

        intLength = results.size();
        menuList = new ArrayList<String>();

        for(int i = 0; i < intLength; i++) {

            //Dateが同じなら・・
            if(String.valueOf(strIntentMonth + (intIntentDate +1 ) + "日").equals(results.get(i).getStrDate())){

                //DBから見せたいデータを抽出
                menuList.add(results.get(i).getStrTime() +results.get(i).getStrMenuNumber()
                        + "：" + results.get(i).getStrMenuTitle() +results.get(i).getIntTotalPrice() + "円");

                //合計金額計算
                intDayTotalPrice += (results.get(i).getIntTotalPrice());
                tvTotalPrice.setText(String.valueOf(intDayTotalPrice));
            }
        }
        intDayTotalPrice = 0;

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuList);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(this);
        lvMenu.setOnItemLongClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(realm != null){
            realm.close();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //日付が同じデータを抽出し並べ替え
        String[] names = {"strDate", "strTime", "strMenuNumber"};
        Sort[] sorts = {Sort.ASCENDING, Sort.DESCENDING, Sort.ASCENDING};
        results2 = realm.where(MenuDB.class).equalTo("strDate", String.valueOf(strIntentMonth + (intIntentDate+1) + "日")).sort(names, sorts).findAll();
        //選択したアイテム
        MenuDB selectedDB = results2.get(position);

        String strSelectedDate = selectedDB.getStrDate();
        String strSelectedTime = selectedDB.getStrTime();
        String strSelectedNumber = selectedDB.getStrMenuNumber();
        String strSelectedMenuTitle = selectedDB.getStrMenuTitle();
        String strSelectedItem1 = selectedDB.getStrItem1();
        String strSelectedItem2 = selectedDB.getStrItem2();
        String strSelectedItem3 = selectedDB.getStrItem3();
        String strSelectedItem4 = selectedDB.getStrItem4();
        String strSelectedItem5 = selectedDB.getStrItem5();
        String strSelectedItem6 = selectedDB.getStrItem6();
        String strSelectedItem7 = selectedDB.getStrItem7();
        int intSelectedPrice1 = selectedDB.getIntPrice1();
        int intSelectedPrice2 = selectedDB.getIntPrice2();
        int intSelectedPrice3 = selectedDB.getIntPrice3();
        int intSelectedPrice4 = selectedDB.getIntPrice4();
        int intSelectedPrice5 = selectedDB.getIntPrice5();
        int intSelectedPrice6 = selectedDB.getIntPrice6();
        int intSelectedPrice7 = selectedDB.getIntPrice7();
        int intSelectedTotalPrice = selectedDB.getIntTotalPrice();

        intent = new Intent(MenuListActivity.this, MenuActivity.class);
        intent.putExtra(getString(R.string.intent_key_status), getString(R.string.intent_status_change));
        intent.putExtra(getString(R.string.intent_key_date), strSelectedDate);
        intent.putExtra(getString(R.string.intent_key_time), strSelectedTime);
        intent.putExtra(getString(R.string.intent_key_number), strSelectedNumber);
        intent.putExtra(getString(R.string.intent_key_menuTitle), strSelectedMenuTitle);
        intent.putExtra(getString(R.string.intent_key_item1), strSelectedItem1);
        intent.putExtra(getString(R.string.intent_key_item2), strSelectedItem2);
        intent.putExtra(getString(R.string.intent_key_item3), strSelectedItem3);
        intent.putExtra(getString(R.string.intent_key_item4), strSelectedItem4);
        intent.putExtra(getString(R.string.intent_key_item5), strSelectedItem5);
        intent.putExtra(getString(R.string.intent_key_item6), strSelectedItem6);
        intent.putExtra(getString(R.string.intent_key_item7), strSelectedItem7);
        intent.putExtra(getString(R.string.intent_key_price1), intSelectedPrice1);
        intent.putExtra(getString(R.string.intent_key_price2), intSelectedPrice2);
        intent.putExtra(getString(R.string.intent_key_price3), intSelectedPrice3);
        intent.putExtra(getString(R.string.intent_key_price4), intSelectedPrice4);
        intent.putExtra(getString(R.string.intent_key_price5), intSelectedPrice5);
        intent.putExtra(getString(R.string.intent_key_price6), intSelectedPrice6);
        intent.putExtra(getString(R.string.intent_key_price7), intSelectedPrice7);
        intent.putExtra(getString(R.string.intent_key_totalPrice), intSelectedTotalPrice);
        intent.putExtra(getString(R.string.intent_key_position),position);
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        //日付が同じデータを抽出し並べ替え
        String[] names = {"strDate", "strTime", "strMenuNumber"};
        Sort[] sorts = {Sort.ASCENDING, Sort.DESCENDING, Sort.ASCENDING};
        results2 = realm.where(MenuDB.class).equalTo("strDate", String.valueOf(strIntentMonth + (intIntentDate+1) + "日")).sort(names, sorts).findAll();
        MenuDB selectedDB = results2.get(position);

        realm.beginTransaction();

        //DBからデータを削除
        selectedDB.deleteFromRealm();
        realm.commitTransaction();

        results2 = realm.where(MenuDB.class).equalTo("strDate", String.valueOf(strIntentMonth + (intIntentDate+1) + "日")).sort(names, sorts).findAll();

        //menuListからデータを削除
        menuList.remove(position);
        lvMenu.setAdapter(adapter);

        //trueにすることで今回の長押し処理が終了
        return true;
    }

}
