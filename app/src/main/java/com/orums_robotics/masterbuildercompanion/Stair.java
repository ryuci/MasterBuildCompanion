package com.orums_robotics.masterbuildercompanion;

/**
 * Created by ryuci on 15. 10. 6..
 */
public class Stair {

    private static final FeetMath stairStd = new FeetMath("17\" 3/4");
    private FeetMath rise, run;
    private FeetMath riserHeight, risers, riserErr;
    private FeetMath treadWidth, treads, treadErr;
    private FeetMath open;
    private FeetMath stringer;
    private double incline;


    Stair(FeetMath riserHeight, FeetMath rise) {
        this.riserHeight = riserHeight;
        this.rise = rise;
        calcStair();
    }

    private void calcStair() {
        risers = rise.divide(riserHeight).round();
        riserErr = rise.subtract(riserHeight.multiply(risers));

        treadWidth = stairStd.subtract(riserHeight);
        treads = risers.subtract(new FeetMath(1,FeetMath.DEC));
        run = treadWidth.multiply(treads);
        treadErr = run.subtract(treadWidth.multiply(treads));;
        open = run.subtract(treadWidth);

        double x = run.getInches() - treadWidth.getInches();
        double y = rise.getInches() - risers.getInches() * 2;
        incline = Math.toDegrees(Math.atan(y / x));
        stringer = new FeetMath(Math.sqrt(x*x+y*y), FeetMath.INCH);
    }

    public String getRise() { return rise.toString(); }
    public String getRun() { return run.toString();}
    public String getRiserHeight() { return riserHeight.toString(); }
    public String getRisers() { return risers.toString(); }
    public String getRiserErr() { return riserErr.toString(); }
    public String getTreadWidth() { return treadWidth.toString(); }
    public String getTreads() { return treads.toString(); }
    public String getTreadErr() { return treadErr.toString(); }
    public String getOpen() { return open.toString(); }
    public String getStringer() { return stringer.toString(); }
    public String getIncline() { return incline + ""; }


    static public void runStiarsEx() {
        FeetMath riserHeight = new FeetMath("6\" 7/8");
        FeetMath rise = new FeetMath("9' 2\" 1/8");
        Stair stair = new Stair(riserHeight, rise);
        System.out.println("riserHeight = " + stair.riserHeight);
        System.out.println("rise = " + stair.rise);
        System.out.println("risers = " + stair.risers);
        System.out.println("riserErr = " + stair.riserErr);
        System.out.println("treadWidth = " + stair.treadWidth);
        System.out.println("treads = " + stair.treads);
        System.out.println("treadErr = " + stair.treadErr);
        System.out.println("open = " + stair.open);
        System.out.println("stringer = " + stair.stringer);
        System.out.println("incline = " + stair.incline);
        System.out.println("run = " + stair.run.getInches());
    }

}
