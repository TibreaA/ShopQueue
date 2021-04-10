package com.utcn.magazin.cozi;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    int id;
    private BlockingQueue<Task> tasks;
    private AtomicInteger totalProcessingTime;
    private AtomicInteger totalNumberOfTasks;
    private AtomicInteger waitingPeriod;
    private Task currentTask ;


    public Server() {
        tasks = new ArrayBlockingQueue<Task>(1024);
        waitingPeriod = new AtomicInteger();
        totalNumberOfTasks = new AtomicInteger();
        totalProcessingTime = new AtomicInteger();
    }

    public Server(int id){
        this.id = id;
        tasks = new ArrayBlockingQueue<Task>(1024);
        waitingPeriod = new AtomicInteger();
        totalNumberOfTasks = new AtomicInteger();
        totalProcessingTime = new AtomicInteger();
    }

    public void addTask(Task newTask){
        if(newTask != null)
        this.tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getProcessingTime());
        totalProcessingTime.addAndGet(newTask.getProcessingTime());
        totalNumberOfTasks.incrementAndGet();
    }
    public double getAvragePerServer(){
        System.out.println("Time : " + totalProcessingTime.get() + " nr : " + totalNumberOfTasks.get());
        double avrageTime = totalProcessingTime.get();
        if(totalNumberOfTasks.get() != 0)
            avrageTime /= totalNumberOfTasks.get();
        return avrageTime;
    }
    @Override
    public void run() {
        try {

        while(true){
                 this.currentTask = tasks.take();
            //System.out.println("waiting : " + currentTask.getProcessingTime() + " ,Thread : " + Thread.currentThread());
            //System.out.println(Thread.currentThread());

                while(currentTask.getProcessingTime() >= 1) {
                    Thread.sleep(1000);
                    currentTask.setProcessingTime(currentTask.getProcessingTime()-1);
                    waitingPeriod.addAndGet(-1);
                    System.out.println(currentTask);
                }
                currentTask = null;

        }

        }catch(InterruptedException e){
            System.out.println(e.getMessage() + " here ");
            Thread.currentThread().interrupt();

        }

    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    @Override
    public String toString() {
        return "Queue  " + id + " :";
    }
}
