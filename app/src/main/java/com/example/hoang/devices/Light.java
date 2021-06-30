package com.example.hoang.devices;

import com.example.hoang.devices.Devices;

public class Light extends Devices {
    public Light(String name, String topic, String id) {
        super(name, topic, id);
    }
    // true equal light on, false equals light off
    public boolean state;

    public void turn_on(){
        state = true;
    }
    public void turn_off(){
        state = false;
    }
}

