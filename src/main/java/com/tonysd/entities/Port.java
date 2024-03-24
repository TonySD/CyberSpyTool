package com.tonysd.entities;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Port {
    short port_id;
    String protocol;
    String state;
    String service;
    String version;
    Map<String, String> additional_info = new HashMap<>();

    public Port(short port_id) {
        this.port_id = port_id;
    }

    public Port(short port_id, String protocol, String state, String service, String version) {
        this.port_id = port_id;
        this.protocol = protocol;
        this.state = state;
        this.service = service;
        this.version = version;
    }

    public Port(short port_id, String protocol, String state, String service) {
        this.port_id = port_id;
        this.protocol = protocol;
        this.state = state;
        this.service = service;
    }
}
