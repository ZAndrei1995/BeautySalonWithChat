package dataBase;

import java.sql.*;

public class DataBase {

    public boolean insertInDataBase ( String uName, String userPassword ) {
        boolean isInserted = false ;

        final String URL = "jdbc:postgresql://idc.cluster-custom-cjcsijnttbb2.eu-central-1.rds.amazonaws.com:5432/AZadic";
        final String USERNAME = "ftuser" ;
        final String PASSWORD = "********" ;
        try {
            Connection myConnection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement preparedStatement = myConnection.prepareStatement("INSERT INTO users(username,userpassword) VALUES (?,?)") ;
            preparedStatement.setString(1,uName);
            preparedStatement.setString(2,userPassword);
            int updateValuesOfDB = preparedStatement.executeUpdate() ;
            System.out.println(updateValuesOfDB);
            preparedStatement.close();
            myConnection.close();
        }catch(SQLException e ) {
            System.out.println("You already have one in database!");
        }

        return isInserted ;
    }

    public boolean isInDataBase(String uName, String uPass) {
        boolean isInDataBase = false ;

        /*Connect to database... */
        final String URL = "jdbc:postgresql://idc.cluster-custom-cjcsijnttbb2.eu-central-1.rds.amazonaws.com:5432/AZadic";
        final String USERNAME = "ftuser" ;
        final String PASSWORD = "*******" ;
        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /* Create a query */
            Statement statement = myConn.createStatement();
            /* Execute the query */
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                String numeUser = resultSet.getString("username");
                String passUser = resultSet.getString("userpassword");
                if ( numeUser.contains(uName.trim()) && passUser.contains(uPass.trim())) {
                    isInDataBase = true ;
                }
            }

            resultSet.close();
            statement.close();
            myConn.close();
        }catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return isInDataBase ;
    }

}
