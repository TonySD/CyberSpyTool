package com.tonysd.nmap;

import com.tonysd.entities.Machine;
import com.tonysd.entities.Port;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortScan {
    private static final String machineScanCommand = "nmap -sC -sVVV %s";
    private static final Pattern portInfoPattern = Pattern.compile("(\\d+)\\/(\\w+)\\s+(\\w+)\\s+(\\S+)\\s+(.*)", Pattern.MULTILINE);
    private static final Pattern portSmallInfoPattern = Pattern.compile("(\\d+)\\/(\\w+)\\s+(\\w+)\\s+(\\S+)", Pattern.MULTILINE);
    public static List<Port> scanMachine(String ip) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(machineScanCommand.formatted(ip).split(" "));
        Process nmapScanner = builder.start();
        nmapScanner.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(nmapScanner.getInputStream()));

        return parseNmapOutput(reader);
    }

    private static List<Port> parseNmapOutput(BufferedReader outputStream) throws IOException {
        List<Port> ports = new ArrayList<>();
        Port currentPort = null;
        String outputLine, port, protocol, state, service, version;
        String[] additionalInfo;
        Matcher matcher;

        while ((outputLine = outputStream.readLine()) != null && !outputLine.isBlank()) {
            if (Character.isDigit(outputLine.charAt(0))) {
                if ((matcher = portInfoPattern.matcher(outputLine)).find()) {
                    if (currentPort != null) ports.add(currentPort);
                    port = matcher.group(1);
                    protocol = matcher.group(2);
                    state = matcher.group(3);
                    service = matcher.group(4);
                    version = matcher.group(5);

                    currentPort = new Port(
                            Short.parseShort(port),
                            protocol,
                            state,
                            service,
                            version
                    );
                } else if ((matcher = portInfoPattern.matcher(outputLine)).find()) {
                    if (currentPort != null) ports.add(currentPort);
                    port = matcher.group(1);
                    protocol = matcher.group(2);
                    state = matcher.group(3);
                    service = matcher.group(4);

                    currentPort = new Port(
                            Short.parseShort(port),
                            protocol,
                            state,
                            service
                    );
                }



            } else if (outputLine.charAt(0) == '|') {
                additionalInfo = outputLine.substring(2).split(":\\s*", 2);
                if (additionalInfo.length > 1)
                    currentPort.getAdditional_info().put(
                            additionalInfo[0],
                            additionalInfo[1]
                    );
                else if (additionalInfo.length == 1) {
                    currentPort.getAdditional_info().put(
                            additionalInfo[0],
                            ""
                    );
                }
            }
        }

        if (currentPort != null) ports.add(currentPort);

        outputStream.close();
        return ports;
    }
}
