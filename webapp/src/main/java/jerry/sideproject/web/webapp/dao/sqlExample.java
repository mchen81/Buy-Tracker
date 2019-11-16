package jerry.sideproject.web.webapp.dao;

import jerry.sideproject.web.webapp.bean.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class sqlExample {

    public static void main(String args[]) {
        //Creating the connection
        String url = "jdbc:mysql://localhost:3306/side_project";
        String user = "root";
        String pass = "";

        Connection con = null;

        Item[] items = new Item[5];
        items[0] = new Item(1, "Apple", 6.8d);
        items[1] = new Item(2, "Orange", 5.3d);
        items[2] = new Item(3, "Fruit", 13.2d);
        items[3] = new Item(4, "Socks", 4.6d);
        items[4] = new Item(5, "Shoes", 3.9d);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            String insertSql = "{CALL insertItem(?,?)}";
//            CallableStatement stmt = con.prepareCall(insertSql);
//            for(Item i : items){
//                stmt.setString("i_item_name", i.getName());
//                stmt.setDouble("i_item_price", i.getPrice());
//                stmt.addBatch();
//            }
//            stmt.executeBatch();

            String querySql = "{Call getItems()}";
            CallableStatement callableStatement = con.prepareCall((querySql));
            ResultSet resultSet = callableStatement.executeQuery();

            List<Item> items2 = new ArrayList<>();
            while (resultSet.next()) {
                items2.add(
                        new Item(resultSet.getInt("ID"),
                                resultSet.getString("ITEM_NAME"),
                                resultSet.getDouble("ITEM_PRICE")));
            }
            System.out.println(items2);

            //int m = stmt.executeUpdate(insert);
//            if (m == 1)
//                System.out.println("inserted successfully : " + insert);
//            else
//                System.out.println("insertion failed");

            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
