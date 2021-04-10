package com.utcn.magazin.management;

import com.utcn.magazin.cozi.Server;
import com.utcn.magazin.cozi.Task;
import com.utcn.magazin.decision.Strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {

    private int timeLimit = 60;
    private  int maxProcessingTime = 7;
    private  int minProcessingtime = 1;
    private   int maxArrivalTime = 40;
    private  int minArrivaltime = 2;
    private  int numberOfServers = 5;
    private  int numberOfClients = 50;
    private  double peakHour = 0;
    private double avrageTime=0;
    private  Strategy.SelectionPolicy selectionPolicy = Strategy.SelectionPolicy.SHORTEST_TIME;


    private Scheduler scheduler;
   /// private SimulationFrame frame;
    private List<Task> generatedTasks;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingtime, int maxArrivalTime, int minArrivaltime, int numberOfServers, int numberOfClients,Strategy.SelectionPolicy policy) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingtime = minProcessingtime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivaltime = minArrivaltime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.selectionPolicy = policy;
        this.scheduler = new Scheduler(this.numberOfServers,this.numberOfClients /(this.numberOfServers-1));
        this.generatedTasks = new ArrayList<Task>();
        generateNRandomTasks();
    }

    public SimulationManager() {
        scheduler = new Scheduler(numberOfServers,numberOfClients);
        generatedTasks = new ArrayList<Task>();
        generateNRandomTasks();
    }

    private void generateNRandomTasks(){
        Random random = new Random();
        int maxMinusMinProcessing = maxProcessingTime - minProcessingtime;
        int maxMinusMinArrival = maxArrivalTime - minArrivaltime;
        int freq[]  = new int[maxArrivalTime];
        int id =0;
        for(int i = 0 ;i < numberOfClients ;i++){
            int arrival = random.nextInt(maxMinusMinArrival) + minArrivaltime;
            int processing = random.nextInt(maxMinusMinProcessing) + minProcessingtime;
            generatedTasks.add(new Task(arrival,processing));
            freq[arrival]++;
        }


        generatedTasks.sort(Task::compareTo);
        
        for(Task task : generatedTasks){
            task.setId(id++);
        }
        int max =0;
        for(int i = 0 ;i < maxArrivalTime ;i++)
            if(freq[i] > max) {
                max = freq[i];
                peakHour = i;
            }

    }
    public String importantData(){
        StringBuilder string = new StringBuilder();

        if(generatedTasks.size() > 0)
            string.append("Waiting clients : " + generatedTasks + "\n");
        else
            string.append("Waiting clients : " + "\n");
        for(Server server : scheduler.getServers()){
            string.append(server);
            if(server.getCurrentTask() != null)
             string.append(server.getCurrentTask());
            else string.append("closed");
            string.append("\n");
        }


        return string.toString();
    }
    public String finalData(){
        StringBuilder string = new StringBuilder();

            string.append("\n");
            string.append("Avrage processing time : "  + avrageTime);
            string.append("\n");
            string.append("Peak Hour : "  + peakHour);
            string.append("\n");
        return string.toString();
    }
    @Override
    public void run() {
        try {
            System.out.println(generatedTasks);
            int currentTime = 0;
            while(currentTime < minArrivaltime){
                currentTime++;
                Thread.sleep(1000);
                System.out.println(currentTime +  " : Before");
            }
            while (currentTime <= maxArrivalTime && currentTime <= timeLimit ) {
                System.out.println(currentTime);
                for (Iterator<Task> iterator = generatedTasks.iterator(); iterator.hasNext();) {
                    Task current = iterator.next();
                    if (current.getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(current);
                        iterator.remove();
                    }
                }
                currentTime++;
                Thread.sleep(1000);
            }

                while(currentTime < timeLimit){
                    currentTime++;
                    Thread.sleep(1000);
                    System.out.println(currentTime + " : Afther");
                }

                Thread.sleep(1000);
                for(Server server : scheduler.getServers()){
                    avrageTime += server.getAvragePerServer();
                }
                avrageTime /= scheduler.getServers().size();
            scheduler.closeThreads();
                System.out.println("Avrage Time : " + avrageTime);
            System.out.println("Peak hour : " + peakHour);

        }catch(InterruptedException e){
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        System.out.println(generatedTasks);
    }

    public int getTimeLimit() {
        return timeLimit;
    }
}
