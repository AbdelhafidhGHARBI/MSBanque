package tn.esprit.msbanque.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tn.esprit.msbanque.dto.CompteDto;

@Slf4j
@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "compte-events", groupId = "compte-group")
    public void listen(@Header("kafka_receivedMessageKey") String key,
                       @Payload CompteDto compteDto) {
        log.info("Received event [{}] with payload: {}", key, compteDto);
    }
}
