package com.example.mymenuapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_submitMenu, btn_showMenu;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_submitMenu = findViewById(R.id.buttonSubmitMenu);
        btn_showMenu = findViewById(R.id.buttonShowMenu);

        btn_submitMenu.setOnClickListener(this);
        btn_showMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int intId = v.getId();

        switch (intId) {
            case R.id.buttonSubmitMenu:
                intent = new Intent(MainActivity.this,MenuActivity.class);
                intent.putExtra(getString(R.string.intent_key_status),getString(R.string.intent_status_submit));
                break;
            case  R.id.buttonShowMenu:
                intent = new Intent(MainActivity.this, CalendarActivity.class);
                break;
        }
        startActivity(intent);
    }
}
