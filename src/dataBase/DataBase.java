package dataBase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DataBase {

    public boolean insertInDataBase ( String uName, String userPassword, String uEmail, String originalName, String originalSurname) {
        boolean isInserted = false ;

        final String URL = "jdbc:postgresql://localhost:5432/andrewdb";
        final String USERNAME = "postgres" ;
        final String PASSWORD = "Hawkeyestar123" ;
        try {
            Connection myConnection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement preparedStatement = myConnection.prepareStatement("INSERT INTO users(username,userpassword,email,name,surname) VALUES (?,?,?,?,?)") ;
            preparedStatement.setString(1,uName);
            preparedStatement.setString(2,userPassword);
            preparedStatement.setString(3,uEmail);
            preparedStatement.setString(4,originalName);
            preparedStatement.setString(5,originalSurname);

            int updateValuesOfDB = preparedStatement.executeUpdate() ;
            System.out.println(updateValuesOfDB);
            preparedStatement.close();
            myConnection.close();
        }catch(SQLException e ) {
            System.out.println(e.getMessage());
        }

        return isInserted ;
    }

    public boolean isInDataBase(String uName, String uPass) {
        boolean isInDataBase = false ;

        /*Connect to database... */
        final String URL = "jdbc:postgresql://localhost:5432/andrewdb";
        final String USERNAME = "postgres" ;
        final String PASSWORD = "Hawkeyestar123" ;
        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /* Create a query */
            Statement statement = myConn.createStatement();
            /* Execute the query */
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                if ( resultSet.getString("username").contains(uName.trim()) && resultSet.getString("userpassword").contains(uPass.trim())) {
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

    public boolean insertScheduleDateAndHour (String name, String surName, LocalDate localDate, LocalDateTime localDateTime) {

        boolean isInserted = false ;

        final String URL = "jdbc:postgresql://localhost:5432/andrewdb";
        final String USERNAME = "postgres" ;
        final String PASSWORD = "Hawkeyestar123" ;
        try {
            Connection myConnection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement preparedStatement = myConnection.prepareStatement("INSERT INTO programaresalon(nume,prenume,dataprogramare,ora_programare) VALUES (?,?,?,?)") ;
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,surName);
            preparedStatement.setDate(3, Date.valueOf(localDate));
            preparedStatement.setObject(4,localDateTime);


            int updateValuesOfDB = preparedStatement.executeUpdate() ;
            System.out.println(updateValuesOfDB);
            preparedStatement.close();
            myConnection.close();
        }catch(SQLException e ) {
            System.out.println(e.getMessage());
        }

        return isInserted ;

    }

    public boolean searchInScheduleDateAndHour( LocalDate lDate, LocalDateTime lDTime ) {

        boolean approval = false ;

        final String URL = "jdbc:postgresql://localhost:5432/andrewdb";
        final String USERNAME = "postgres" ;
        final String PASSWORD = "Hawkeyestar123" ;

        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /* Create a query */
            Statement statement = myConn.createStatement();
            /* Execute the query */
            ResultSet resultSet = statement.executeQuery("SELECT * FROM programaresalon ORDER BY id DESC LIMIT 1");
            while ( resultSet.next()) {
                LocalDate localDate = resultSet.getDate("dataprogramare").toLocalDate() ;
                LocalTime localTime = resultSet.getTime("ora_programare").toLocalTime() ;

                if ( lDate.equals(localDate)) {
                    if ( localTime.isBefore(LocalTime.from(lDTime))) {
                        approval = true ;
                    }
                }else{
                    approval = false ;
                }

            }
        }catch (SQLException e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return approval ;
    }

    public void displayCheckBooking(String name, String surname ) {
        /*Connect to database... */
        final String URL = "jdbc:postgresql://localhost:5432/andrewdb";
        final String USERNAME = "postgres" ;
        final String PASSWORD = "Hawkeyestar123" ;
        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /* Create a query */
            Statement statement = myConn.createStatement();
            /* Execute the query */
            ResultSet resultSet = statement.executeQuery("SELECT * FROM programaresalon");

            while (resultSet.next()) {
                if ( resultSet.getString("nume").contains(name.trim()) && resultSet.getString("prenume").contains(surname.trim())) {
                    System.out.println("Your booking is at: " + resultSet.getDate("dataprogramare") +  " at " + resultSet.getTime("ora_programare") );
                    System.out.println();
                }
            }

            resultSet.close();
            statement.close();
            myConn.close();
        }catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

}
