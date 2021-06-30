package com.example.hoang.devices;

public class Devices {
    private String name;
    private String topic;
    private String id;

    public Devices(String name, String topic, String id){
        this.name = name;
        this.topic = topic;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
