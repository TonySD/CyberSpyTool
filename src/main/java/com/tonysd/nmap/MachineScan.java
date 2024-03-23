package com.tonysd.nmap;

import com.tonysd.entities.Machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MachineScan {
    private static final String networkScanCommand = "nmap -sn %s";
    private static final Pattern ipPattern = Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");;
    public static List<Machine> scanNetwork(String networkMask) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(networkScanCommand.formatted(networkMask).split(" "));
        Process nmapScanner = builder.start();
        nmapScanner.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(nmapScanner.getInputStream()));

        return parseNmapOutput(reader);
    }

    private static List<Machine> parseNmapOutput(BufferedReader outputStream) throws IOException {
        List<Machine> machines = new ArrayList<>();
        String outputLine;
        Matcher found_ips;

        while ((outputLine = outputStream.readLine()) != null) {
            found_ips = ipPattern.matcher(outputLine);
            if (found_ips.find())
                machines.add(
                        new Machine(found_ips.group())
                );
        }

        return machines;
    }
}
