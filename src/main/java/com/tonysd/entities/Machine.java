package com.tonysd.entities;
import com.tonysd.nmap.PortScan;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Machine {
    private List<Port> openedPorts = new ArrayList<>();
    private String ip;

    public Machine(String ip) throws IOException, InterruptedException {
        this.ip = ip;
        openedPorts = PortScan.scanMachine(ip);
    }

    public Machine(List<Port> openedPorts, String ip) {
        this.openedPorts = openedPorts;
        this.ip = ip;
    }
}
