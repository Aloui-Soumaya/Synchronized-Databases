package SynchronizedDatabase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class HeadOfficeJob {
    public final static String QUEUE_NAME1="product_queue1";
    public final static String QUEUE_NAME2="product_queue2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Insert insert = new Insert();
        JFrame tableFrame = new JFrame();
        tableFrame.setVisible(true);
        tableFrame.setTitle("Head Office");
        Infos infos = new Infos();
        tableFrame.add(infos.getScrollPane());
        tableFrame.setSize(900,500);
        tableFrame.setLocation(500,250);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel1 = connection.createChannel();
        Channel channel2 = connection.createChannel();
        channel1.queueDeclare(QUEUE_NAME1 ,false,false,false,null);
        channel2.queueDeclare(QUEUE_NAME1 ,false,false,false,null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody(),"UTF-8");
            List<Product> productList = deserialize(receivedMessage);
            System.out.println("mesg here "+productList);
            try {
                insert.insert(productList);
                infos.fillTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        };


        channel1.basicConsume(QUEUE_NAME1,true,deliverCallback,consumerTag -> {
            System.out.println("ERROR");
        });
        channel2.basicConsume(QUEUE_NAME2 ,true,deliverCallback,consumerTag -> {
            System.out.println("ERROR");
        });
    }

    public static List<Product> deserialize(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(message, new TypeReference<List<Product>>(){});

    }
}
