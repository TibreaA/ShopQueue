package com.utcn.magazin.decision;

import com.utcn.magazin.cozi.Server;
import com.utcn.magazin.cozi.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);

    public enum SelectionPolicy{
        SHORTEST_QUEUE, SHORTEST_TIME
    }

}
