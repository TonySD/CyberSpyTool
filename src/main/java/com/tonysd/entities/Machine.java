package com.tonysd.entities;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Machine {
    private List<Port> openedPorts = new ArrayList<>();
    private String ip;
}
