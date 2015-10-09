package com.orums_robotics.masterbuildercompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent = getIntent();
        ((TextView)findViewById(R.id.textViewRise)).setText(intent.getStringExtra("rise"));
        ((TextView)findViewById(R.id.textViewRun)).setText(intent.getStringExtra("run"));
        ((TextView)findViewById(R.id.textViewRiserHeight)).setText(intent.getStringExtra("riserHeight"));
        ((TextView)findViewById(R.id.textViewRisers)).setText(intent.getStringExtra("risers"));
        ((TextView)findViewById(R.id.textViewRiserErr)).setText(intent.getStringExtra("riserErr"));
        ((TextView)findViewById(R.id.textViewTreadWidth)).setText(intent.getStringExtra("treadWidth"));
        ((TextView)findViewById(R.id.textViewTreads)).setText(intent.getStringExtra("treads"));
        ((TextView)findViewById(R.id.textViewTreadErr)).setText(intent.getStringExtra("treadErr"));
        ((TextView)findViewById(R.id.textViewOpen)).setText(intent.getStringExtra("open"));
        ((TextView)findViewById(R.id.textViewStringer)).setText(intent.getStringExtra("stringer"));
        ((TextView)findViewById(R.id.textViewIncline)).setText(intent.getStringExtra("incline"));

    }

    public void onClickButtonCloseDlg(View view) {
        Intent intent = new Intent(TableActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
