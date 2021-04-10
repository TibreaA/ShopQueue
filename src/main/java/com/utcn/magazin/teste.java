package com.utcn.magazin;

import com.utcn.magazin.management.SimulationManager;

public class teste {
    public static void main(String[] args) {
       /**Server server = new Server();
        Thread thread = new Thread(server);
        Task task1 = new Task(1,1);
        Task task2 = new Task(3,4);
       server.addTask(task1);
       server.addTask(task2);
        System.out.println(server.getTasks());
        thread.start();*/


      //  Scheduler scheduler = new Scheduler(2,100);
      //  scheduler.dispatchTask(task1);
     //   scheduler.dispatchTask(task2);
        SimulationManager simulationManager = new SimulationManager();
        Thread thread = new Thread(simulationManager);
        thread.start();
    }
}
