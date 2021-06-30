package com.example.hoang.devices;

import com.example.hoang.MQTT;
import java.util.concurrent.TimeUnit;

import com.example.hoang.MQTTCallback;
import com.example.hoang.devices.DoAm;
import com.example.hoang.devices.NhietDo;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DataHandler {
    public NhietDo CamBienNhietDo;
    public DoAm CamBienDoAm;
    public MQTT mqttconnection;
    public Light den;
    public String username = "AustrianOak/f/";
    public boolean connected;

    public boolean MQTT_connect() throws InterruptedException {
//        while (!mqttconnection.connect()){
//            Thread.sleep(5000);
//            mqttconnection.connect();
//        }
//        connected = true;
        return mqttconnection.connect();
    }
    public DataHandler() throws InterruptedException {
        CamBienNhietDo = new NhietDo("NhietDo", "NHIET_DO", "1");
        CamBienDoAm = new DoAm("DoAm", "DO_AM", "2");
        den = new Light("Den", "DEN", "3");
        mqttconnection = new MQTT();

    }

    public void subscribe_to_topics(){

        mqttconnection.subscribe(username+CamBienNhietDo.getTopic());
        mqttconnection.subscribe(username+CamBienDoAm.getTopic());
        mqttconnection.subscribe(username+den.getTopic());
        mqttconnection.client.setCallback(new MQTTCallback(){
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                System.out.println("Received message topic:" + topic);
//                System.out.println("Received message Qos:" + message.getQos());
//                System.out.println("Received message content:" + new String(message.getPayload()));
                String payload = new String(message.getPayload());
                System.out.println(payload);
                if (topic.equals(username+CamBienNhietDo.getTopic())){
                    CamBienNhietDo.add_data(Double.parseDouble(payload));
                }
                if (topic.equals(username+CamBienDoAm.getTopic())){
                    CamBienDoAm.add_data((Double.parseDouble(payload)));
                }
                if (topic.equals(username+den.getTopic())){
                    if (payload.equals("ON")){
                        den.turn_on();
                    }
                    else if(payload.equals("OFF")){
                        den.turn_off();
                    }
                }
            }
        });
    }

    public void command(String topic, String message){
        if (topic.equals(username + den.getTopic())){
            mqttconnection.publish_message(message, topic);
        }
    }
}
