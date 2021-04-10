package com.utcn.magazin.controller;

import com.utcn.magazin.decision.Strategy;
import com.utcn.magazin.management.SimulationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {

    @FXML
    public TextField numarClienti;
    @FXML
    public TextField numarCase;
    @FXML
    public TextField timpSimulare;
    @FXML
    public TextField timpMinAjuns;
    @FXML
    public TextField timpMaxAjuns;
    @FXML
    public TextField timpMin;
    @FXML
    public TextField timpMax;
    @FXML
    public TextArea rezultatSimulare;

    private SimulationManager simulationManager = new SimulationManager();

    private Strategy.SelectionPolicy policy = Strategy.SelectionPolicy.SHORTEST_TIME;

    public static String fieldtoString(TextField textField){
        return textField.getText();
    }
    public static String areatoString(TextArea textField){
        return textField.getText();
    }
    public void changeStrategyForTime(ActionEvent actionEvent) {
        policy = Strategy.SelectionPolicy.SHORTEST_TIME;
    }

    public void runSimulation(ActionEvent actionEvent) {
        int timpSim = 0;
        if(timpSimulare != null)
             timpSim = Integer.parseInt(Controller.fieldtoString(timpSimulare));
        int timpMaxSim = 0;
        if(timpMax != null)
            timpMaxSim = Integer.parseInt(Controller.fieldtoString(timpMax));
        int timpMinSim = 0;
        if(timpMin != null)
            timpMinSim = Integer.parseInt(Controller.fieldtoString(timpMin));
        int timpMaxArr = 0;
        if(timpMaxAjuns != null)
            timpMaxArr = Integer.parseInt(Controller.fieldtoString(timpMaxAjuns));
        int timpMinArr = 0;
        if(timpMinAjuns != null)
            timpMinArr = Integer.parseInt(Controller.fieldtoString(timpMinAjuns));
        int nrCase = 0;
        if(numarCase != null)
            nrCase = Integer.parseInt(Controller.fieldtoString(numarCase));
        int nrClienti = 0;
        if(numarClienti != null)
            nrClienti = Integer.parseInt(Controller.fieldtoString(numarClienti));


        simulationManager = new SimulationManager(timpSim,timpMaxSim,timpMinSim,timpMaxArr,timpMinArr,nrCase,nrClienti,policy);
        Thread thread = new Thread(simulationManager);
        UpdateUI updateUI = new UpdateUI(simulationManager,rezultatSimulare);
        Thread thread1 = new Thread(updateUI);
        thread.start();
        thread1.start();
    }


    public void changeStrategyForLength(ActionEvent actionEvent) {
        policy = Strategy.SelectionPolicy.SHORTEST_QUEUE;
    }


}
 class UpdateUI implements Runnable{

     private SimulationManager simulationManager ;
     private TextArea result;
     private BufferedWriter writer;
     private final String fileName = "D:\\proietcteTp\\proiect2\\resources\\result";

     public UpdateUI(SimulationManager simulationManager,TextArea real){
         this.simulationManager = simulationManager;
         this.result = real;
     }

     @Override
     public void run() {
         System.out.println("Update started");

        int currentTime = 0;
        int limit = simulationManager.getTimeLimit();
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(fileName,false);
            writer = new BufferedWriter(fileWriter);

            while (currentTime <= limit) {

                StringBuilder string = new StringBuilder();
                //happens now
                string.append("Time: " + currentTime + "\n");
                string.append(simulationManager.importantData());
                System.out.println(string.toString());
                string.append("\n");
                writer.write(string.toString());

                //happened in the past
                string.append(Controller.areatoString(result));
                string.append("\n");

                result.setText(string.toString());

                currentTime++;
                Thread.sleep(1000);
            }
            //avrage time , peak hour
            Thread.sleep(2000);
            StringBuilder string = new StringBuilder();
            string.append(simulationManager.finalData());
            writer.write(string.toString());

            string.append(Controller.areatoString(result));
            string.append("\n");
            result.setText(string.toString());

            writer.close();
        }catch(InterruptedException e){
            System.out.println(" happend here ");
            Thread.currentThread().interrupt();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
     }
 }
