package com.teamrocket.control;

import com.teamrocket.dto.PaymentRequest;
import com.teamrocket.dto.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("pay")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("")
    public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest request) {
        return handlePayment(request);
    }

    private ResponseEntity<PaymentResponse> handlePayment(PaymentRequest request) {
        logger.info("Start time: {}", new Date().getTime());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        logger.info("After Sleep time: {}", new Date().getTime());

        PaymentResponse response = new PaymentResponse("Payment completed", request.getOrderId(), request.getAmount(), HttpStatus.OK);
        response.setAmount(request.getAmount());
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);
        if (randomNum == 5) {
            response.setMessage("Payment Failed");
            response.setStatus(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        logger.info("End time: {}", new Date().getTime());

        return ResponseEntity.status(HttpStatus.OK).body(response);


    }


}
