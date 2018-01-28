package com.qbao.aisr.stuff.kafka.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class ConsumerDemo extends Thread {

    private String topic;

    public ConsumerDemo(String topic){
        this.topic = topic;
    }

    public void run(){
        ConsumerConnector consumer = createConsumer();

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(this.topic, 1);

        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);

        KafkaStream<byte[], byte[]> stream = messageStreams.get(this.topic).get(0);

        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while(iterator.hasNext()){
            String message = new String(iterator.next().message());

            System.out.println("recieve data：" + message);
        }
    }

    private ConsumerConnector createConsumer() {
        Properties prop = new Properties();

        prop.put("zookeeper.connect", "10.172.30.31:2181,10.172.30.32:2181,10.172.30.33:2181");

        prop.put("group.id", "group1");

        return Consumer.createJavaConsumerConnector(new ConsumerConfig(prop));
    }

    public static void main(String[] args) {
        new ConsumerDemo("mydemo1").start();
    }

}
