package com.utcn.magazin.management;

import com.utcn.magazin.cozi.Server;
import com.utcn.magazin.cozi.Task;
import com.utcn.magazin.decision.ConcreteStrategyQueue;
import com.utcn.magazin.decision.ConcreteStrategyTime;
import com.utcn.magazin.decision.Strategy;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private List<Thread> threads;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy stategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<Server>();
        threads = new ArrayList<Thread>();
        for(int i=0 ;i < maxNoServers ;i++){
            Server server = new Server(i+1);
            Thread thread = new Thread(server);
            System.out.println(thread);
            servers.add(server);
            threads.add(thread);
            thread.start();

        }
        stategy = new ConcreteStrategyTime();
    }
    public void closeThreads(){
        for(Thread current : threads){
            current.interrupt();
        }
    }
    public void changeStrategy(Strategy.SelectionPolicy policy){
        if(policy == Strategy.SelectionPolicy.SHORTEST_QUEUE){
            stategy = new ConcreteStrategyQueue();
        }
        if(policy == Strategy.SelectionPolicy.SHORTEST_TIME){
            stategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t){
        if(stategy != null) {

            stategy.addTask(servers, t);
        }
    }

    public List<Server> getServers() {
        return servers;
    }
}
