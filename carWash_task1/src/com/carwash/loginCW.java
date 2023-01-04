package com.carwash;
import java.sql.*;
import java.util.Scanner;
import java.sql.ResultSet;
import java.time.*;
import java.util.Vector;


public class loginCW {
//    static void checkAvailabilityAndBooking(String name) {
//        Scanner sc = new Scanner(System.in);
//        PreparedStatement insert, insert2;
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash", "root", "")) {
//            insert = connection.prepareStatement("SELECT placeName, date , COUNT(date) from bookings GROUP BY placeName, date HAVING ((COUNT(placeName) < 6) AND (COUNT(date) < 6 ) AND (date=?) AND placeName=?);");
////            stack
//            insert.setDate(1, date);
//            insert.setString(2, placename);
//            ResultSet rs = insert.executeQuery();
//            if (rs.next()) {
//                System.out.println("Booking Available press 0 to continue");
//                int cont = sc.nextInt();
//                if (cont == 0) {
//                    insert2 = connection.prepareStatement("INSERT INTO bookings (id, name, placeName, service, status, date) VALUES (NULL, ?,?,?,?,?)");
//                    insert2.setString(1, name);
//                    insert2.setString(2, placename);
//                    insert2.setString(3, service);
//                    insert2.setString(4, "Pending");
//                    insert2.setDate(5, date);
//                    insert2.executeUpdate();
//                    System.out.println("Slot Booked Successfully!");
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    static void searchPlaces(){
        PreparedStatement insert;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash","root", "")) {
            Scanner sc = new Scanner(System.in);
            insert = connection.prepareStatement("select DISTINCT placeName from places");
            ResultSet rs = insert.executeQuery();
            System.out.println("Available Cities Are:");
            int n=1;
            while (rs.next()){
                System.out.println((n++) +" "+rs.getString("placeName"));
            }
            System.out.println("Enter city Name to check availability:");

            //   check avai  ....

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

        static void addPlacesAndServices(){
            PreparedStatement insert;
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash","root", "")) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter city name");
                String city = sc.next();
                System.out.println("Enter service wash/basic  (0 to wash and 1 to basic");
                int s= sc.nextInt();
                String service;
                if(s ==0)
                    service = "wash";
                else
                    service = "basic";
                insert = connection.prepareStatement("insert into places(placeName,services) values (?,?)");
                insert.setString(2,city);
                insert.setString(3,service);
                insert.executeUpdate();

                System.out.println("Data updated successfully.");
            }catch (SQLException e) {
        throw new RuntimeException(e);
    }
        }
        static void viewBookings(){
            PreparedStatement insert;
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash","root", "")) {
                insert = connection.prepareStatement("select * from bookings");
                ResultSet rs = insert.executeQuery();
                while (rs.next()){
                    System.out.println(rs.getInt("id")+"\t"+rs.getString("name")+"\t"+rs.getString("placeName")+"\t"+rs.getString("service")+"\t"+rs.getString("date")+"\t"+rs.getString("status"));
                }


            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    static void userdashboard(String userName){
        PreparedStatement insert;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash","root", "")) {
        int n = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome, "+userName);
        System.out.println("1. Search Places and Check Availability" );
        System.out.println("2. Get Booking Status");
        System.out.println("0. logout");
        n=sc.nextInt();
        switch (n){
            case 1 : searchPlaces(); break;
            case 2 : break;
            case 0 : break;
            default: System.out.println("Enter valid input");break;
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static void updateStatus(){
//        UPDATE `bookings` SET `status` = 'completed' WHERE `bookings`.`id` = 2
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash","root", "")) {
        String stats = null;
        PreparedStatement insert;
        viewBookings();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter id :");
        int id = sc.nextInt();
         System.out.println("1. Success \n2. Pending \n3. Rejected");
         int sts = sc.nextInt();
        if(sts==1)
            stats="Success";
        else if (sts==2)
            stats="Pending";
        else if(sts==3)
            stats="Rejected";
        else {
            System.out.println("Enter valid choice :");
            updateStatus();
//            goto here;
        }
            insert = connection.prepareStatement("UPDATE bookings SET status = ? WHERE id = ?");
            insert.setString(1,stats);
            insert.setInt(2,id);
            insert.executeUpdate();
            System.out.println("Status Updated Successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        admindashboard();
    }
    static void admindashboard(){
        PreparedStatement insert;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash","root", "")) {
        Scanner sc = new Scanner(System.in);
        int n = 0;
        System.out.println("Welcome, Admin");
        System.out.println("1. Add places and Services.");
        System.out.println("2. accept/reject Bookings.");
        System.out.println("3. View all Bookings.");
        System.out.println("4. Update Status");
        System.out.println("0. logout");
        n=sc.nextInt();
        switch (n){
            case 1 : addPlacesAndServices();break;
            case 2 : break;
            case 3 : viewBookings();break;
            case 4 : updateStatus();break;
            default: System.out.println("Enter valid input");break;
    }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static String[] signin(String email, String password){
        boolean flag = false;
        String[] arr = new String[3];
        PreparedStatement insert;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash","root", "")) {
            insert = connection.prepareStatement("select userName,email,password,userRoll from users where email= ? and password=?");
            insert.setString(1,email);
            insert.setString(2,password);
            ResultSet rs = insert.executeQuery();
            while (rs.next()) {
                String username = rs.getString("userName");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                String uRoll = rs.getString("userRoll");

                if (password.equals(userPassword) && email.equals(userEmail) ){
                    arr[0] = "true";
                    arr[1] = username;
                    arr[2] = uRoll;
                    return arr;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arr;
    }

        static void signup(String userName, String email, String password, String userRoll){

        PreparedStatement insert;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/car_wash","root", "")) {

            insert = connection.prepareStatement("insert into users(userName,email,password,userRoll) values (?,?,?,?)");
            insert.setString(1,userName);
            insert.setString(2,email);
            insert.setString(3,password);
            insert.setString(4,userRoll);
            insert.executeUpdate();

            System.out.println("Signup Successfull!");
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("login/SignUp \n(press 0 to login or press 1 to signup)");
        int ip = sc.nextInt();
        if(ip==0){
            String email,password;
            System.out.println("Enter email:");
            email = sc.next();
            System.out.println("Enter password:");
            password = sc.next();
            String[] flag = signin(email, password);
            if (flag[0]=="true") {
                System.out.println("login successfull!");

                if(flag[2].equals("admin"))
                    admindashboard();
                else
                    userdashboard(flag[1]);
            }
            else
                System.out.println("username/password incorrect or account not exist!");
        }else if(ip==1){
            String userName, email, password, userRoll;
            System.out.println("Enter username");
            userName = sc.next();
            System.out.println("Enter Email");
            email = sc.next();
            System.out.println("Enter password");
            password = sc.next();
            System.out.println("Enter userRoll");
            userRoll = sc.next();
            signup(userName,email,password,userRoll);
        }else{
            System.out.println("Enter valid input \n");
            main(null);
        }
    }
}