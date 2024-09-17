package com.example.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class Consumer {
    // 绑定的队列名
    private static final String QUEUE_NAME = "queue";

    public static void main(String args[]) {
        // 创建连接工厂
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost", 5672);

        // 创建admin
        RabbitAdmin admin = new RabbitAdmin(factory);

        // 声明队列
        admin.declareQueue(new Queue(QUEUE_NAME));

        // 创建连接
        RabbitTemplate template = new RabbitTemplate(factory);

        // 消费消息
        while (true) {
            String message = (String) template.receiveAndConvert(QUEUE_NAME);
            if (message != null) {
                System.out.println("Received: " + message);
            }
        }
    }
}
