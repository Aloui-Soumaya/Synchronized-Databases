package Synchronized_Database.Branch_offices1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UpdateBO1 {
    private int bo_num;

    public UpdateBO1() {
        this.url = "jdbc:mysql://localhost:3306/BO1";
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
