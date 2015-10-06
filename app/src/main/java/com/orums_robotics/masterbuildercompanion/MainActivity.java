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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int[][] buttonIdMap = {
            {R.id.button_pitch, R.id.button_rise, R.id.button_run, R.id.button_diag, R.id.button_hipV},
            {R.id.button_comp, R.id.button_stair, R.id.button_arc, R.id.button_circ, R.id.button_jack},
            {R.id.button_m, R.id.button_length, R.id.button_width, R.id.button_height, R.id.button_percent},
            {R.id.button_yds, R.id.button_feet, R.id.button_inch, R.id.button_to, R.id.button_back},
            {R.id.button_conv, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_div},
            {R.id.button_store, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_mul},
            {R.id.button_rcl, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_sub},
            {R.id.button_mplus, R.id.button_0, R.id.button_dot, R.id.button_equal, R.id.button_add}};
    private int[][] textColor = new int[8][5];
    private View[][] buttonView = new View[8][5];

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

        // Save button view reference for future reference
        for (int i = 0; i < 8; i++) {
            buttonView[i][0] = findViewById(buttonIdMap[i][0]);
            buttonView[i][1] = findViewById(buttonIdMap[i][1]);
            buttonView[i][2] = findViewById(buttonIdMap[i][2]);
            buttonView[i][3] = findViewById(buttonIdMap[i][3]);
            buttonView[i][4] = findViewById(buttonIdMap[i][4]);
        }

        // Save text colors assigned in layout file
        for (int i = 0; i < 8; i++) {
            textColor[i][0] = ((Button)buttonView[i][0]).getCurrentTextColor();
            textColor[i][1] = ((Button)buttonView[i][1]).getCurrentTextColor();
            textColor[i][2] = ((Button)buttonView[i][2]).getCurrentTextColor();
            textColor[i][3] = ((Button)buttonView[i][3]).getCurrentTextColor();
            textColor[i][4] = ((Button)buttonView[i][4]).getCurrentTextColor();
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
        if (keyCode == KeyEvent.KEYCODE_BACK) onClickButtonOff(null);
        return false;
    }

    // Process app exit initiated by pressing off button
    public void onClickButtonOff(View view)
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

    public void onClickButtonClr(View view)
    {
        calc.inputKey(ConstructionCalculator.CXX);
        firstScreen.setText(calc.getPrmScreenText());
        if (buttonsDisabled) enableButtons();

    }

    public void onClickButtonFrac(View view) {
        calc.inputKey(ConstructionCalculator.FRAC);
        firstScreen.setText(calc.getPrmScreenText());

    }

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

    public void onClickButtonStair(View view) {
        calc.inputKey(ConstructionCalculator.STAIR);
        //firstScreen.setText(calc.getPrmScreenText());
        Intent intent = new Intent(MainActivity.this, TableActivity.class);
        startActivity(intent);
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
    public void onClickButtonYds(View view) {
        calc.inputKey(ConstructionCalculator.YDS);
        firstScreen.setText(calc.getPrmScreenText());
        if (buttonsDisabled) enableButtons();
    }

    public void onClickButtonFeet(View view) {
        calc.inputKey(ConstructionCalculator.FEET);
        firstScreen.setText(calc.getPrmScreenText());
        if (buttonsDisabled) enableButtons();
    }

    public void onClickButtonInch(View view) {
        calc.inputKey(ConstructionCalculator.INCH);
        firstScreen.setText(calc.getPrmScreenText());
        if (buttonsDisabled) enableButtons();
    }

    public void onClickButtonMeter(View view) {
        calc.inputKey(ConstructionCalculator.M);
        firstScreen.setText(calc.getPrmScreenText());
        if (buttonsDisabled) enableButtons();
    }

    public void onClickButtonConv(View view) {
        calc.inputKey(ConstructionCalculator.CONV);
        firstScreen.setText(calc.getPrmScreenText());
        disableButtons(ConstructionCalculator.CONV);
        //disableButtons(0xf3, 0xe7, 0xf7, 0xef, 0xff);
        ((Button)buttonView[4][1]).setText("cm");
        ((Button)buttonView[4][3]).setText("mm");
    }

    public void onClickButtonBack(View view) {
        calc.inputKey(ConstructionCalculator.BSP);
        firstScreen.setText(calc.getPrmScreenText());
    }


    // Memory keys
    public void onClickButtonStore(View view) {
        calc.inputKey(ConstructionCalculator.MSX);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Memory keys
    public void onClickButtonRcl(View view) {
        calc.inputKey(ConstructionCalculator.MRX);
        firstScreen.setText(calc.getPrmScreenText());
        secondScreen.setText(calc.getSecScreenText());
    }

    // Memory keys
    public void onClickButtonMplus(View view) {
        calc.inputKey(ConstructionCalculator.MPX);
        firstScreen.setText(calc.getPrmScreenText());
        secondScreen.setText(calc.getSecScreenText());
    }

    // Arithmetic keys
    public void onClickButtonMul(View view) {
        calc.inputKey(ConstructionCalculator.MUL);
        firstScreen.setText(calc.getPrmScreenText());
        secondScreen.setText(calc.getSecScreenText());
    }

    // Arithmetic keys
    public void onClickButtonDiv(View view) {
        calc.inputKey(ConstructionCalculator.DIV);
        firstScreen.setText(calc.getPrmScreenText());
        secondScreen.setText(calc.getSecScreenText());
    }

    // Arithmetic keys
    public void onClickButtonAdd(View view) {
        calc.inputKey(ConstructionCalculator.ADD);
        firstScreen.setText(calc.getPrmScreenText());
        secondScreen.setText(calc.getSecScreenText());
    }

    // Arithmetic keys
    public void onClickButtonSub(View view) {
        calc.inputKey(ConstructionCalculator.SUB);
        firstScreen.setText(calc.getPrmScreenText());
        secondScreen.setText(calc.getSecScreenText());
    }

    // Arithmetic keys
    public void onClickButtonEqual(View view) {
        calc.inputKey(ConstructionCalculator.EQU);
        firstScreen.setText(calc.getPrmScreenText());
        secondScreen.setText(calc.getSecScreenText());

    }

    // Numeric
    public void onClickButton0(View view)
    {
        calc.inputKey(ConstructionCalculator.DG0);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Numeric
    public void onClickButton1(View view)
    {
        calc.inputKey(ConstructionCalculator.DG1);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Numeric
    public void onClickButton2(View view)
    {
        calc.inputKey(ConstructionCalculator.DG2);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Numeric
    public void onClickButton3(View view)
    {
        calc.inputKey(ConstructionCalculator.DG3);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Numeric
    public void onClickButton4(View view)
    {
        calc.inputKey(ConstructionCalculator.DG4);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Numeric
    public void onClickButton5(View view)
    {
        calc.inputKey(ConstructionCalculator.DG5);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Numeric
    public void onClickButton6(View view)
    {
        calc.inputKey(ConstructionCalculator.DG6);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Numeric
    public void onClickButton7(View view)
    {
        calc.inputKey(buttonsDisabled ? ConstructionCalculator.CM : ConstructionCalculator.DG7);
        firstScreen.setText(calc.getPrmScreenText());
        if (buttonsDisabled) enableButtons();

    }

    // Numeric
    public void onClickButton8(View view)
    {
        calc.inputKey(ConstructionCalculator.DG8);
        firstScreen.setText(calc.getPrmScreenText());
    }

    // Numeric
    public void onClickButton9(View view)
    {
        calc.inputKey(buttonsDisabled ? ConstructionCalculator.MM : ConstructionCalculator.DG9);
        firstScreen.setText(calc.getPrmScreenText());
        if (buttonsDisabled) enableButtons();

    }

    // Numeric
    public void onClickButtonDot(View view)
    {
        calc.inputKey(ConstructionCalculator.DOT);
        firstScreen.setText(calc.getPrmScreenText());
    }

    private void enableButtons()
    {
        int col1, col2, col3, col4, col5;
        col1 = col2 = col3 = col4 = col5 = 0xff;
        for (int i = 0; i < 8; i++) {
            if ((col1 & 1) == 1) {buttonView[i][0].setClickable(true); ((Button)buttonView[i][0]).setTextColor(textColor[i][0]);}
            if ((col2 & 1) == 1) {buttonView[i][1].setClickable(true); ((Button)buttonView[i][1]).setTextColor(textColor[i][1]);}
            if ((col3 & 1) == 1) {buttonView[i][2].setClickable(true); ((Button)buttonView[i][2]).setTextColor(textColor[i][2]);}
            if ((col4 & 1) == 1) {buttonView[i][3].setClickable(true); ((Button)buttonView[i][3]).setTextColor(textColor[i][3]);}
            if ((col5 & 1) == 1) {buttonView[i][4].setClickable(true); ((Button)buttonView[i][4]).setTextColor(textColor[i][4]);}
            col1 >>= 1;
            col2 >>= 1;
            col3 >>= 1;
            col4 >>= 1;
            col5 >>= 1;
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
                ((Button)buttonView[i][0]).setTextColor(Color.rgb(146, 149, 152));
            }
            if ((col2 & 1) == 1) {
                buttonView[i][1].setClickable(false);
                ((Button)buttonView[i][1]).setTextColor(Color.rgb(146, 149, 152));
            }
            if ((col3 & 1) == 1) {
                buttonView[i][2].setClickable(false);
                ((Button)buttonView[i][2]).setTextColor(Color.rgb(146, 149, 152));
            }
            if ((col4 & 1) == 1) {
                buttonView[i][3].setClickable(false);
                ((Button)buttonView[i][3]).setTextColor(Color.rgb(146, 149, 152));
            }
            if ((col5 & 1) == 1) {
                buttonView[i][4].setClickable(false);
                ((Button)buttonView[i][4]).setTextColor(Color.rgb(146, 149, 152));
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
