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
    public static final String PIT = "Pitch";
    public static final String RUN = "Run";
    public static final String DIA = "Diag";
    public static final String HIP = "Hip/V";
    public static final String COM = "Comp/Miter";
    public static final String STA = "Stair";
    public static final String ARC = "Arc";
    public static final String CIR = "Circ";
    public static final String JCK = "Jack";
    public static final String MTR = "m";
    public static final String CMT = "cm";
    public static final String MMT = "mm";
    public static final String LEN = "Length";
    public static final String WID = "Width";
    public static final String HGT = "Height";
    public static final String YDS = "Yds";
    public static final String FET = "Feet";
    public static final String INC = "Inch";
    public static final String FRC = "/";
    public static final String CON = "Conv";

    //======================================================================
    // [DATA] Class instance data fields.
    private String mode = DEC;
    private boolean convert = false;
    private Pattern pattern;
    //private String regex = "^(\\d*\\.?\\d*)['\"]\\s?(\\d?)[-\\s]?((\\d{1,2})/(\\d{1,2})?)\"?";
    private String regex = "^(\\d*\\.?\\d*)['\"]\\s?(\\d?)[-\\s]?((\\d{1,2})/(\\d{1,2}))?\"?";


    //======================================================================
     // [FUNC] Primary class constructor.
     public ConstructionCalculator()
     {
         super();
         pattern = Pattern.compile(regex);
     }

    @Override
    protected void initFields() {
        mode = DEC; // decimal mode
        convert = false;
        super.initFields();
    }

    //======================================================================
    // [FUNC] Accepts input key from the user.
    @Override
    public void inputKey(String key)
    {
        Log.i(TAG, "inputKey");
/*
        if (convert)
        {
            convertUnit(mode, key);
            convert = false;
        }*/
        try {
            switch (key) {
                case FET:
                    if (convert) {
                        convertUnit(mode, key);
                        convert = false;
                    } else if (!prmScreenText.contains("'"))
                        prmScreenText += "' ";
                    mode = FET;
                    break;
                case INC:
                    if (convert) {
                        convertUnit(mode, key);
                        convert = false;
                        mode = INC;
                    } else if (!prmScreenText.contains("\""))
                        prmScreenText += "\" ";
                    break;
                case FRC:
                    if (convert) {
                        convert = false;
                    } else if (!prmScreenText.contains("/"))
                        prmScreenText += "/";
                    break;
                case MTR:
                    if (convert) {
                        convertUnit(mode, key);
                    } else {
                        if (!prmScreenText.contains("m") && mode != MTR)
                            prmScreenText += "m";
                    }
                    mode = MTR;
                    break;
                case CMT:
                    if (convert) {
                        convertUnit(mode, key);
                    } else {
                        if (!prmScreenText.contains("cm") && mode != CMT)
                            prmScreenText += "cm";
                    }
                    mode = CMT;
                    break;
                case MMT:
                    if (convert) {
                        convertUnit(mode, key);
                    } else {
                        if (!prmScreenText.contains("mm") && mode != MMT)
                            prmScreenText += "mm";
                    }
                    mode = MMT;
                    break;
                case YDS:
                    if (convert) {
                        convertUnit(mode, key);
                    } else {
                        if (!prmScreenText.contains("yds"))
                            prmScreenText += "yds";
                    }
                    mode = YDS;
                    break;
                case CON:
                    convert = true;
                    break;
                case CXX:
                    initFields();
                    break;
                default:
                    if (convert) convert = false;
                    super.inputKey(key);
                    break;

            }
        }
        catch(NumberFormatException e) {
            prmScreenText = e.toString();
        }
    }

    private void convertUnit(String mode, String key)
    {
        Log.i(TAG, "convertUnit");
        switch (mode)
        {
            // http://www.coderanch.com/t/370805/java/java/Regex
            case FET:
                FeetMath tmp = new FeetMath(prmScreenText);
                switch (key) {
                    case FET:
                        break;
                    case INC:
                        prmScreenText = tmp.getInches() + "\"";
                        break;
                    case MTR:
                        prmScreenText = tmp.getMeter() + "m";
                        break;
                    case CMT:
                        prmScreenText = tmp.getCentiMeter() + "cm";
                        break;
                    case MMT:
                        prmScreenText = tmp.getMilliMeter() + "mm";
                        break;
                    case YDS:
                        prmScreenText = tmp.getYds() + "yds";
                        break;
                }
                break;
            case INC:
                tmp = new FeetMath(Double.parseDouble(prmScreenText.substring(0,prmScreenText.length()-1)),FeetMath.INCH);
                switch (key) {
                    case FET:
                        prmScreenText = tmp.toString();
                        break;
                    case INC:
                        break;
                    case MTR:
                        prmScreenText = tmp.getMeter() + "m";
                        break;
                    case CMT:
                        prmScreenText = tmp.getCentiMeter() + "cm";
                        break;
                    case MMT:
                        prmScreenText = tmp.getMilliMeter() + "mm";
                        break;
                    case YDS:
                        prmScreenText = tmp.getYds() + "yds";
                        break;
                }
                break;
            case MTR:
                tmp = new FeetMath(Double.parseDouble(prmScreenText.substring(0, prmScreenText.length()-1)),FeetMath.M);
                switch (key) {
                    case FET:
                        prmScreenText = tmp.toString();
                    case INC:
                        prmScreenText = tmp.getInches() + "\"";
                        break;
                    case MTR:
                        break;
                    case CMT:
                        prmScreenText = tmp.getCentiMeter() + "cm";
                        break;
                    case MMT:
                        prmScreenText = tmp.getMilliMeter() + "mm";
                        break;
                    case YDS:
                        prmScreenText = tmp.getYds() + "yds";
                        break;
                }
                break;
            case MMT:
                tmp = new FeetMath(Double.parseDouble(prmScreenText.substring(0, prmScreenText.length()-2)),FeetMath.M);
                switch (key) {
                    case FET:
                        prmScreenText = tmp.toString();
                    case INC:
                        prmScreenText = tmp.getInches() + "\"";
                        break;
                    case MTR:
                        prmScreenText = tmp.getMeter() + "m";
                        break;
                    case CMT:
                        prmScreenText = tmp.getCentiMeter() + "cm";
                        break;
                    case MMT:
                        break;
                    case YDS:
                        prmScreenText = tmp.getYds() + "yds";
                        break;
                }
                break;
            case CMT:
                tmp = new FeetMath(Double.parseDouble(prmScreenText.substring(0, prmScreenText.length()-2)),FeetMath.M);
                switch (key) {
                    case FET:
                        prmScreenText = tmp.toString();
                    case INC:
                        prmScreenText = tmp.getInches() + "\"";
                        break;
                    case MTR:
                        prmScreenText = tmp.getMeter() + "m";
                        break;
                    case CMT:
                        break;
                    case MMT:
                        prmScreenText = tmp.getMilliMeter() + "mm";
                        break;
                    case YDS:
                        prmScreenText = tmp.getYds() + "yds";
                        break;
                }
                break;
            case YDS:
                tmp = new FeetMath(Double.parseDouble(prmScreenText.substring(0,prmScreenText.length()-3)),FeetMath.YDS);
                switch (key) {
                    case FET:
                        prmScreenText = tmp.toString();
                        break;
                    case INC:
                        prmScreenText = tmp.getInches() + "\"";
                        break;
                    case MTR:
                        prmScreenText = tmp.getMeter() + "m";
                        break;
                    case CMT:
                        prmScreenText = tmp.getCentiMeter() + "cm";
                        break;
                    case MMT:
                        prmScreenText = tmp.getMilliMeter() + "mm";
                        break;
                    case YDS:
                        break;
                }
                break;
        }
    }
}
