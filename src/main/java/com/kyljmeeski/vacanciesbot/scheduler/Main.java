package com.kyljmeeski.vacanciesbot.scheduler;

import com.rabbitmq.client.ConnectionFactory;

public class Main {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("localhost");
    }

}
