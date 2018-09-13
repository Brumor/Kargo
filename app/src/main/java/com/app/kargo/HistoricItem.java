package com.app.kargo;

/**
 * Created by pbric on 31/07/2017.
 */

public class HistoricItem {



    private String time;
    private String name;
    private int amount;

    public HistoricItem(String time, String name, int amount) {
        this.name = name;
        this.time = time;
        this.amount = amount;
    }


    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
