package com.myz.wxapp.ticket;

/**
 * Ticket
 * Created by myz
 * Date 2020/12/1 15:00
 */

public class Ticket {

    public Ticket() {

    }

    /**
     * ticket name
     */
    private String name;

    /**
     * current id that can be used
     */
    private long currentId;

    /**
     * max id(excluding)
     */
    private long maxId;

    /**
     * cache step
     */
    private int step;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(long currentId) {
        this.currentId = currentId;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
