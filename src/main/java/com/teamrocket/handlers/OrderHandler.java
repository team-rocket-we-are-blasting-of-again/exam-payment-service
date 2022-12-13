package com.teamrocket.handlers;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
@ExternalTaskSubscription(topicName = "handlePayment")
public class OrderHandler implements ExternalTaskHandler {


    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        System.out.println("handlePayment fired!");
        boolean paymentSuccess = true;
        Map<String, Object> allVariables = externalTask.getAllVariables();
        allVariables.put("payment_accepted", paymentSuccess);
        externalTaskService.complete(externalTask, allVariables);
    }
}
