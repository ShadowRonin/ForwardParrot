package com.parrot.freeflight.milTools;
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
    private double latestRotZ;
    private boolean fileFound;
    private Context contexM;
    private File dataFile;

    public DroneCompass(Context contextM){
        dataFile = null;
        try{
            walk(getDataDir(contextM));
        }catch (Exception e){

        }
        fileFound = false;
        northInDegMag = 0.0;
        latestRotZ = 0.0;
        this.contexM = contexM;
    }

    /* Nav data updates this as frequently as it wants */
    public void updateRotZ(double rotZ) {
        latestRotZ = rotZ;
    }
    
    /* UI calls this on command */
    public void markNorth() {
        northInDegMag = latestRotZ;
    }
    
    /* UI calls this at set frequency */
    public int getAzimuthInMils() {
        
        double azInDegrees = latestRotZ - northInDegMag;
        int azInMils = (int) (azInDegrees * (6400 / 360));
        if (azInMils < 0) {
            azInMils += 6400;
        }
        if (azInMils >= 6400) {
            azInMils -= 6400;
        }
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

                    File root = new File( path );
                    File[] list = root.listFiles();

                    if (list == null) return;

                    for ( File f : list ) {
                        if ( f.isDirectory() ) {
                            walk( f.getAbsolutePath() );
                            System.out.println( "Dir:" + f.getAbsoluteFile() );
                        }
                        else {
                            if(f.getName().contains('mesures')){
                                updateDataFile(f);
                            }
                        }
                    }
                }


}
