package com.teamrocket.control;

import com.teamrocket.dto.PaymentRequest;
import com.teamrocket.dto.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("pay")
public class PaymentController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private String paymentFailedTopic = "paymentFailed";
    private String paymentSucceededTopic = "paymentSucceeded";

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("")
    public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest request) {
        return handlePayment(request);
    }

    private ResponseEntity<PaymentResponse> handlePayment(PaymentRequest request) {
        Date now = new Date();
        logger.info("Received new {}", now);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        PaymentResponse response = new PaymentResponse("Payment completed", request.getOrderId(), request.getAmount(), HttpStatus.OK, now);
        response.setAmount(request.getAmount());
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);
        if (randomNum == 5) {
            response.setMessage("Payment failed");
            response.setStatus(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        logger.info("{} - id:  ", response.getMessage(), response.getPaymentId());
        emitPaymentEvent(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    private void emitPaymentEvent(PaymentResponse response) {
        if (response.getStatus() == HttpStatus.UNAUTHORIZED) {
            kafkaTemplate.send("paymentFailed", response);

        } else {
            kafkaTemplate.send("paymentSucceeded", response);

        }
        // TODO acknowledge event
        logger.info("Payment event emitted", new Date().getTime());

    }

}
