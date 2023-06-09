package SynchronizedDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;

public class Infos {
    final Object[] column = {"Date","Id","Region","Product","Quantity","Cost","AMT","Tax","Total","BO"};
    private JScrollPane scrollPane;
    private JTable dataTable;
    DefaultTableModel dtm;

    private Connection connection = null;
    private Statement statement = null;

    public Infos(){
        Object[][] data = {};
        this.dtm = new DefaultTableModel(data, this.column);
        this.dataTable =new JTable(dtm);
        this.dataTable.setBounds(30,40,200,300);
        this.scrollPane = new JScrollPane(this.dataTable);
        try {
            this.fillTable();
        } catch (SQLException sqlException){

        }

    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void fillTable() throws SQLException {
        dtm.setRowCount(0);
        RetrieveFromBO1_BO2 retrieveFromBO1BO2 = new RetrieveFromBO1_BO2();
        List<Product> productList = retrieveFromBO1BO2.retrieve();
        for (Product p : productList){
            dtm.addRow(new Object[]{p.getDate().toString(),
                    p.getId(),
                    p.getRegion(),
                    p.getProduct(),
                    Integer.toString(p.getQty()),
                    Float.toString(p.getCost()),
                    Double.toString(p.getAmt()),
                    Float.toString(p.getTax()),
                    Double.toString(p.getTotal()),
                    Integer.toString(p.getBo_num()),
            });
        }

    }
}
