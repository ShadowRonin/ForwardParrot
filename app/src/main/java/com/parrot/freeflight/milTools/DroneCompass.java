
public class DroneCompass {
    
    private double northInDegMag;
    private double latestRotZ;
    
    public DroneCompass() {
        latestRotZ = 0;
        markNorth();
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
    
}
