package com.tonysd;

import com.tonysd.entities.Machine;
import com.tonysd.entities.Port;
import com.tonysd.nmap.MachineScan;
import com.tonysd.nmap.PortScan;
import com.tonysd.report.ReportCreator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<Machine> machineList;

        String[] ips = {"10.124.0.128/26", "10.124.0.192/26", "10.124.1.0/26", "10.124.1.64/26", "10.124.0.64/26", "10.154.2.0/23", "10.124.0.0/26", "10.154.0.0/23"};
        for (int i = 0; i < ips.length; ++i) {

            try {
                machineList = MachineScan.scanNetwork(ips[i]);
                ReportCreator.makeReport(machineList, "./%d.txt".formatted(i));
                System.out.printf("Parsed %d network!\n", i);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }

    }
}