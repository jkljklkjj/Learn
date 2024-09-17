package com.example.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class Producer {
    // 队列名
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) {
        // 创建连接工厂
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost", 5672);

        // 创建admin
        RabbitAdmin admin = new RabbitAdmin(factory);

        // 声明队列
        admin.declareQueue(new Queue(QUEUE_NAME));

        // 创建连接
        RabbitTemplate template = new RabbitTemplate(factory);

        // 发送消息
        int count = 0;
        int maxMessages = 10; // 设置要发送的最大消息数
        while (count < maxMessages) {
            template.convertAndSend(QUEUE_NAME, "Hello, World!");
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 关闭连接
        factory.destroy();
        System.out.println("Message sent successfully.");
    }
}