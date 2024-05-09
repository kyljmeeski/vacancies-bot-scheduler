package com.kyljmeeski.vacanciesbot.scheduler;

import com.kyljmeeski.rabbitmqwrapper.Exchanges;
import com.kyljmeeski.rabbitmqwrapper.Queues;
import com.rabbitmq.client.ConnectionFactory;

public class Main {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("localhost");

        Exchanges exchanges = new Exchanges(factory);
        Queues queues = new Queues(factory);
    }

}
