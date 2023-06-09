package SynchronizedDatabase;

import java.sql.*;
import java.util.List;

public class Insert {
    public String url = "jdbc:mysql://localhost:3306/ho";
    private Connection connection1,connection2 = null;

    public String user="root";
    public String password = "";
    public String query = "INSERT INTO product_sale(id,date, region, product, qty, cost, amt, tax, total, bo_num) values(?,?,?,?,?,?,?,?,?,?)";

    public void insert(List<Product> productList) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = connection.prepareStatement(query)
        ){
            for(int i=0; i<productList.size(); i++) {
                Product p = productList.get(i);
                if(p.getMethod().equals("supprimer")) {
                    System.out.println("******** supp *****");
                    String query2 = "DELETE FROM product_sale WHERE id = ?";
                    connection1 = DriverManager.getConnection(
                            url, user, password);
                    int idProduct = p.getId();
                    PreparedStatement preparedStmt = connection1.prepareStatement(query2);
                    preparedStmt.setInt(1, idProduct);
                    preparedStmt.execute();
                    System.out.println("succes");

                }
                if(p.getMethod().equals("ajout")){
                    pst.setInt(1,p.getId());
                    pst.setDate(2, new Date(p.getDate().getTime()));
                    pst.setString(3, p.getRegion());
                    pst.setString(4, p.getProduct());
                    pst.setInt(5, p.getQty());
                    pst.setFloat(6, p.getCost());
                    pst.setDouble(7, p.getAmt());
                    pst.setFloat(8, p.getTax());
                    pst.setDouble(9, p.getTotal());
                    pst.setInt(10, p.getBo_num());
                    pst.executeUpdate();
                }
                if (p.getMethod().equals("modifier")){
                    String url ="jdbc:mysql://localhost:3306/ho";
                    String user="root";
                    String password = "";
                    connection1 = DriverManager.getConnection(
                            url, user, password);
                    String query = "UPDATE product_sale SET date = ?, region = ? ,product = ? ,qty = ?  , cost = ?,amt = ?,tax = ?,total = ? , bo_num = ? WHERE id = ?";
                    PreparedStatement preparedStmt = connection1.prepareStatement(query);
                    preparedStmt.setDate(1, new Date(p.getDate().getTime()));
                    preparedStmt.setString(2,  p.getRegion());
                    preparedStmt.setString(3, p.getProduct());
                    preparedStmt.setInt(4, p.getQty());
                    preparedStmt.setFloat(5, p.getCost());
                    preparedStmt.setDouble(6, p.getAmt());
                    preparedStmt.setFloat(7,  p.getTax());
                    preparedStmt.setDouble(8, p.getTotal());
                    preparedStmt.setInt(9, p.getBo_num());
                    preparedStmt.setDouble(10, p.getId());
                    preparedStmt.execute();               }

            }
        }
    }
}
