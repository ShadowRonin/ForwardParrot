package com.parrot.freeflight.milTools;

import android.util.Log;

public class DroneCompass {
    
    private static double northInDegMag = 0.0;
    private static double latestRotZ = 0.0;
    private static int test =0;
    
    /* Nav data updates this as frequently as it wants */
    public static void updateRotZ(double rotZ) {
        latestRotZ = rotZ;
        Log.w("BestDroneTeam", String.valueOf(rotZ));
        System.out.println("ROTZ " + rotZ);
    }
    
    /* UI calls this on command */
    public static void markNorth() {
        northInDegMag = latestRotZ;
    }
    
    /* UI calls this at set frequency */
    public static int getAzimuthInMils() {
        
        double azInDegrees = latestRotZ - northInDegMag;
        int azInMils = (int) (azInDegrees * (6400 / 6.283));
        if (azInMils < 0) {
            azInMils += 6400;
        }
        if (azInMils >= 6400) {
            azInMils -= 6400;
        }
        //test++;
        //return test;
        return azInMils;
    }
    
}
