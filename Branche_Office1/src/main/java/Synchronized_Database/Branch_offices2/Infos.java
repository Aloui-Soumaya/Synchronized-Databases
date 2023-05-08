package Synchronized_Database.Branch_offices2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;

public class Infos {
    final Object[] column = {"Date","Id","Region","Product","Quantity","Cost","AMT","Tax","Total"};
    private JScrollPane scrollPane;
    private JTable dataTable;
    DefaultTableModel dtm;

    private Connection connection = null;
    private Statement statement = null;
    int i;
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
        RetrieveFromBO1 retrieveFromBO1 = new RetrieveFromBO1(true);
        List<Product> productEntityList = retrieveFromBO1.retrieve("ajout");
        for (Product p : productEntityList){
            dtm.addRow(new Object[]{p.getDate().toString(),
                    p.getId(),
                    p.getRegion(),
                    p.getProduct(),
                    Integer.toString(p.getQty()),
                    Float.toString(p.getCost()),
                    Double.toString(p.getAmt()),
                    Float.toString(p.getTax()),
                    Double.toString(p.getTotal()),
            });
        }

    }
}
