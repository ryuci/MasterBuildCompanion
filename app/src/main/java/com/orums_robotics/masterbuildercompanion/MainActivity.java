package com.orums_robotics.masterbuildercompanion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int[][] buttonIdMap = {
            {R.id.button_dummy1, R.id.button_dummy1, R.id.button_dummy1, R.id.button_off, R.id.button_c},
            {R.id.button_pitch, R.id.button_rise, R.id.button_run, R.id.button_diag, R.id.button_hipV},
            {R.id.button_comp, R.id.button_stair, R.id.button_arc, R.id.button_circ, R.id.button_jack},
            {R.id.button_m, R.id.button_length, R.id.button_width, R.id.button_height, R.id.button_percent},
            {R.id.button_yds, R.id.button_feet, R.id.button_inch, R.id.button_to, R.id.button_back},
            {R.id.button_conv, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_div},
            {R.id.button_store, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_mul},
            {R.id.button_rcl, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_sub},
            {R.id.button_mplus, R.id.button_0, R.id.button_dot, R.id.button_equal, R.id.button_add}};
    private Button[][] buttonView = new Button[9][5];
    private String[][] buttonText = new String[9][5];
    private int[][] buttonTextColor = new int[9][5];
    private Calculator calc = new ConstructionCalculator();
    private TextView firstScreen;
    private TextView secondScreen;

    private View v;
    private String lastButton;
    private String lastNumber;
    private boolean buttonsDisabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        // Do not allow landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firstScreen = (TextView) findViewById(R.id.textView);
        secondScreen = (TextView)findViewById(R.id.textView5);

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 5; j++) {
                // Save button view reference for future reference
                buttonView[i][j] = (Button)findViewById(buttonIdMap[i][j]);
                // Save text
                buttonText[i][j] = (String)buttonView[i][j].getText();
                // Save text colors
                buttonTextColor[i][j] = buttonView[i][j].getCurrentHintTextColor();
                //
                buttonView[i][j].setOnClickListener(onClickListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    // Process app exit initiated by pressing back button
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) finishApp();
        return false;
    }

    Button.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            String key;
            switch (v.getId()) {
                case R.id.button_off: finishApp(); break;

                case R.id.button_c: calc.inputKey(ConstructionCalculator.CXX); printScreen(); if (buttonsDisabled) enableButtons(); break;
                case R.id.button_back: calc.inputKey(ConstructionCalculator.BSP); printScreen(); break;

                case R.id.button_0: calc.inputKey(ConstructionCalculator.DG0); printScreen(); break;
                case R.id.button_1: calc.inputKey(ConstructionCalculator.DG1); printScreen(); break;
                case R.id.button_2: calc.inputKey(ConstructionCalculator.DG2); printScreen(); break;
                case R.id.button_3: calc.inputKey(ConstructionCalculator.DG3); printScreen(); break;
                case R.id.button_4: calc.inputKey(ConstructionCalculator.DG4); printScreen(); break;
                case R.id.button_5: calc.inputKey(ConstructionCalculator.DG5); printScreen(); break;
                case R.id.button_6: calc.inputKey(ConstructionCalculator.DG6); printScreen(); break;
                case R.id.button_7: calc.inputKey(ConstructionCalculator.DG7); printScreen(); break;
                case R.id.button_8: calc.inputKey(ConstructionCalculator.DG8); printScreen(); break;
                case R.id.button_9: calc.inputKey(ConstructionCalculator.DG9); printScreen(); break;
                case R.id.button_dot: calc.inputKey(ConstructionCalculator.DOT); printScreen(); break;

                case R.id.button_equal: calc.inputKey(ConstructionCalculator.EQU); printScreen(); break;
                case R.id.button_add: calc.inputKey(ConstructionCalculator.ADD); printScreen(); break;
                case R.id.button_sub: calc.inputKey(ConstructionCalculator.SUB); printScreen(); break;
                case R.id.button_mul: calc.inputKey(ConstructionCalculator.MUL); printScreen(); break;
                case R.id.button_div: calc.inputKey(ConstructionCalculator.DIV); printScreen(); break;

                case R.id.button_feet: calc.inputKey(ConstructionCalculator.FEET); printScreen(); if (buttonsDisabled) enableButtons(); break;
                case R.id.button_inch: calc.inputKey(ConstructionCalculator.INCH); printScreen(); if (buttonsDisabled) enableButtons(); break;
                case R.id.button_m: calc.inputKey(ConstructionCalculator.M); printScreen(); if (buttonsDisabled) enableButtons(); break;
                case R.id.button_yds: calc.inputKey(ConstructionCalculator.YDS); printScreen(); if (buttonsDisabled) enableButtons(); break;
                case R.id.button_to: calc.inputKey(ConstructionCalculator.FRAC); printScreen(); if (buttonsDisabled) enableButtons(); break;

                case R.id.button_conv: handleConv(); break;

                case R.id.button_stair: calc.inputKey(ConstructionCalculator.STAIR); printStairResult(); break;

                case R.id.button_store: calc.inputKey(ConstructionCalculator.MSX); printStairResult(); break;
                case R.id.button_rcl: calc.inputKey(ConstructionCalculator.MRX); printStairResult(); break;
                case R.id.button_mplus: calc.inputKey(ConstructionCalculator.MPX); printStairResult(); break;
            }
        }
    };

    //
    private void handleConv() {
        calc.inputKey(ConstructionCalculator.CONV);
        firstScreen.setText(calc.getPrmScreenText());
        disableButtons(ConstructionCalculator.CONV);
        buttonView[4][1].setText("cm");
        buttonView[4][3].setText("mm");
    }

    // Process app exit initiated by pressing off button
    private void finishApp()
    {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("종료")
                .setMessage("앱을 종료하시겠습니까?")
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton("취소", null)
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    //
    private void printScreen()
    {
        firstScreen.setText(calc.getPrmScreenText());
        secondScreen.setText(calc.getSecScreenText());
    }

    //
    public void printStairResult() {
  /*      if (calc.prmScreenText == "getStairResult") {
            Stair result = calc.getStairResult();
            Intent intent = new Intent(MainActivity.this, TableActivity.class);
            intent.putExtra("rise", result.getRise());
            intent.putExtra("run", result.getRun());
            intent.putExtra("riserHeight", result.getRiserHeight());
            intent.putExtra("risers", result.getRisers());
            intent.putExtra("riserErr", result.getRiserErr());
            intent.putExtra("treadWidth", result.getTreadWidth());
            intent.putExtra("treads", result.getTreads());
            intent.putExtra("treadErr", result.getTreadErr());
            intent.putExtra("open", result.getOpen());
            intent.putExtra("stringer", result.getStringer());
            intent.putExtra("incline", result.getIncline());
            startActivity(intent);
        }*/
    }

    /*



    public void onClickButtonPit(View view) {
        //Intent intent = new Intent(MainActivity.this, RoofNumbersActivity.class);
        //startActivity(intent);
        calc.inputKey(ConstructionCalculator.PITCH);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonRise(View view) {
        calc.inputKey(ConstructionCalculator.RISE);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonRun(View view) {
        calc.inputKey(ConstructionCalculator.RUN);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonDiag(View view) {
        calc.inputKey(ConstructionCalculator.DIAG);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonHipV(View view) {
        calc.inputKey(ConstructionCalculator.HIP);
        firstScreen.setText(calc.getPrmScreenText());
    }


    public void onClickButtonArc(View view) {
        calc.inputKey(ConstructionCalculator.ARC);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonCirc(View view) {
        calc.inputKey(ConstructionCalculator.CIRC);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonJack(View view) {
        calc.inputKey(ConstructionCalculator.JACK);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonLength(View view) {
        calc.inputKey(ConstructionCalculator.LENGTH);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonWidth(View view) {
        calc.inputKey(ConstructionCalculator.WIDTH);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonHeight(View view) {
        calc.inputKey(ConstructionCalculator.HEIGHT);
        firstScreen.setText(calc.getPrmScreenText());
    }

    public void onClickButtonPercent(View view) {
        calc.inputKey(ConstructionCalculator.PER);
        firstScreen.setText(calc.getPrmScreenText());
    }

    //
    // Unit Conversion
    //




    }


*/
    private void enableButtons() {
        int[] col = new int[5];
        col[0] = col[1] = col[2] = col[3] = col[4] = 0xff;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; i++) {
                if ((col[j] & 1) == 1) {
                    buttonView[i][j].setClickable(true);
                    buttonView[i][j].setTextColor(buttonTextColor[i][j]);
                }
                col[j] >>= 1;
            }
        }
        ((Button)buttonView[4][1]).setText("7");
        ((Button)buttonView[4][3]).setText("9");

        buttonsDisabled = false;
    }

    private void disableButtons(String key) {
        switch (key) {
            case ConstructionCalculator.CONV:
                disableButtons(0xf3, 0xe7, 0xf7, 0xef, 0xff);
                break;


        }
    }

    private void disableButtons(int col1, int col2, int col3, int col4, int col5)
    {
        for (int i = 0; i < 8; i++) {
            if ((col1 & 1) == 1) {
                buttonView[i][0].setClickable(false);
                buttonView[i][0].setTextColor(Color.rgb(146, 149, 152));
            }
            if ((col2 & 1) == 1) {
                buttonView[i][1].setClickable(false);
                buttonView[i][1].setTextColor(Color.rgb(146, 149, 152));
            }
            if ((col3 & 1) == 1) {
                buttonView[i][2].setClickable(false);
                buttonView[i][2].setTextColor(Color.rgb(146, 149, 152));
            }
            if ((col4 & 1) == 1) {
                buttonView[i][3].setClickable(false);
                buttonView[i][3].setTextColor(Color.rgb(146, 149, 152));
            }
            if ((col5 & 1) == 1) {
                buttonView[i][4].setClickable(false);
                buttonView[i][4].setTextColor(Color.rgb(146, 149, 152));
            }
            col1 >>= 1;
            col2 >>= 1;
            col3 >>= 1;
            col4 >>= 1;
            col5 >>= 1;
        }
        buttonsDisabled = true;
    }


}
