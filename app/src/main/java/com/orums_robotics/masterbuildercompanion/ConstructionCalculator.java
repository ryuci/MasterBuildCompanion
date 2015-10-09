package com.orums_robotics.masterbuildercompanion;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ryuci on 15. 9. 28..
 */
public class ConstructionCalculator extends Calculator {
    //======================================================================
    // [DATA] Static class data fields.
    private static final String TAG = "orums_robotics.masterbuildercompanion.ConstructionCalculator";
    public static final String OFF = "Off";			// Off
    public static final String PITCH = "Pitch";
    public static final String RISE = "Rise";
    public static final String RUN = "Run";
    public static final String DIAG = "Diag";
    public static final String HIP = "Hip/V";
    public static final String COMP = "Comp/Miter";
    public static final String STAIR = "Stair";
    public static final String ARC = "Arc";
    public static final String CIRC = "Circ";
    public static final String JACK = "Jack";
    public static final String FEET = "'";
    public static final String INCH = FeetMath.INCH;
    public static final String DEC = FeetMath.DEC;
    public static final String M = FeetMath.M;
    public static final String CM = FeetMath.CM;
    public static final String MM = FeetMath.MM;
    public static final String YDS = FeetMath.YDS;
    public static final String LENGTH = "Length";
    public static final String WIDTH = "Width";
    public static final String HEIGHT = "Height";
    public static final String FRAC = "/";
    public static final String CONV = "Conv";
    public static final String STO = "STO";

    //======================================================================
    // [DATA] Class instance data fields.
    private String mode, lastMode;
    private FeetMath lastVal;
    private String lastValueInInch;
    private boolean stoOn, stairOn;
    private FeetMath lastSto;
    private String statusTxt;

    private Stair stairResult;
    private Roof roofResult;

    //======================================================================
    // [FUNC] Primary class constructor.
    public ConstructionCalculator()
    {
        super();
        initFields();
    }

    @Override
    protected void initFields() {
        super.initFields();
        mode = DEC; // decimal mode
        lastMode = DEC;
        lastVal = new FeetMath(0, INCH);
        lastValueInInch = "";
        stoOn = false;
        lastSto = new FeetMath(0, INCH);
        stairOn = false;
        statusTxt = "";
    }

    @Override
    public String getSecScreenText() {

        return super.getSecScreenText() + statusTxt;
    }


    private boolean isNum(String key) {
        return key == DG0 || key == DG1 || key == DG2 || key == DG3
                || key == DG4 || key == DG5 || key == DG6 || key == DG7
                || key == DG8 || key == DG9 || key == DOT;
    }

    private boolean isFeet(String key) {
        return key == FEET || key == INCH || key == FRAC;
    }

    private boolean isOp(String key) {
        return key == ADD || key == SUB || key == MUL || key == DIV;
    }

    private FeetMath getScreenValue(String mode) {
        FeetMath ret = new FeetMath(0, INCH);
        try {
            switch (mode) {
                case FEET:
                    // ex. 1' 3" 3/4 -> to inch
                    ret = new FeetMath(prmScreenText);
                    break;
                case INCH:
                    // ex. 3.984' -> 3.984
                    ret = new FeetMath(prmScreenText.substring(0, prmScreenText.length() - 1), mode);
                    break;
                case M:
                    // ex. 3.984m -> 3.984 -> to inch
                    ret = new FeetMath(prmScreenText.substring(0, prmScreenText.length() - 1), mode);
                    break;
                case CM:
                    // ex. 3.984cm -> 3.984 -> to inch
                    ret = new FeetMath(prmScreenText.substring(0, prmScreenText.length() - 2), mode);
                    break;
                case MM:
                    // ex. 3.984mm -> 3.984 -> to inch
                    ret = new FeetMath(prmScreenText.substring(0, prmScreenText.length() - 2), mode);
                    break;
                case YDS:
                    // ex. 3.984yds -> 3.984 -> to inch
                    ret = new FeetMath(prmScreenText.substring(0, prmScreenText.length() - 3), mode);
                    break;
                case DEC:
                    // ex. 3.984 -> 3.984 -> to scala
                    ret = new FeetMath(prmScreenText.substring(0, prmScreenText.length() - 3), mode);
                    break;

            }
        } catch(NumberFormatException e) {
            prmScreenText = e.toString();
        }
        return ret;
    }

    //======================================================================
    // [FUNC] Accepts input key from the user.
    @Override
    public void inputKey(String key) {
        switch (mode) {
            case DEC:
                if (key == FEET)        { mode = FEET; prmScreenText += "' "; }
                else if (key == INCH)   { mode = FEET; prmScreenText += "\" "; }
                else if (key == FRAC)   { mode = FEET; prmScreenText += "/"; }
                else if (key == M)      { mode = M; prmScreenText += "m"; }
                else if (key == YDS)    { mode = YDS; prmScreenText += "yds"; }
                else if (key == CXX)    { super.inputKey(key); initFields(); }
                else if (key == EQU) {
                    super.inputKey(key);
                    switch (lastMode) {
                        case FEET:  prmScreenText = new FeetMath(prmScreenText, INCH).toString(); mode = lastMode; break;
                        case INCH:  prmScreenText += new FeetMath(prmScreenText, INCH).getInches() + "\""; mode = lastMode; break;
                        case M:     prmScreenText += new FeetMath(prmScreenText, INCH).getMeter() + "m"; mode = lastMode; break;
                        case CM:    prmScreenText += new FeetMath(prmScreenText, INCH).getCentiMeter() + "cm"; mode = lastMode; break;
                        case MM:    prmScreenText += new FeetMath(prmScreenText, INCH).getMilliMeter() + "mm"; mode = lastMode; break;
                        case YDS:   prmScreenText += new FeetMath(prmScreenText, INCH).getYds() + "yds"; mode = lastMode; break;
                    }
                }
                else super.inputKey(key);
                break;
            case FEET:
                if (isOp(key)) {
                    lastMode = mode;
                    mode = DEC;
                    lastValueInInch = String.valueOf(new FeetMath(prmScreenText).getInches());

                    // Simulate calc.inputKey() with op key in
                    prmScreenText = lastValueInInch;
                    super.inputKey(key);
                }
                else if (key == CONV) {
                    lastVal = getScreenValue(FEET);
                    lastMode = mode;
                    statusTxt = "Converting";
                    mode = CONV;
                }
                else if (key == FEET) {
                }
                else if (key == INCH) {
                    if (!prmScreenText.contains("\"")) prmScreenText += "\" ";
                }
                else if (key == FRAC) {
                    if (!prmScreenText.contains("/")) prmScreenText += "/";
                }
                else if (isNum(key)) {
                    super.inputKey(key);
                }
                else if (key == STO) {
                    stoOn = true;
                    statusTxt += " [STO]";
                    lastSto = getScreenValue(FEET);
                }
                else if (key == STAIR) {
                    stairOn = true;
                    statusTxt += " [STAIR]";
                    // update 2nd screen here!
                }
                else if (key == RISE) {
                    if (stairOn && stoOn) {

                    }
                }
                else if (key == CXX) {
                    super.inputKey(key);
                    initFields();
                }
                else if (key == EQU) {
                    super.inputKey(key);
                    switch (lastMode) {
                        case FEET:
                            prmScreenText = new FeetMath(prmScreenText, INCH).toString();
                            mode = lastMode;
                            break;
                        case INCH:
                            prmScreenText += new FeetMath(prmScreenText, INCH).getInches() + "\"";
                            mode = lastMode;
                            break;
                        case M:
                            prmScreenText += new FeetMath(prmScreenText, INCH).getMeter() + "m";
                            mode = lastMode;
                            break;
                        case CM:
                            prmScreenText += new FeetMath(prmScreenText, INCH).getCentiMeter() + "cm";
                            mode = lastMode;
                            break;
                        case MM:
                            prmScreenText += new FeetMath(prmScreenText, INCH).getMilliMeter() + "mm";
                            mode = lastMode;
                            break;
                        case YDS:
                            prmScreenText += new FeetMath(prmScreenText, INCH).getYds() + "yds";
                            mode = lastMode;
                            break;
                    }
                }
                else if (key == BSP) {
                    super.inputKey(key);
                }
                break;
            case INCH:
            case M:
            case CM:
            case MM:
            case YDS:
                if (isOp(key)) {
                    lastMode = mode;
                    mode = DEC;
                    lastValueInInch = String.valueOf(new FeetMath(prmScreenText).getInches());

                    // Simulate calc.inputKey() with op key in
                    prmScreenText = lastValueInInch;
                    super.inputKey(key);
                }
                else if (key == CONV) {
                    lastVal = getScreenValue(mode);
                    lastMode = mode;
                    mode = CONV;
                    statusTxt = "Converting";
                }
                else if (key == CXX) {
                    super.inputKey(key);
                    initFields();
                }
                else if (key == BSP) {
                    super.inputKey(key);
                }
                break;
            case CONV:
                if (key == FEET)        prmScreenText = lastVal.toString();
                else if (key == INCH)   prmScreenText = lastVal.getInches() + "\"";
                else if (key == YDS)    prmScreenText = lastVal.getYds() + "yds";
                else if (key == M)      prmScreenText = lastVal.getMeter() + "m";
                else if (key == CM)     prmScreenText = lastVal.getCentiMeter() + "cm";
                else if (key == MM)     prmScreenText = lastVal.getMilliMeter() + "mm";
                else if (key == CXX) {
                    super.inputKey(key);
                    initFields();
                }
                mode = key;
                statusTxt = "";
                break;
        }
    }



}
