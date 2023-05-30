package Synchronized_Database.Branch_offices2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Branche_OfficeJob2 {
    public final static String QUEUE_NAME="product_queue2";
    public static RetrieveFromBO2 retrieveFromBO2;
    public static UpdateBO2 updateBO2;

    public static void main(String[] args) throws IOException, SQLException {
        JFrame insertionFrame = new JFrame();
        insertionFrame.setVisible(true);
        insertionFrame.setTitle("Branch Office " + 2);
        Infos infos = new Infos();
        FormulaireAjout formulaireAjout = new FormulaireAjout(infos);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                formulaireAjout, infos.getScrollPane());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);

        insertionFrame.add(splitPane);
        insertionFrame.setSize(900,750);
        insertionFrame.setLocation(500,250);

        retrieveFromBO2 = new RetrieveFromBO2( false);
        updateBO2 = new UpdateBO2();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        List<Product> productEntityList = retrieveFromBO2.retrieve("ajout");
        System.out.println("+++++"+productEntityList);
        String message = serialize(productEntityList);
        System.out.println("here :"+message);
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            updateBO2.update(productEntityList);

       } catch (Exception e){ }

    }

    public static String serialize(List<Product> productEntityList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productEntityList);
    }
}
