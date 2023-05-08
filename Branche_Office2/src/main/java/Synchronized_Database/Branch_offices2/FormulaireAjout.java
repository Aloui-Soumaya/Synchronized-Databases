package Synchronized_Database.Branch_offices2;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;

import javax.swing.*;


public class FormulaireAjout extends JPanel{
    private Connection connection1 = null;
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
        info2.setForeground(Color.green);

        JLabel info1 = new JLabel("Pour Ajouter : ");
        info1.setForeground(Color.green);

        JLabel idLabel = new JLabel("ID : ");
        idFiled = new JTextField(15);
        textArea = new JTextArea(10, 30);

        //add current date
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

                String url ="jdbc:mysql://localhost:3306/BO2";
                String user="root";
                String password = "";
                connection1 = DriverManager.getConnection(
                        url, user, password);


                int idProduct = Integer.parseInt(idFiled.getText());
                System.out.println(idProduct);

                // the mysql insert statement
                String query = "DELETE FROM product_sale WHERE id = ?";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = connection1.prepareStatement(query);
                preparedStmt.setInt(1, idProduct);
                // execute the preparedstatement
                preparedStmt.execute();
                System.out.println(preparedStmt);
                connection1.close();
                idFiled.setText("");
                System.out.println("succes");
                infos.fillTable();
                System.out.println("succes");

            } catch (Exception exception){

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

                String url ="jdbc:mysql://localhost:3306/BO2";
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


                // the mysql insert statement
                String query = " INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = connection1.prepareStatement(query);
                preparedStmt.setDate(1, startDate);
                preparedStmt.setString(2, region);
                preparedStmt.setString(3, product);
                preparedStmt.setInt(4, qty);
                preparedStmt.setFloat(5, cost);
                preparedStmt.setDouble(6, amt);
                preparedStmt.setFloat(7, tax);
                preparedStmt.setDouble(8, total);

                // execute the preparedstatement
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

                String url ="jdbc:mysql://localhost:3306/BO2";
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


                // the mysql insert statement
                String query = "UPDATE product_sale SET date = ?, region = ? ,product = ? ,qty = ?  , cost = ?,amt = ?,tax = ?,total = ?  WHERE id = ?";


                // create the mysql insert preparedstatement
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

                // execute the preparedstatement
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
                System.out.println("succes");
                infos.fillTable();
                System.out.println("succes");

            } catch (Exception exception){

            }
        }
    }


}

