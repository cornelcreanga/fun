package com.ccreanga.various;

import com.ccreanga.various.airport.Airplane;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LogAnalysys {


    static class Entry{
        public String shard;
        public String file;
        public Integer duration;

        public Entry(String shard, String file, int duration) {
            this.shard = shard;
            this.file = file;
            this.duration = duration;
        }
    }

    public static void main(String[] args) {
        CSVParser csvParser = null;
        List<Entry> entries = new ArrayList<>();
        String message = "";
        try {
            csvParser = new CSVParser(new BufferedReader(new FileReader("/home/cornel/xdmData.csv")), CSVFormat.DEFAULT);

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            List<Thread> threadList = new ArrayList<>();

            for (CSVRecord csvRecord : csvRecords) {
                //System.out.println(csvRecord);
                message = csvRecord.get(15);
                if (!message.contains("save operation"))
                    continue;
                int i1 = message.indexOf("duration=");
                if (i1==-1)
                    continue;
                int i2 = message.indexOf(" ",i1+"duration=".length());
                if (i2==-1)
                    i2 = message.indexOf(",",i1+"duration=".length());
                String time;
                if (i2!=-1)
                    time = message.substring(i1+"duration=".length(),i2);
                else
                    time = message.substring(i1+"duration=".length());

                i1 = message.indexOf("shard=");
                i2 = message.indexOf(" ",i1);
                String shard = message.substring(i1+"shard=".length(),i2);

                entries.add(new Entry(shard, "", Integer.parseInt(time)));


            }
        } catch (Exception e) {
            System.out.println(message);
            System.exit(1);
        }
        entries.sort(((Comparator<Entry>) (entry, t1) -> {
            return entry.duration.compareTo(t1.duration);
        }).reversed());

        System.out.println(entries.get(0));
    }
}
