package com.paypal.ewallet.user.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

     /*
    outside the scope of application
    * zookeeper running
    * bootstrap server
    * created a topic for user
    * */

    /***
     * For my application
     * 1. Configure a producer.
     * 2. Configure a template via which my code can communicate to Kafka
     * 3. Send kafka event.
     * */

@Bean
    public Map<String, Object> kafkaProducerConfig(){//This method returns a Map<String, Object> containing the
        // configuration settings for the Kafka producer.
    Map<String, Object> config= new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);//strings will be serialized  using
    // the StringSerializer which is a class provided with the Kafka clients.in Kafka when you use a producer,you pass
    // in some information and at first there will be a string and that will be serialized into bytes by this
    // key.serializer and the value.serializer before being sent to Apache Kafka.
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return config;
}


@Bean
    public ProducerFactory<String,String> producerFactory(){//This method creates a ProducerFactory bean.
    // The ProducerFactory is responsible for creating Kafka Producer instances.The DefaultKafkaProducerFactory is provided
    // with the producer configuration map created in the kafkaProducerConfig method.
    //This factory will produce Kafka producers that use the configurations specified.
    return new DefaultKafkaProducerFactory<>(kafkaProducerConfig());
}

@Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
    return new KafkaTemplate<>(producerFactory());
//KafkaTemplate is constructed using the producerFactory bean.
//KafkaTemplate provides a high-level API to send messages to Kafka topics.

}
}
