package Synchronized_Database.Branch_offices1;

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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

public class Branche_OfficeJob1 {
    public final static String QUEUE_NAME="product_sale_queue1";
    public static RetrieveFromBO1 retrieveFromBO1;
    public static UpdateBO1 updateBO1;

    public static void main(String[] args) throws IOException, SQLException {
        JFrame insertionFrame = new JFrame();
        insertionFrame.setVisible(true);
        insertionFrame.setTitle("Branch Office " + 1);
        Infos infos = new Infos();
        FormulaireAjout formulaireAjout = new FormulaireAjout(infos);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                formulaireAjout, infos.getScrollPane());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);

        insertionFrame.add(splitPane);
        insertionFrame.setSize(900,750);
        insertionFrame.setLocation(500,250);

        //Instancier son DAO
        retrieveFromBO1 = new RetrieveFromBO1( false);
        updateBO1 = new UpdateBO1();

        //Preparation necessaire pour le sender de RabbitMQ
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        //Definir le job
        TimerTask task = new TimerTask() {
            public void run(){
                try {
                    List<Product> productEntityList = retrieveFromBO1.retrieve();
                    System.out.println("+++++"+productEntityList);
                    String message = serialize(productEntityList);
                    System.out.println("here :"+message);
                    try (Connection connection = connectionFactory.newConnection()) {
                        Channel channel = connection.createChannel();
                        channel.queueDeclare(QUEUE_NAME  , false, false, false, null);
                        channel.basicPublish("", QUEUE_NAME  , null, message.getBytes());
                        System.out.println(" [x] sent '" + message + " '" + LocalDateTime.now().toString());
                        //Mise en TRUE de l'attribut sent dans la table de la base de données
                        updateBO1.update(productEntityList);
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e){ }
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 60*1000L;
        timer.schedule(task,0, delay);

    }

    public static String serialize(List<Product> productEntityList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productEntityList);
    }
}
