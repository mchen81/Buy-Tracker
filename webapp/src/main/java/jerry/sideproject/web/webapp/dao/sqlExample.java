package jerry.sideproject.web.webapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class sqlExample {

    public static void main(String args[]) {
        //Creating the connection
        String url = "jdbc:mysql://localhost:3306/side_project";
        String user = "root";
        String pass = "";

        //Entering the data
        Scanner k = new Scanner(System.in);
        System.out.println("enter id");
        int id = k.nextInt();
        System.out.println("enter name");
        String name = k.next();
        System.out.println("enter password");
        String password = k.next();
        System.out.println("enter last name");
        String lastname = k.next();

        //Inserting data using SQL query
        String sql = String.format("insert into user_info values('%s','%s','%s','%s')", id, name, password, lastname);
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver");

            //Reference to connection interface
            con = DriverManager.getConnection(url, user, pass);

            Statement st = con.createStatement();
            int m = st.executeUpdate(sql);
            if (m == 1)
                System.out.println("inserted successfully : " + sql);
            else
                System.out.println("insertion failed");
            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
