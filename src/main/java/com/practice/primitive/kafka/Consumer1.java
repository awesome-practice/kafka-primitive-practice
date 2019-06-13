package com.practice.primitive.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer1 {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "dev.ufotosoft.com:9151");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "test-group");

        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("devglan-test");
        kafkaConsumer.subscribe(topics);
        try {
            while (true) {
                ConsumerRecords records = kafkaConsumer.poll(Duration.ofSeconds(1));
                for (Object obj : records) {
                    ConsumerRecord record = (ConsumerRecord) obj;
                    System.out.println(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(), record.partition(), record.value()));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }
    }
}