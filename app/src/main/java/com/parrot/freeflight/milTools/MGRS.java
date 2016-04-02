package com.parrot.freeflight.milTools;

import com.ibm.util.CoordinateConversion;
/**
 * Created by pjhudgins on 4/2/2016.
 */
public class MGRS {

    public static String fullGrid(double lat, double lon) {
        CoordinateConversion c = new CoordinateConversion();

        String fullgrid = c.latLon2MGRUTM(lat, lon);
        return fullgrid;
    }

    public static String eightGrid(double lat, double lon) {
        CoordinateConversion c = new CoordinateConversion();

        String fullgrid = c.latLon2MGRUTM(lat, lon);
        String easting = fullgrid.substring(5, 9);
        String northing = fullgrid.substring(10, 14);
        return easting + " " + northing;
    }
}
