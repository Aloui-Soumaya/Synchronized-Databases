package Synchronized_Database.Branch_offices2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetrieveFromBO1 {
    public RetrieveFromBO1(boolean sent) {
        this.url = "jdbc:mysql://localhost:3306/BO1";
        query = "SELECT * FROM product_sale" + (sent ? "" : " where sent=FALSE");
    }
    public String url;
    public String user="root";
    public String password = "";
    public String query;
    public List<Product> retrieve(String method) throws SQLException{
        System.out.println(this.url);
        List<Product> res = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

        ) {
            while(rs.next()) {
                Product productEntity = new Product();
                productEntity.setId(rs.getInt("id"));
                productEntity.setDate(rs.getDate("date"));
                productEntity.setRegion(rs.getString("region"));
                productEntity.setProduct(rs.getString("product"));
                productEntity.setQty(rs.getInt("qty"));
                productEntity.setCost(rs.getFloat("cost"));
                productEntity.setAmt(rs.getDouble("amt"));
                productEntity.setTax(rs.getFloat("tax"));
                productEntity.setTotal(rs.getDouble("total"));
                productEntity.setBo_num(1);
                productEntity.setMethod(method);
                res.add(productEntity);
            }

            return res;
        }

    }
}
