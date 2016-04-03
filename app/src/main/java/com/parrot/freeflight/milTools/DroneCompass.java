package com.parrot.freeflight.milTools;

import android.content.Context;

import android.util.Log;

import java.io.File;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FilenameFilter;
import java.util.List;

import android.content.pm.*;
import android.content.Context;
public class DroneCompass {
    
    private double northInDegMag;
    private static double latestRotZ;
    private boolean fileFound;
    private Context contexM;
    private File dataFile;

    public DroneCompass(Context contextM){
        dataFile = null;
        try{
            walk(getDataDir(contextM));
            //walk(".");
        }catch (Exception e){

        }
        fileFound = false;
        northInDegMag = 0.0;
        latestRotZ = 0.0;
        this.contexM = contexM;
    }

    /* Nav data updates this as frequently as it wants */
    public static void updateRotZ(double rotZ) {
        latestRotZ = rotZ;
        Log.w("BestDroneTeam", String.valueOf(rotZ));
        System.out.println("ROTZ " + rotZ);
    }
    
    /* UI calls this on command */
    public void markNorth() {
        northInDegMag = latestRotZ;
    }
    
    /* UI calls this at set frequency */
    public int getAzimuthInMils() {
        
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
//    public File getNavFile(){
//        if()
////        try{
////            File dir = new File(getDataDir(this.contexM));
////
////            File[] matches = dir.listFiles(new FilenameFilter()
////            {
////                public boolean accept(File dir, String name)
////                {
////                    return name.startsWith("temp") && name.endsWith(".txt");
////                }
////            });
////            if(matches.length > 0)
////                return matches[0];
////            return null;
////        }catch (Exception e){
////            fileFound = false;
////            return null;
////        }
//    }
    public void updateDataFile(File aFile){
        fileFound = true;
        dataFile = aFile;
        System.out.println(aFile.getName());
    }

    public static String getDataDir(Context context) throws Exception {
        return context.getPackageManager()
                .getPackageInfo(context.getPackageName(), 0)
                .applicationInfo.dataDir;
    }

                public void walk( String path ) {

                    System.out.println("Hello");
                    File root = new File( path );
                    File[] list = root.listFiles();

                    if (list == null) return;

                    for ( File f : list ) {
                     //System.out.println("Name: " + f.getAbsolutePath());
                        if ( f.isDirectory() ) {
                            walk(f.getAbsolutePath());
                        }
                        else {
                            if(f.getAbsolutePath().contains("txt")){
                                //updateDataFile(f);
                                System.out.println("Dir:" + f.getAbsoluteFile());
                            }
                        }
                    }
                }


}
