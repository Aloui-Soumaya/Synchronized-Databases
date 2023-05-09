package Synchronized_Database.Branch_offices2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

import static Synchronized_Database.Branch_offices2.Branche_OfficeJob1.*;


public class FormulaireAjout extends JPanel{
    public final static String QUEUE_NAME="product_queue1";

    private Connection connection1,connection2 = null;
    private Statement statement = null;
    public JTextField regionTextFld;
    public JTextField productTextFld;
    public JTextField quantityTextFld;
    public JTextField costTextFld;
    public JTextField amtTextFld;
    public JTextField taxTextFld;
    public JTextField totalTextFld;
    public JTextField idFiled;
    public JTextArea textArea;
    public JButton submitBtn;
    public JButton updateBtn;
    public JButton deleteBtn;
    Infos infos;

    public FormulaireAjout(Infos infos) {
            setBackground(new Color(0xEFEFEF));
        this.infos = infos;
        setPreferredSize(new Dimension(1200, 1000));

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1000, 1000));

        JLabel regionLabel = new JLabel("Region: ");
        regionTextFld = new JTextField(15);

        JLabel productLabel = new JLabel("Product: ");
        productTextFld = new JTextField(20);

        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityTextFld = new JTextField(10);

        JLabel costLabel = new JLabel("Cost: ");
        costTextFld = new JTextField(10);

        JLabel amtLabel = new JLabel("AMT: ");
        amtTextFld = new JTextField(10);

        JLabel taxLabel = new JLabel("Tax: ");
        taxTextFld = new JTextField(10);

        JLabel totalLabel = new JLabel("Total: ");
        totalTextFld = new JTextField(10);

        submitBtn = new JButton("Ajouter");
        ButtonListenerAdd buttonListener = new ButtonListenerAdd();
        submitBtn.addActionListener(buttonListener);

        deleteBtn = new JButton("Supprimer");
        ButtonListenerDelete buttonListener2 = new ButtonListenerDelete();
        deleteBtn.addActionListener(buttonListener2);

        updateBtn = new JButton("Modifier");
        ButtonListenerUpdate buttonListener3 = new ButtonListenerUpdate();
        updateBtn.addActionListener(buttonListener3);

        JLabel info2 = new JLabel("Pour supprimer ou modifier : ");
        info2.setForeground(Color.RED);

        JLabel info1 = new JLabel("Pour Ajouter : ");
        info1.setForeground(Color.RED);

        JLabel idLabel = new JLabel("ID : ");
        idFiled = new JTextField(15);
        textArea = new JTextArea(10, 30);
        p.add(info1);
        p.add(regionLabel);
        p.add(regionTextFld);
        p.add(productLabel);
        p.add(productTextFld);
        p.add(quantityLabel);
        p.add(quantityTextFld);
        p.add(costLabel);
        p.add(costTextFld);
        p.add(amtLabel);
        p.add(amtTextFld);
        p.add(taxLabel);
        p.add(taxTextFld);
        p.add(totalLabel);
        p.add(totalTextFld);
        p.add(Box.createRigidArea(new Dimension(10, 0)));
        p.add(submitBtn);
        p.add(textArea);
        p.add(Box.createRigidArea(new Dimension(10, 0)));
        p.add(info2);
        p.add(idLabel);
        p.add(idFiled);
        p.add(Box.createRigidArea(new Dimension(10, 0)));
        p.add(updateBtn);
        p.add(Box.createRigidArea(new Dimension(10, 0)));
        p.add(deleteBtn);
        add(p, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
    }

    public class ButtonListenerDelete implements ActionListener{
        public ButtonListenerDelete(){

        }
        public void actionPerformed(ActionEvent e){
            try {
                Calendar calendar = Calendar.getInstance();
                java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
                String url ="jdbc:mysql://localhost:3306/BO1";
                String user="root";
                String password = "";
                connection1 = DriverManager.getConnection(
                        url, user, password);
                int idProduct = Integer.parseInt(idFiled.getText());
                System.out.println(idProduct);
                String query = "DELETE FROM product_sale WHERE id = ?";
                String query2 = "SELECT * FROM product_sale WHERE id = ?";
                /***************/
                connection2 = DriverManager.getConnection(
                        url, user, password);
                PreparedStatement preparedStmt2 = connection1.prepareStatement(query2);
                preparedStmt2.setInt(1, idProduct);
                ResultSet rs = preparedStmt2.executeQuery();
                java.util.List<Product> res = new ArrayList<>();
                while(rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setDate(rs.getDate("date"));
                    product.setRegion(rs.getString("region"));
                    product.setProduct(rs.getString("product"));
                    product.setQty(rs.getInt("qty"));
                    product.setCost(rs.getFloat("cost"));
                    product.setAmt(rs.getDouble("amt"));
                    product.setTax(rs.getFloat("tax"));
                    product.setTotal(rs.getDouble("total"));
                    product.setMethod("supprimer");
                    res.add(product);
                }
                String message = serialize(res);
                System.out.println("what is new to delete : "+ message);
                ConnectionFactory connectionFactory = new ConnectionFactory();
                connectionFactory.setHost("localhost");
                try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection()) {
                    Channel channel = connection.createChannel();
                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                    System.out.println(" [x] sent '" + message + " '" + LocalDateTime.now().toString());
                } catch (Exception e1){ }
                /*********************/
                PreparedStatement preparedStmt = connection1.prepareStatement(query);
                preparedStmt.setInt(1, idProduct);
                preparedStmt.execute();
                System.out.println(preparedStmt);
                connection1.close();
                idFiled.setText("");
                System.out.println("succes");
                infos.fillTable();
                System.out.println("succes");
            }
            catch (Exception exception){

            }
        }
    }
    public class ButtonListenerAdd implements ActionListener{
        public ButtonListenerAdd(){

        }
        public void actionPerformed(ActionEvent e){
            try {
                Calendar calendar = Calendar.getInstance();
                java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
                String url ="jdbc:mysql://localhost:3306/BO1";
                String user="root";
                String password = "";
                connection1 = DriverManager.getConnection(
                        url, user, password);
                String region = regionTextFld.getText();
                String product = productTextFld.getText();
                int qty = Integer.parseInt(quantityTextFld.getText());
                float cost = Float.parseFloat(costTextFld.getText());
                double amt = Double.parseDouble(amtTextFld.getText());
                float tax = Float.parseFloat(taxTextFld.getText());
                double total = Double.parseDouble(totalTextFld.getText());
                String query = " INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = connection1.prepareStatement(query);
                preparedStmt.setDate(1, startDate);
                preparedStmt.setString(2, region);
                preparedStmt.setString(3, product);
                preparedStmt.setInt(4, qty);
                preparedStmt.setFloat(5, cost);
                preparedStmt.setDouble(6, amt);
                preparedStmt.setFloat(7, tax);
                preparedStmt.setDouble(8, total);
                preparedStmt.execute();
                System.out.println(preparedStmt);
                connection1.close();
                regionTextFld.setText("");
                productTextFld.setText("");
                quantityTextFld.setText("");
                costTextFld.setText("");
                amtTextFld.setText("");
                taxTextFld.setText("");
                totalTextFld.setText("");
                System.out.println("succes");
                infos.fillTable();
                System.out.println("succes");
                ConnectionFactory connectionFactory = new ConnectionFactory();
                connectionFactory.setHost("localhost");
                java.util.List<Product> productEntityList = retrieveFromBO1.retrieve("ajout");
                String message = serialize(productEntityList);
                System.out.println("what is new : "+ message);
                try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection()) {
                    Channel channel = connection.createChannel();
                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                    System.out.println(" [x] sent '" + message + " '" + LocalDateTime.now().toString());
                    updateBO1.update(productEntityList);
                } catch (Exception e1){ }
            } catch (Exception exception){

            }
        }
    }
    public class ButtonListenerUpdate implements ActionListener{
        public ButtonListenerUpdate(){

        }
        public void actionPerformed(ActionEvent e){
            try {
                Calendar calendar = Calendar.getInstance();
                java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
                String url ="jdbc:mysql://localhost:3306/BO1";
                String user="root";
                String password = "";
                connection1 = DriverManager.getConnection(
                        url, user, password);
                int idProduct = Integer.parseInt(idFiled.getText());
                String region = regionTextFld.getText();
                String product = productTextFld.getText();
                int qty = Integer.parseInt(quantityTextFld.getText());
                float cost = Float.parseFloat(costTextFld.getText());
                double amt = Double.parseDouble(amtTextFld.getText());
                float tax = Float.parseFloat(taxTextFld.getText());
                double total = Double.parseDouble(totalTextFld.getText());
                String query = "UPDATE product_sale SET date = ?, region = ? ,product = ? ,qty = ?  , cost = ?,amt = ?,tax = ?,total = ?  WHERE id = ?";
                PreparedStatement preparedStmt = connection1.prepareStatement(query);
                preparedStmt.setDate(1, startDate);
                preparedStmt.setString(2, region);
                preparedStmt.setString(3, product);
                preparedStmt.setInt(4, qty);
                preparedStmt.setFloat(5, cost);
                preparedStmt.setDouble(6, amt);
                preparedStmt.setFloat(7, tax);
                preparedStmt.setDouble(8, total);
                preparedStmt.setDouble(9, idProduct);
                preparedStmt.execute();
                System.out.println(preparedStmt);
                connection1.close();
                regionTextFld.setText("");
                productTextFld.setText("");
                quantityTextFld.setText("");
                costTextFld.setText("");
                amtTextFld.setText("");
                taxTextFld.setText("");
                totalTextFld.setText("");
                idFiled.setText("");
                infos.fillTable();
                System.out.println("succes");
                /********  Rabbit mq ********/
                Product product1 = new Product();
                product1.setId(idProduct);
                product1.setDate(startDate);
                product1.setRegion(region);
                product1.setProduct(product);
                product1.setQty(qty);
                product1.setCost(cost);
                product1.setAmt(amt);
                product1.setTax(tax);
                product1.setTotal(total);
                product1.setBo_num(1);
                product1.setMethod("modifier");
                java.util.List<Product> res = new ArrayList<>();
                res.add(product1);
                String message = serialize(res);
                System.out.println("what is new to update: "+ message);
                ConnectionFactory connectionFactory = new ConnectionFactory();
                connectionFactory.setHost("localhost");
                try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection()) {
                Channel channel = connection.createChannel();
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] sent '" + message + " '" + LocalDateTime.now().toString());
                } catch (Exception e1){ }
                /********  Rabbit mq ********/
            } catch (Exception exception){

            }
        }
    }


}
