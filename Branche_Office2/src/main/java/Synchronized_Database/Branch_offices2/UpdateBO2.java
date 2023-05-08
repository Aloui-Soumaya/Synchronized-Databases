package Synchronized_Database.Branch_offices2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UpdateBO2 {
    private int bo_num;

    public UpdateBO2() {
        this.url = "jdbc:mysql://localhost:3306/BO2";
    }
    //Coordonnées de la base
    public String url;
    public String user="root";
    public String password = "";
    //Requete pour la mise en TRUE de l'attribut sent
    public String query = "UPDATE product_sale set sent = TRUE where id = ?";
    //Methode pour la mise en TRUE de l'attribut sent
    public void update(List<Product> productEntityList) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = connection.prepareStatement(query)
        ){
            for(int i = 0; i< productEntityList.size(); i++) {
                pst.setInt(1, productEntityList.get(i).getId());
                pst.executeUpdate();
            }
        }
    }
}
