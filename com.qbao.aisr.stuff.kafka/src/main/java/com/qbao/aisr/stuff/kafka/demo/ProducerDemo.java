package com.qbao.aisr.stuff.kafka.demo;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

public class ProducerDemo extends Thread {

    private String topic;

    public ProducerDemo(String topic){
        this.topic = topic;
    }

    public void run(){
        Producer producer = createProducer();

        int i = 0;

        while(true){
            String data = "message " + i++;

            producer.send(new KeyedMessage(this.topic, data));

            System.out.println("send data：" + data);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Producer createProducer() {
        Properties prop = new Properties();

        prop.put("zookeeper.connect", "10.172.30.31:2181,10.172.30.32:2181,10.172.30.33:2181");
        prop.put("serializer.class",StringEncoder.class.getName());

        prop.put("metadata.broker.list","10.172.30.21:9092,10.172.30.21:9093");

        return new Producer(new ProducerConfig(prop));
    }

    public static void main(String[] args) {
        new ProducerDemo("mydemo1").start();
    }

}









