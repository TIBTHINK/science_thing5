package com.tibthink.science_thing5;

import android.location.Location;

public class CLocation extends Location {

    private boolean bUseMetricUnit = false;

    public CLocation(Location location){
        this(location, true);
    }

    public CLocation(Location location, boolean bUseMetricUnit) {
        super(location);
        this.bUseMetricUnit = bUseMetricUnit;
    }

    public boolean getUseMetricUnits() {
        return this.bUseMetricUnit;
    }

    public void setUseMetricUnits(boolean bUseMetricUnit){
        this.bUseMetricUnit = bUseMetricUnit;
    }

    public CLocation(String provider) {
        super(provider);
    }

    @Override
    public float distanceTo(Location dest) {
        float nDistence = super.distanceTo(dest);

        if (!this.getUseMetricUnits()) {
            //covert them meters to them nice feet pics
            nDistence = nDistence * 3.2808398501312f;
        }

        return nDistence;
    }

    @Override
    public double getAltitude(){
        double nAltitude = super.getAltitude();

        if (!this.getUseMetricUnits()) {
            //covert them meters to them nice feet pics
            nAltitude = nAltitude * 3.2808398501312f;
        }

        return nAltitude;
    }

    @Override
    public float getSpeed() {
        float nSpeed = super.getSpeed() * 3.6f;

        if (!this.getUseMetricUnits()) {
            //covert them meters a secound to them nice miles per syrup
            nSpeed = super.getSpeed() * 2.23693629f;
        }

        return nSpeed;
    }

    @Override
    public float getAccuracy() {
        float nAccuracy = super.getAccuracy();

        if (!this.getUseMetricUnits()) {
            //covert them meters to them nice feet pics
            nAccuracy = nAccuracy * 3.2808398501312f;
        }

        return nAccuracy;
    }
}
