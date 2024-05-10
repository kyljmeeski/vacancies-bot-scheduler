package com.kyljmeeski.vacanciesbot.scheduler;

import com.kyljmeeski.plainscheduler.*;
import com.kyljmeeski.rabbitmqwrapper.*;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("localhost");

        Exchanges exchanges = new Exchanges(factory);
        Queues queues = new Queues(factory);

        try {
            RabbitExchange exchange = exchanges.declare("vacancies", "direct");
            RabbitQueue queue = queues.declare(
                    "vacancy-import-tasks", false, false, false, null
            ).bind(exchange, "import-tasks");

            Producer producer = new PlainProducer(factory, exchange, "import-tasks");

            Task task = new PlainTask(() -> {
                try {
                    producer.produce("{\"pages\": 1}");
                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }, TaskFrequency.everySeconds(2));

            Scheduler scheduler = new PlainScheduler();
            scheduler.schedule(task);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException("Check RabbitMQ.");
        }
    }

}
