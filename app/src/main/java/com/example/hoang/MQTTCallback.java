package com.example.hoang;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTCallback implements MqttCallback{

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("disconnect, you can reconnect");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Receive message topic"+topic);
        System.out.println("Received message qos:"+message.getQos());
        System.out.println("Received message content"+ new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete-------------"+token.isComplete());
    }
}
