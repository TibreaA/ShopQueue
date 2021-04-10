package com.utcn.magazin.decision;

import com.utcn.magazin.cozi.Server;
import com.utcn.magazin.cozi.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) {
        if(servers.size() < 1)
            return ;
        Server server  = new Server();
        int minValue = Integer.MAX_VALUE;
        for(Server current : servers){
            if(current.getTasks().size() < minValue && server.getTasks().size() < 1024){
                minValue = current.getTasks().size();
                server = current;
            }
        }
        server.addTask(t);
    }
}