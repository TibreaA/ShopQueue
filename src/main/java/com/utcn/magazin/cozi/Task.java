package com.utcn.magazin.cozi;

public class Task implements Comparable<Task> {
    int id;
    private int arrivalTime;
    private int processingTime;
    // finnishTime = arrival + processing




    public Task(int arrivalTime, int processingTime) {
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(arrivalTime,o.getArrivalTime());
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    @Override
    public String toString() {
        return "{" + id +
                " ," + arrivalTime +
                ", " + processingTime +
                '}';
    }
}
