package com.ccreanga.various.airport;

import com.ccreanga.various.airport.messages.AirplaneMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Airport {


    public static void main(String[] args) throws Exception {

        BlockingQueue<AirplaneMessage> airplaneToControllers = new LinkedBlockingQueue<>();

        AirstripManager manager = new AirstripManager();
        WaitingPlanesManager waitingPlanesManager = new WaitingPlanesManager();

        Controller c1 = new Controller("First controller", waitingPlanesManager, airplaneToControllers, manager);
        Controller c2 = new Controller("Second controller", waitingPlanesManager, airplaneToControllers, manager);
        new Thread(c1).start();
        new Thread(c2).start();

        if (args.length != 1) {
            System.out.println("the config file path should be passed as an argument");
            System.exit(1);
        }

        CSVParser csvParser = null;
        try {
            csvParser = new CSVParser(new BufferedReader(new FileReader(args[0])), CSVFormat.DEFAULT);
        } catch (IOException e) {
            System.out.println("Can't parse the CSV file");
            System.exit(1);
        }
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        List<Thread> threadList = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            Airplane a = new Airplane(
                csvRecord.get(0),
                csvRecord.get(1).trim().equals("Regular"),
                csvRecord.get(2).trim().equals("Emergency"),
                Integer.parseInt(csvRecord.get(3).trim()),
                waitingPlanesManager,
                airplaneToControllers);
            System.out.println(a);

            Thread t = new Thread(a);
            t.start();
            threadList.add(t);
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        c1.stopController();
        c2.stopController();
    }

}
