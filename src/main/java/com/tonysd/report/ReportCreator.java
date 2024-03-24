package com.tonysd.report;

import com.tonysd.entities.Machine;
import com.tonysd.entities.Port;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportCreator {
    private static final String reportFilePath = "./report.txt";

    public static void makeReport(List<Machine> machineList, String filename) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        for (Machine machine : machineList) {
            writer.write("Machine %s ports:\n".formatted(machine.getIp()));
            for (Port port : machine.getOpenedPorts()) {
                writer.write("Port %d:\n".formatted(port.getPort_id()));
                writer.write("\tProtocol: %s\n".formatted(port.getProtocol()));
                writer.write("\tState: %s\n".formatted(port.getState()));
                writer.write("\tService: %s\n".formatted(port.getService()));
                writer.write("\tVersion: %s\n".formatted(port.getVersion()));
                if (!port.getAdditional_info().isEmpty()) writer.write("\tAdditional info: \n");
                for (String key : port.getAdditional_info().keySet()) {
                    writer.write("\t\t%s: %s\n".formatted(key, port.getAdditional_info().get(key)));
                }
            }
            writer.write("-------------------------------------------------------\n");
        }

        writer.close();
    }

    public static void makeReport(List<Machine> machineList) throws IOException {
        makeReport(machineList, reportFilePath);
    }
}
