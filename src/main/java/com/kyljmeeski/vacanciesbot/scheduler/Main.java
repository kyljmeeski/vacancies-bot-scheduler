/*
* The MIT License (MIT)
*
* Copyright (c) 2024 Amir Syrgabaev
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
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
