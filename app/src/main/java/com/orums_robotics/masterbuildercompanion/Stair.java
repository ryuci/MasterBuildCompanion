package com.orums_robotics.masterbuildercompanion;

/**
 * Created by ryuci on 15. 10. 6..
 */
public class Stair {


    private FeetMath rise, run;
    private FeetMath riserHeight, risers, riserErr;
    private FeetMath treadWidth, treads, threadErr;
    private FeetMath open;
    private FeetMath stringer;
    private FeetMath incline;

    Stair(FeetMath riserHeight, FeetMath rise) {
        this.riserHeight = riserHeight;
        this.rise = rise;
        calcStair();
    }

    private void calcStair() {
        risers = rise.divide(riserHeight);
        System.out.println(rise);
        System.out.println(riserHeight);

        System.out.println(risers);

        treads = risers.subtract(new FeetMath(1,FeetMath.DEC));
    }

    static public void runStiarsEx() {
        Stair stair = new Stair(new FeetMath("6\" 7/8"), new FeetMath("110\" 1/8"));
        System.out.printf("risers = %s\ntreads = %s\n",stair.risers, stair.treads);

    }

}
