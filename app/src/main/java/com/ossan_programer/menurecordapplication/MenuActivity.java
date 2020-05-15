package com.ossan_programer.menurecordapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_Date, tv_Status;
    EditText et_MenuTitle;
    EditText et_Item1, et_Item2, et_Item3, et_Item4, et_Item5, et_Item6, et_Item7;
    EditText et_Price1, et_Price2, et_Price3, et_Price4, et_Price5, et_Price6, et_Price7;
    RadioGroup rg_Time;
    RadioButton rb_Breakfast, rb_Lunch, rb_Dinner;
    Spinner sp_Menu;
    Button btn_Calender, btn_MenuSubmit;

    int intId;

    //EditTextをint,Stringに変換する箱
    int int_Price1, int_Price2, int_Price3, int_Price4, int_Price5, int_Price6, int_Price7;
    int int_TotalPrice;
    String str_MenuTitle;
    String str_Item1, str_Item2, str_Item3, str_Item4, str_Item5, str_Item6, str_Item7;
    String str_MenuNumber;
    String str_Date;
    int int_Time;

    //Realm関係
    Realm realm;
    MenuDB menuDB;
    RealmResults<MenuDB> results;

    //spinner用
    ArrayAdapter<String> arrayAdapter;

    //Intent関係
    String strIntentStatus;
    String strIntentDate;
    String strIntentTime;
    String strIntentNumber;
    String strIntentMenuTitle;
    String strIntentItem1, strIntentItem2, strIntentItem3, strIntentItem4, strIntentItem5, strIntentItem6, strIntentItem7;
    int intIntentPrice1, intIntentPrice2, intIntentPrice3, intIntentPrice4, intIntentPrice5, intIntentPrice6, intIntentPrice7;
    int intIntentPosition;

    //Calendar関係
    Calendar cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tv_Date = findViewById(R.id.textViewDate);
        tv_Status = findViewById(R.id.textViewStatus);
        et_MenuTitle = findViewById(R.id.editTextMenuTitle);
        et_Item1 = findViewById(R.id.editTextItem1);
        et_Item2 = findViewById(R.id.editTextItem2);
        et_Item3 = findViewById(R.id.editTextItem3);
        et_Item4 = findViewById(R.id.editTextItem4);
        et_Item5 = findViewById(R.id.editTextItem5);
        et_Item6 = findViewById(R.id.editTextItem6);
        et_Item7 = findViewById(R.id.editTextItem7);
        et_Price1 = findViewById(R.id.editTextPrice1);
        et_Price2 = findViewById(R.id.editTextPrice2);
        et_Price3 = findViewById(R.id.editTextPrice3);
        et_Price4 = findViewById(R.id.editTextPrice4);
        et_Price5 = findViewById(R.id.editTextPrice5);
        et_Price6 = findViewById(R.id.editTextPrice6);
        et_Price7 = findViewById(R.id.editTextPrice7);

        rg_Time = findViewById(R.id.radioTime);
        rb_Breakfast = findViewById(R.id.radioBreakfast);
        rb_Lunch = findViewById(R.id.radioLunch);
        rb_Dinner = findViewById(R.id.radioDinner);
        sp_Menu = findViewById(R.id.spinnerMenu);
        btn_Calender = findViewById(R.id.button_Calendar);
        btn_MenuSubmit = findViewById(R.id.buttonMenuSubmit);

        tv_Status.setText(getString(R.string.intent_status_submit));
        btn_MenuSubmit.setText("メニューの登録");

    }

    @Override
    protected void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();

        cl = Calendar.getInstance();

        //MenuListから修正の場合
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        strIntentStatus = bundle.getString(getString(R.string.intent_key_status));
        tv_Status.setText(strIntentStatus);

        //ステータスが修正の場合
        if(strIntentStatus.equals(getString(R.string.intent_status_change))) {

            btn_MenuSubmit.setText("メニューの修正");

            //インテントを引継ぎ
            strIntentDate = bundle.getString(getString(R.string.intent_key_date));
            strIntentTime = bundle.getString(getString(R.string.intent_key_time));
            strIntentNumber = bundle.getString(getString(R.string.intent_key_number));
            strIntentMenuTitle = bundle.getString(getString(R.string.intent_key_menuTitle));
            strIntentItem1 = bundle.getString(getString(R.string.intent_key_item1));
            strIntentItem2 = bundle.getString(getString(R.string.intent_key_item2));
            strIntentItem3 = bundle.getString(getString(R.string.intent_key_item3));
            strIntentItem4 = bundle.getString(getString(R.string.intent_key_item4));
            strIntentItem5 = bundle.getString(getString(R.string.intent_key_item5));
            strIntentItem6 = bundle.getString(getString(R.string.intent_key_item6));
            strIntentItem7 = bundle.getString(getString(R.string.intent_key_item7));
            intIntentPrice1 = bundle.getInt(getString(R.string.intent_key_price1));
            intIntentPrice2 = bundle.getInt(getString(R.string.intent_key_price2));
            intIntentPrice3 = bundle.getInt(getString(R.string.intent_key_price3));
            intIntentPrice4 = bundle.getInt(getString(R.string.intent_key_price4));
            intIntentPrice5 = bundle.getInt(getString(R.string.intent_key_price5));
            intIntentPrice6 = bundle.getInt(getString(R.string.intent_key_price6));
            intIntentPrice7 = bundle.getInt(getString(R.string.intent_key_price7));
            intIntentPosition = bundle.getInt(getString(R.string.intent_key_position));


            //表示
            tv_Date.setText(strIntentDate);
            //ラジオボタン表示
            switch (strIntentTime) {
                case "朝":
                    rg_Time.check(R.id.radioBreakfast);
                    break;
                case "昼":
                    rg_Time.check(R.id.radioLunch);
                    break;
                case "夜":
                    rg_Time.check(R.id.radioDinner);
                    break;
            }
            //spinner表示
            //sp_Menu.setSelection(Integer.parseInt(strIntentNumber));
            //表示つづき
            et_MenuTitle.setText(strIntentMenuTitle);
            et_Item1.setText(strIntentItem1);
            et_Item2.setText(strIntentItem2);
            et_Item3.setText(strIntentItem3);
            et_Item4.setText(strIntentItem4);
            et_Item5.setText(strIntentItem5);
            et_Item6.setText(strIntentItem6);
            et_Item7.setText(strIntentItem7);
            et_Price1.setText(String.valueOf(intIntentPrice1));
            et_Price2.setText(String.valueOf(intIntentPrice2));
            et_Price3.setText(String.valueOf(intIntentPrice3));
            et_Price4.setText(String.valueOf(intIntentPrice4));
            et_Price5.setText(String.valueOf(intIntentPrice5));
            et_Price6.setText(String.valueOf(intIntentPrice6));
            et_Price7.setText(String.valueOf(intIntentPrice7));
        }

        //spinner用
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        arrayAdapter.add("1品目");
        arrayAdapter.add("2品目");
        arrayAdapter.add("3品目");
        arrayAdapter.add("4品目");
        arrayAdapter.add("5品目");
        sp_Menu.setAdapter(arrayAdapter);

        btn_Calender.setOnClickListener(this);
        btn_MenuSubmit.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(realm != null) {
            realm.close();
        }
    }

    @Override
    public void onClick(View v) {

        //新規登録の場合
        if(strIntentStatus.equals(getString(R.string.intent_status_submit))) {

            intId = v.getId();
            switch (intId) {
                case R.id.button_Calendar:

                    //ダイアログをインスタンス化
                    CustomDialogFlagment dialog = new CustomDialogFlagment();
                    //表示
                    dialog.show(getSupportFragmentManager(), "sample");
                    break;

                case R.id.buttonMenuSubmit:

                    addNew(); //自作メソッド
                    break;
            }

        } else if(strIntentStatus.equals(getString(R.string.intent_status_change))) { //修正の場合

            intId = v.getId();
            switch (intId) {
                case R.id.button_Calendar:

                    //ダイアログをインスタンス化
                    CustomDialogFlagment dialog = new CustomDialogFlagment();
                    //表示
                    dialog.show(getSupportFragmentManager(), "sample");
                    break;

                case R.id.buttonMenuSubmit:

                    menuChange();
                    break;

            }
        }
    }

    //ダイアログで入力した値をtextViewに入れる
    public  void setTextView(String value) {
        tv_Date.setText(value);
    }

    //新たな献立を登録するメソッド
    private void addNew() {

        //et_Priceをint_Priceに変換
        int_Price1 = Integer.parseInt(String.valueOf(et_Price1.getText()));
        //金額を空白にするとjava.lang.NumberFormatExceptionで落ちるので、try-catch
        try {
            int_Price2 = Integer.parseInt(String.valueOf(et_Price2.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price3 = Integer.parseInt(String.valueOf(et_Price3.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price4 = Integer.parseInt(String.valueOf(et_Price4.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price5 = Integer.parseInt(String.valueOf(et_Price5.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price6 = Integer.parseInt(String.valueOf(et_Price6.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price7 = Integer.parseInt(String.valueOf(et_Price7.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }

        str_Date = String.valueOf(tv_Date.getText());
        int_Time = rg_Time.getCheckedRadioButtonId();
        str_MenuNumber = sp_Menu.getSelectedItem().toString();
        str_MenuTitle = String.valueOf(et_MenuTitle.getText());
        str_Item1 = String.valueOf(et_Item1.getText());
        str_Item2 = String.valueOf(et_Item2.getText());
        str_Item3 = String.valueOf(et_Item3.getText());
        str_Item4 = String.valueOf(et_Item4.getText());
        str_Item5 = String.valueOf(et_Item5.getText());
        str_Item6 = String.valueOf(et_Item6.getText());
        str_Item7 = String.valueOf(et_Item7.getText());

        //合計金額計算
        int_TotalPrice = int_Price1 + int_Price2 + int_Price3 + int_Price4 + int_Price5 + int_Price6 + int_Price7;

        //DB登録
        realm.beginTransaction();
        menuDB = realm.createObject(MenuDB.class);
        menuDB.setId(getAutoIncrementKey(realm , MenuDB.class)); //IDを自動取得する自作メソッド
        menuDB.setStrDate(str_Date);
        //DB登録（ラジオボタン・・朝、昼、夜）
        switch (int_Time) {
            case R.id.radioBreakfast:
                menuDB.setStrTime("朝");
                break;
            case R.id.radioLunch:
                menuDB.setStrTime("昼");
                break;
            case R.id.radioDinner:
                menuDB.setStrTime("夜");
                break;
        }
        //DB登録つづき
        menuDB.setStrMenuNumber(str_MenuNumber);
        menuDB.setStrMenuTitle(str_MenuTitle);
        menuDB.setStrItem1(str_Item1);
        menuDB.setStrItem2(str_Item2);
        menuDB.setStrItem3(str_Item3);
        menuDB.setStrItem4(str_Item4);
        menuDB.setStrItem5(str_Item5);
        menuDB.setStrItem6(str_Item6);
        menuDB.setStrItem7(str_Item7);
        menuDB.setIntPrice1(int_Price1);
        menuDB.setIntPrice2(int_Price2);
        menuDB.setIntPrice3(int_Price3);
        menuDB.setIntPrice4(int_Price4);
        menuDB.setIntPrice5(int_Price5);
        menuDB.setIntPrice6(int_Price6);
        menuDB.setIntPrice7(int_Price7);
        menuDB.setIntTotalPrice(int_TotalPrice);

        realm.commitTransaction();

        //Toastメッセージ
        Toast.makeText(MenuActivity.this, str_MenuTitle + "の合計金額は" + int_TotalPrice + "円です", Toast.LENGTH_LONG).show();

        //入力した文字を消す
        et_MenuTitle.setText("");
        et_Item1.setText("");
        et_Item2.setText("");
        et_Item3.setText("");
        et_Item4.setText("");
        et_Item5.setText("");
        et_Item6.setText("");
        et_Item7.setText("");
        et_Price1.setText("");
        et_Price2.setText("");
        et_Price3.setText("");
        et_Price4.setText("");
        et_Price5.setText("");
        et_Price6.setText("");
        et_Price7.setText("");

        finish(); //登録終了でトップに戻る

    }

    //idを自動取得するメソッド
    private long getAutoIncrementKey(Realm realm, Class<MenuDB> menuDBClass) {

        if(realm.where(menuDBClass).count() == 0) return 1;
        else return realm.where(menuDBClass).max("id").intValue() + 1;

    }


    //献立の修正をするメソッド
    private void menuChange() {

        //et_Priceをint_Priceに変換
        int_Price1 = Integer.parseInt(String.valueOf(et_Price1.getText()));
        //金額を空白にするとjava.lang.NumberFormatExceptionで落ちるので、try-catch
        try {
            int_Price2 = Integer.parseInt(String.valueOf(et_Price2.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price3 = Integer.parseInt(String.valueOf(et_Price3.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price4 = Integer.parseInt(String.valueOf(et_Price4.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price5 = Integer.parseInt(String.valueOf(et_Price5.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price6 = Integer.parseInt(String.valueOf(et_Price6.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }
        try {
            int_Price7 = Integer.parseInt(String.valueOf(et_Price7.getText()));
        } catch (java.lang.NumberFormatException e) {

        } finally {

        }

        str_Date = String.valueOf(tv_Date.getText());
        int_Time = rg_Time.getCheckedRadioButtonId();
        str_MenuNumber = sp_Menu.getSelectedItem().toString();
        str_MenuTitle = String.valueOf(et_MenuTitle.getText());
        str_Item1 = String.valueOf(et_Item1.getText());
        str_Item2 = String.valueOf(et_Item2.getText());
        str_Item3 = String.valueOf(et_Item3.getText());
        str_Item4 = String.valueOf(et_Item4.getText());
        str_Item5 = String.valueOf(et_Item5.getText());
        str_Item6 = String.valueOf(et_Item6.getText());
        str_Item7 = String.valueOf(et_Item7.getText());
        int_TotalPrice = int_Price1 + int_Price2 + int_Price3 + int_Price4 + int_Price5 + int_Price6 + int_Price7;

        //全件抽出
        String[] names = {"strDate", "strTime", "strMenuNumber"};
        Sort[] sorts = {Sort.ASCENDING, Sort.DESCENDING, Sort.ASCENDING};
        results = realm.where(MenuDB.class).equalTo("strDate",strIntentDate).sort(names, sorts).findAll();
        //Intentで渡されたポジションでアイテムを選択する
        menuDB = results.get(intIntentPosition);
        realm.beginTransaction();
        menuDB.setStrDate(str_Date);
        //DB登録（ラジオボタン・・朝、昼、夜）
        switch (int_Time) {
            case R.id.radioBreakfast:
                menuDB.setStrTime("朝");
                break;
            case R.id.radioLunch:
                menuDB.setStrTime("昼");
                break;
            case R.id.radioDinner:
                menuDB.setStrTime("夜");
                break;
        }

        //DB登録つづき
        menuDB.setStrMenuNumber(str_MenuNumber);
        menuDB.setStrMenuTitle(str_MenuTitle);
        menuDB.setStrItem1(str_Item1);
        menuDB.setStrItem2(str_Item2);
        menuDB.setStrItem3(str_Item3);
        menuDB.setStrItem4(str_Item4);
        menuDB.setStrItem5(str_Item5);
        menuDB.setStrItem6(str_Item6);
        menuDB.setStrItem7(str_Item7);
        menuDB.setIntPrice1(int_Price1);
        menuDB.setIntPrice2(int_Price2);
        menuDB.setIntPrice3(int_Price3);
        menuDB.setIntPrice4(int_Price4);
        menuDB.setIntPrice5(int_Price5);
        menuDB.setIntPrice6(int_Price6);
        menuDB.setIntPrice7(int_Price7);
        menuDB.setIntTotalPrice(int_TotalPrice);

        realm.commitTransaction();

        //Toastメッセージ
        Toast.makeText(MenuActivity.this, "変更しました", Toast.LENGTH_LONG).show();

        //入力した文字を消す
        et_MenuTitle.setText("");
        et_Item1.setText("");
        et_Item2.setText("");
        et_Item3.setText("");
        et_Item4.setText("");
        et_Item5.setText("");
        et_Item6.setText("");
        et_Item7.setText("");
        et_Price1.setText("");
        et_Price2.setText("");
        et_Price3.setText("");
        et_Price4.setText("");
        et_Price5.setText("");
        et_Price6.setText("");
        et_Price7.setText("");

        finish(); //修正終了でリストに戻る
    }
}
