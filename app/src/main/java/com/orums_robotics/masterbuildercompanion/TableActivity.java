package com.orums_robotics.masterbuildercompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
    }

    public void onClickButtonCloseDlg(View view) {
        Intent intent = new Intent(TableActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
