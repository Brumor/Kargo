package com.app.kargo;

/**
 * Created by pbric on 26/07/2017.
 */

public class Delivery {


    private int Id;
    private String deliveryStartingPoint;
    private String deliveryDestination;
    private String userName;
    private int packageNumber;
    private int duration;
    private boolean isDelivered;

    public Delivery(int id, String deliveryStartingPoint, String deliveryDestination, String userName, int packageNumber, int duration, boolean isDelivered) {
        Id = id;
        this.deliveryStartingPoint = deliveryStartingPoint;
        this.deliveryDestination = deliveryDestination;
        this.userName = userName;
        this.packageNumber = packageNumber;
        this.duration = duration;
        this.isDelivered = isDelivered;
    }


    public String getDeliveryStartingPoint() {
        return deliveryStartingPoint;
    }

    public String getDeliveryDestination() {
        return deliveryDestination;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public int getDuration() {
        return duration;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return Id;
    }

    public boolean isDelivered() {return isDelivered;}

    public void setDelivered(boolean delivered) {isDelivered = delivered;}
}
