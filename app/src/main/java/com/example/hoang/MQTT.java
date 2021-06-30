package com.example.hoang;

import android.view.Menu;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

public class MQTT {
//    private String subTopic;
//    private String pubTopic;
    private int qos;
    private String broker;
    private String clientId;
    private String UserName;
    private String PassWord;
    private MemoryPersistence persistence = new MemoryPersistence();
    public MqttClient client;
    private boolean connected = false;

    public MQTT(String subTopic, String pubTopic, int qos, String broker, String clientId, String UserName, String PassWord){
//        this.subTopic = subTopic;
//        this.pubTopic = pubTopic;
        this.qos = qos;
        this.broker = broker;
        this.clientId = clientId;
        this.UserName = UserName;
        this.PassWord = PassWord;
    }

    public MQTT(){
//        this.subTopic = "AustrianOak/f/test";
//        this.pubTopic = "AustrianOak/f/test";
        this.qos = 2;
        this.broker = "tcp://io.adafruit.com:1883";
        this.clientId = "";
        this.UserName = "AustrianOak";
        this.PassWord = "aio_prTO40dLmWHKCfo8Z114nxqGsRKc";
    }

    public boolean connect() {
        try {
            this.client = new MqttClient(this.broker, this.clientId, this.persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(this.UserName);
            connOpts.setPassword(this.PassWord.toCharArray());
            connOpts.setCleanSession(true);
            client.setCallback(new MQTTCallback());
            System.out.println("Connecting to a broker"+this.broker);
            client.connect(connOpts);
            System.out.println("Connected");
            this.connected = true;
            return true;

        }catch (MqttException me){
            show_exception(me);
            return false;
        }
    }

    public boolean publish_message(String content, String pubTopic) {
        try {
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(this.qos);
            this.client.publish(pubTopic, message);
            System.out.println("Message published");
            return true;

        }catch (MqttException me){
            show_exception(me);
            return false;
        }
    }

//    public boolean subcribe(){
//        try{
//            this.client.subscribe(this.subTopic);
//            return true;
//
//        }catch (MqttException me){
//            show_exception(me);
//            return false;
//        }
//    }
    public boolean subscribe(String topic){
        try{
            this.client.subscribe(topic);
            return true;

        }catch (MqttException me){
            show_exception(me);
            return false;
        }
    }

    static public void show_exception(MqttException err){
        System.out.println("reason"+err.getReasonCode());
        System.out.println("msg"+err.getMessage());
        System.out.println("loc"+err.getLocalizedMessage());
        System.out.println("cause"+err.getCause());
        System.out.println("excep"+err);
        err.printStackTrace();
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

//    public String getSubTopic() {
//        return subTopic;
//    }

//    public void setSubTopic(String subTopic) {
//        this.subTopic = subTopic;
//    }

//    public String getPubTopic() {
//        return pubTopic;
//    }

//    public void setPubTopic(String pubTopic) {
//        this.pubTopic = pubTopic;
//    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
