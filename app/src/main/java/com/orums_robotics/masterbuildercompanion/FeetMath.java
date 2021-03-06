package com.orums_robotics.masterbuildercompanion;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryuci on 15. 10. 3..
 */
public class FeetMath {
    // independent variable
    private double inches;  // = inches if dim is non-zero

    // dependent on inches i.e. converted from inches
    private String encodedValue;
    private double feet, inch, fraction;
    private double m, cm, mm;
    private double yds;
    private int dim;      // unit dimension: 0-scala 1-inch 2-square inch 3-cubic inch

    private static final double IN2YD = 0.027778;
    private static final double IN2M = 0.0254;
    private static final double M2YD = 1.093613;
    private static final double M2IN = 39.370079;
    private static final double YD2M = 0.9144;
    private static final double YD2IN = 36.;
    private static final double M2CM = 100.;
    private static final double M2MM = 1000.;
    private static final double MM2M = (1/M2MM);
    private static final double CM2M = (1/M2CM);

    public static final String INCH = "\"";
    public static final String M = "m";
    public static final String CM = "cm";
    public static final String MM = "mm";
    public static final String YDS = "yds";
    public static final String DEC = "dec";


    public FeetMath(String encodedValue)
    {
        this.encodedValue = encodedValue;
        Map<String,Double> map = decode(encodedValue);
        feet = map.get("feet");
        inch = map.get("inch");
        fraction = map.get("fraction");
        //System.out.println("feet="+feet+" inch="+inch+" fraction="+fraction);
        inches = feet * 12 + inch + fraction;
        m = inches * IN2M;
        cm = m * M2CM;
        mm = cm * CM2M * M2MM;
        yds = inches * IN2YD;
        dim = 1;
    }

    public FeetMath(double feet, double inch, double fraction)
    {
        this.feet = feet;
        this.inch = inch;
        this.fraction = fraction;
        inches = feet * 12 + inch + fraction;
        m = inches * IN2M;
        cm = m * M2CM;
        mm = cm * M2CM;
        yds = inches * IN2YD;
        encodedValue = encode(inches);
        dim = 1;
    }

    public FeetMath(double value) {
        inches = value;
        dim = 0;
    }

    public FeetMath(String length, String unit)
    {
        this(Double.parseDouble(length), unit);
    }

    public FeetMath(double length, String unit)
    {
        switch (unit) {
            case INCH:
                inches = length;
                m = inches * IN2M;
                cm = m * M2CM;
                mm = cm * CM2M * M2MM;
                yds = inches * IN2YD;
                break;
            case M:
                m = length;
                cm = m * M2CM;
                mm = cm * CM2M * M2MM;
                inches = m * M2IN;
                yds = m * M2YD;
                break;
            case CM:
                cm = length;
                m = cm * CM2M;
                mm = cm * CM2M * M2MM;
                inches = m * M2IN;
                yds = m * M2YD;
                break;
            case MM:
                mm = length;
                cm = mm * MM2M * M2CM;
                m = cm * CM2M;
                inches = m * M2IN;
                yds = m * M2YD;
                break;
            case YDS:
                yds = length;
                inches = yds * YD2IN;
                m = yds * YD2M;
                cm = m * M2CM;
                mm = cm * CM2M * M2MM;
                break;
            case DEC:
                inches = length;
                m = yds = cm = mm = 0;
                break;
        }
        if (unit == DEC) {
            dim = 0;
        }
        else {
            dim = 1;
            encodedValue = encode(inches);
            Map<String,Double> map = decode(encodedValue);
            feet = map.get("feet");
            inch = map.get("inch");
            fraction = map.get("fraction");
        }
    }

    public double getInches() 		{ return inches; }
    public double getMeter() 		{ return m; }
    public double getCentiMeter() 	{ return cm; }
    public double getMilliMeter() 	{ return mm; }
    public double getYds()			{ return yds; }
    public int 	  getDim()			{ return dim; }

    // Encode decimal inch value to {feet}' {inch}" {fraction1}/{fraction2} format of String
    public String encode(double inches)
    {
        int intValue;
        double residue;
        String ret = "", suffix = "";

        if (inches == 0) return "0";

        // compose feet part
        intValue = (int)(inches / 12);
        residue = inches / 12 - intValue;
        //System.out.println("intValue="+intValue+" residue="+residue);

        if (intValue != 0) {
            ret += intValue;
            ret += "'";
            suffix = " ";
        }

        // compose inch part
        intValue = (int)(residue * 12);
        residue = residue * 12 - intValue;
        //System.out.println("intValue="+intValue+" residue="+residue);
        // Executing following 3 lines should be retarded until fraction composition
        // where inch value could be added up by 1.
        //if (intValue != 0) {
        //	ret += suffix + intValue;
        //}

        // compose fraction part
        if (residue != 0) {
            int intValue2 = (int)Math.round(residue * 16);
            if (intValue2 == 16) {
                ret += suffix + (intValue+1) + "\"";
            } else if (intValue2 != 0) {
                if (intValue != 0) {
                    ret += suffix + intValue + "\""; 		// retarded execution
                    suffix = " ";
                }
                ret += suffix + intValue2 + "/16";
            }
            else {
                if (intValue != 0) {
                    ret += suffix + intValue;    // retarded execution
                    ret += "\"";
                }
            }
        }
        else {
            if (intValue != 0) {
                ret += suffix + intValue;        // retarded execution
                ret += "\"";
            }
        }
        //System.out.println("inches"+inches+"formatValue = "+ret);

        return ret;
    }

    private double toInches(double value, String unit)
    {
        switch (unit) {
            case INCH:
                break;
            case M:
                value *= M2IN;
                break;
            case CM:
                value *= CM2M * M2IN;
                break;
            case MM:
                value *= MM2M * M2IN;
                break;
            case YDS:
                value *= YD2IN;
                break;
        }
        return value;
    }


    public FeetMath add(FeetMath operand2)
    {
        FeetMath ret = null;
        if (dim != operand2.dim) {
            // Throw exception here!!!return null;
            System.out.println("Adding different dimensions is not allowed!");
        } else {
            ret = new FeetMath(inches + operand2.inches, dim == 0 ? DEC : INCH);
        }
        return ret;
    }

    public FeetMath subtract(FeetMath operand2)
    {
        FeetMath ret = null;

        if (dim != operand2.dim) {
            // Throw exception here!!!
            System.out.println("Subtracting different dimensions is not allowed!");
        } else {
            ret = new FeetMath(inches - operand2.inches, dim == 0 ? DEC : INCH);
        }
        return ret;
    }


    public FeetMath multiply(FeetMath operand2)
    {
        FeetMath ret = new FeetMath(inches * operand2.inches, INCH);
        ret.dim = dim + operand2.dim;
        return ret;
    }


    public FeetMath divide(FeetMath operand2)
    {
        FeetMath ret = new FeetMath(inches / operand2.inches, INCH);
        ret.dim = dim - operand2.dim;

        if (ret.dim < 0) {
            // Throw exception here!!!
            System.out.println("Inverse unit is not allowed!");
            ret = null;
        }
        return ret;
    }


    public FeetMath round()
    {
        return new FeetMath(Math.round(inches),  dim == 0 ? DEC : INCH);
    }


    // expected value format: {feet}' {inch}" {fraction1}/{fraction2}
    private Map<String,Double> decode(String valueFormatted)
    {
        Map<String,Double> ret = new HashMap<String,Double>();

        // get feet if there is ' in current valueFormatted
        int idx = valueFormatted.indexOf("'");
        if (idx == -1)
            ret.put("feet", 0.0);
        else {
            ret.put("feet", Double.parseDouble(valueFormatted.substring(0, idx)));
            valueFormatted = valueFormatted.substring(idx+1, valueFormatted.length()).trim();
        }

        // get inch and fraction if there is " in current valueFormatted
        int inchMarkIdx = valueFormatted.indexOf("\"");
        if (inchMarkIdx == -1) {
            // neither inch nor fraction exists
            ret.put("inch", 0.0);
            ret.put("fraction", 0.0);
            return ret;
        }
        else {
            // 1" or 1" 1/8 or 1/8"
            idx = valueFormatted.indexOf(" ");
            if (idx == -1) {
                // 1" or 1/8"
                idx = valueFormatted.indexOf("/");
                if (idx == -1) {
                    // 1"
                    ret.put("inch", Double.parseDouble(valueFormatted.substring(0, inchMarkIdx)));
                    ret.put("fraction", 0.0);
                    return ret;
                }
                else {
                    // 1/8"
                    // get fraction later
                    ret.put("inch",  0.0);
                }
            } else {
                // 1" 1/8
                // get inch now and get fraction later
                ret.put("inch", Double.parseDouble(valueFormatted.substring(0, idx-1)));
                valueFormatted = valueFormatted.substring(idx, valueFormatted.length()).trim();
            }
        }

        // get fraction
        double fraction1, fraction2;
        idx = valueFormatted.indexOf("/");
        if (idx == -1) {
            // should not reach here
            ret.put("fraction", 0.0);
            System.out.println("Algorithm failure!!!");
        }
        else {
            fraction1 = Double.parseDouble(valueFormatted.substring(0, idx));
            valueFormatted = valueFormatted.substring(idx+1, valueFormatted.length()).trim();
            fraction2 = Double.parseDouble(valueFormatted);
            ret.put("fraction", fraction1/fraction2);
        }
        return ret;
    }

    @Override
    public String toString()
    {
        if (dim == 0) return String.valueOf(inches);
        else return encodedValue;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FeetMath) {
            FeetMath tmp = (FeetMath)obj;
            return tmp.encodedValue.equals(encodedValue);
        } else
            return false;
    }

    @Override
    public int hashCode()
    {
        return encodedValue.hashCode();
    }

    static public void runFeetMathEx()
    {
        String format = "\t{0}\n\t{1} inches\n\t{2} meters\n\t{3} cm\n\t{4} mm\n\t{5} yds\n";
        FeetMath a = new FeetMath("13' 5\"");
        System.out.println("a:\n"+ MessageFormat.format(format,
                a,
                a.getInches(),
                a.getMeter(),
                a.getCentiMeter(),
                a.getMilliMeter(),
                a.getYds()));

        FeetMath b = new FeetMath(a.getInches(),INCH);
        System.out.println("b:\n"+MessageFormat.format(format,
                b,
                b.getInches(),
                b.getMeter(),
                b.getCentiMeter(),
                b.getMilliMeter(),
                b.getYds()));

        FeetMath c = a.add(new FeetMath(10,INCH));
        System.out.println("c:\n"+MessageFormat.format(format,
                c,
                c.getInches(),
                c.getMeter(),
                c.getCentiMeter(),
                c.getMilliMeter(),
                c.getYds()));

        FeetMath d = a.multiply(new FeetMath(10,INCH));
        System.out.println("d:\n"+MessageFormat.format(format,
                d,
                d.getInches(),
                d.getMeter(),
                d.getCentiMeter(),
                d.getMilliMeter(),
                d.getYds()));

        FeetMath e = new FeetMath(a.getInches(),INCH); //.add(new FeetMath(0.1, INCH));
        System.out.println("e:\n"+MessageFormat.format(format,
                e,
                e.getInches(),
                e.getMeter(),
                e.getCentiMeter(),
                e.getMilliMeter(),
                e.getYds()));

        FeetMath f = new FeetMath("13' 5\" 1/16");
        System.out.println("a:\n"+ MessageFormat.format(format,
                a,
                a.getInches(),
                a.getMeter(),
                a.getCentiMeter(),
                a.getMilliMeter(),
                a.getYds()));


        if (a == b)
            System.out.println("a,b - same");
        else
            System.out.println("a,b - different");

        if (a == c)
            System.out.println("a,c - same");
        else
            System.out.println("a,c - different");

    }

}