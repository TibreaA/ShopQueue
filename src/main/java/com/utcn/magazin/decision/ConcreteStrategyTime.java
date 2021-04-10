package com.utcn.magazin.decision;

import com.utcn.magazin.cozi.Server;
import com.utcn.magazin.cozi.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) {
        if(servers.size() < 1)
            return ;
        Server server  = new Server();
        int minValue = Integer.MAX_VALUE;
        for(Server current : servers){
            if(current.getWaitingPeriod().get() < minValue){
                minValue = current.getWaitingPeriod().get();
                server = current;
            }
        }
        System.out.println("discpachet task : " + t.getProcessingTime() + " to " + server);
        server.addTask(t);
    }
}
