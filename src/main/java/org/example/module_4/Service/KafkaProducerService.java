package org.example.module_4.Service;


import org.example.module_4.DTO.UserForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private KafkaTemplate<Object, String> kafkaTemplate;
    @Value("${kafka.topic}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<Object, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(String operation, UserForm user) {

        String message = String.format("{\"operation\": \"%s\", \"user\": \"%s\"}", operation, user.getEmail());
        System.out.println(message);
        kafkaTemplate.send(topic, message);
    }

}
