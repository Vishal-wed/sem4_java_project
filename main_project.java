import java.util.Scanner;
// import java.lang.*;
import java.sql.*;

public class main_project {

    static String username = null;
    static String password = null;
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("************************************************************************");
            System.out.println("****                        Banking System                          ****");
            System.out.println("************************************************************************");
            System.out.println();
            System.out.println("                              1 : Login");
            System.out.println("                              2 : Registration");
            System.out.println("                              3 : Exit");
            System.out.println();
            System.out.print("                             Enter your choice : ");
            int user_choice = sc.nextInt();

            switch (user_choice) {
                case 1:
                    login();
                    break;
            
                case 2:
                    regi();
                    break;

                case 3:
                    return;

                default:
                    break;
            }
        }
    }

    public static void is_login(){
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("************************************************************************");
            System.out.println("****                        Banking System                          ****");
            System.out.println("************************************************************************");
            System.out.println();

            System.out.println("                            1 : Transfer Money");
            System.out.println("                            2 : Add Monry");
            System.out.println("                            3 : User details");
            System.out.println("                            4 : Logout");
            System.out.println();
            System.out.print("                             Enter your choice : ");
            int user_choice = sc.nextInt();

            switch (user_choice) {
                case 1:
                    transfer_money();
                    break;

                case 2:
                    add_money();
                    break;

                case 3:
                    user_details();
                    break;

                case 4:
                    username = null;
                    password = null;
                    return;
            }
        }
    }

    // login function

    public static int login(){
        Scanner sc = new Scanner(System.in);

        System.out.println("************************************************************************");
        System.out.println("****                           Login form                           ****");
        System.out.println("************************************************************************");
        System.out.println();
        System.out.print("                           username : ");
        String test_username = sc.next();
        System.out.print("                           password : ");
        String test_password = sc.next();
        System.out.println();
        System.out.println("************************************************************************");

        try{
            boolean login_condition = false;
            String upl = "jdbc:mysql://localhost:3307/java_bank_system";
            String conn_username = "root";
            String conn_password = "vishal";

            Connection connection = DriverManager.getConnection(upl, conn_username, conn_password);
            System.out.println();

            Statement stmt = connection.createStatement();

            String sql = "select * from details where username = '"+test_username+"' && password = '"+test_password+"'";
            ResultSet result_sql = stmt.executeQuery(sql);

            while (result_sql.next()) {
                login_condition = true;
                int id = result_sql.getInt("id");
                username = result_sql.getString("username");
                password = result_sql.getString("password");
                is_login();
            }

            if (!login_condition) {
                System.out.println("                       enter username or password is wrong");
            }
            goEnterKey();
        }catch(Exception e){ System.out.println(e);}
        return 0;
    }

    // registration functin

    public static void regi(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("************************************************************************");
        System.out.println("****                      registration form                         ****");
        System.out.println("************************************************************************");
        System.out.print("                      Full Name : ");
        String name = sc.nextLine();
        System.out.print("                  Mobile Number : ");
        String mobile_number = sc.nextLine();
        System.out.print("                         E-mail : ");
        String email = sc.nextLine();
        System.out.print("                            Age : ");
        int age = sc.nextInt();
        System.out.print("                       Username : ");
        String user_name = sc.next();
        System.out.print("                       Password : ");
        String user_password = sc.next();
        System.out.print("                            Pin : ");
        int user_pin = sc.nextInt();
        System.out.println("************************************************************************");
        
        try{
            boolean exsist_user = false;
            String url = "jdbc:mysql://localhost:3307/java_bank_system";
            String username = "root";
            String password = "vishal";
            
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("                      connect sucessfull");
            Statement stmt=connection.createStatement();

            String check_exsist_user = "select * from details where username = '"+user_name+"'";
            ResultSet resultSet = stmt.executeQuery(check_exsist_user);

            while (resultSet.next()) {
                exsist_user = true;
            }
            if (exsist_user) {
                System.out.println("                       username is already exsist");
            }
            else if (!exsist_user) {
                if (age >= 18) {
                    int len = String.valueOf(user_pin).length();
                    if (len == 4) {
                        String value = "INSERT INTO details(name, mobile_number, email, age, username, password, money, pin) values('"+name+"',"+mobile_number+",'"+email+"',"+age+",'"+user_name+"','"+user_password+"',100, "+user_pin+")";
                        int result = stmt.executeUpdate(value);
                        System.out.println("                      insert sucessfull");
                    }else{
                        System.out.println("                       length of pin is not 4");
                    }
                }
                else{
                    System.out.println("                     you are not eligible");
                }
            }
            goEnterKey();
        }catch(Exception e){ System.out.println(e);}
    }

    // Add money funtion

    public static void add_money(){
        Scanner sc = new Scanner(System.in);

        System.out.println("************************************************************************");
        System.out.println("****                           Add Money                            ****");
        System.out.println("************************************************************************");
        System.out.println();
        
        System.out.print("                                Pin : ");
        int user_pin = sc.nextInt();
        System.out.print("                             Amount : ");
        int add_amount = sc.nextInt();
        System.out.println();
        System.out.println("************************************************************************");

        try {
            if (add_amount < 0) {
                System.out.println("                       Invalid amount");
                goEnterKey();
                return;
            }
            boolean login_condition = false;
            String url = "jdbc:mysql://localhost:3307/java_bank_system";
            String conn_username = "root";
            String conn_password = "vishal";

            Connection connection = DriverManager.getConnection(url, conn_username, conn_password);
            System.out.println();

            Statement stmt = connection.createStatement();

            String check_user = "select * from details where username = '"+username+"' && password = '"+password+"'";

            ResultSet result_exsist_user = stmt.executeQuery(check_user);

            while (result_exsist_user.next()) {
                if (user_pin == result_exsist_user.getInt("pin")) {
                    login_condition = true;
                    int orignal_amount = result_exsist_user.getInt("money");
                    add_amount += orignal_amount;
                }

            }
            if (!login_condition) {
                System.out.println(                       "your Pin is wrong");
            }
            else if (login_condition) {
                String sql_add_money = "update details set money = "+add_amount+" where username = '"+username+"'";
                int result_add_money = stmt.executeUpdate(sql_add_money); 

                if (result_add_money == 1) {
                    System.out.println("                       money added successfully"); 
                } else {
                    System.out.println("                       someting went wrong");
                }
            } else {
                System.out.println("                       someting went wrong");
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        goEnterKey();
    }

    // transfer money function

    public static void transfer_money(){
        Scanner sc = new Scanner(System.in);

        System.out.println("************************************************************************");
        System.out.println("****                        Transfer Money                          ****");
        System.out.println("************************************************************************");
        System.out.println();

        System.out.print("                  Receiver username : ");
        String receiver_username = sc.next();
        System.out.print("                         Sender Pin : ");
        int sender_pin = sc.nextInt();
        System.out.print("                             Amount : ");
        int sending_amount = sc.nextInt();


        System.out.println();
        System.out.println("************************************************************************");

        try {
            if (sending_amount < 0) {
                System.out.println("                       Invalid amount");
                goEnterKey();
                return;
            }
            boolean sender = false;
            boolean receiver = false;
            int money = 0;
            int receiver_money = 0;
            String url = "jdbc:mysql://localhost:3307/java_bank_system";
            String conn_username = "root";
            String conn_password = "vishal";

            Connection connection = DriverManager.getConnection(url, conn_username, conn_password);

            System.out.println();

            Statement stmt = connection.createStatement();

            String Sender_exsist = "select * from details where username = '"+username+"' && password = '"+password+"'";
            ResultSet sender_result = stmt.executeQuery(Sender_exsist);

            while (sender_result.next()) {
                if (sending_amount <= sender_result.getInt("money")) {
                    if (sender_pin == sender_result.getInt("pin")) {
                        sender = true;
                        money = sender_result.getInt("money") - sending_amount;
                    }
                }
                else{
                    System.out.println("                       not either money in your account");
                    goEnterKey();
                    return;
                }
            }
            if (!sender) {
                System.out.println("                       sender pin is wrong");
                goEnterKey();
                return;
            }
            String receiver_exsist = "select * from details where username = '"+receiver_username+"'";
            ResultSet receiver_result = stmt.executeQuery(receiver_exsist);

            while (receiver_result.next()) {
                receiver = true;
                receiver_money = receiver_result.getInt("money") + sending_amount;
            }

            if (!receiver) {
                System.out.println("                       receiver don't exsist");
                goEnterKey();
                return;
            }
            if (receiver) {
                String cut_money_receiver = "update details set money="+receiver_money+" where username = '"+receiver_username+"'";

                int result_receiver = stmt.executeUpdate(cut_money_receiver);
            }
            if (sender) {
                String cut_money_sender = "update details set money="+money+" where username = '"+username+"'";

                int result_cut = stmt.executeUpdate(cut_money_sender);
                System.out.println("                         transfer sucessfull");
            }

            
        } catch (Exception e) {
            System.out.println(e);
        }
        goEnterKey();
    }


    // user details funtions

    public static void user_details(){
        Scanner sc = new Scanner(System.in);
        
        try {
            String url = "jdbc:mysql://localhost:3307/java_bank_system";
            String conn_username = "root";
            String conn_password = "vishal";

            Connection connection = DriverManager.getConnection(url, conn_username, conn_password);

            System.out.println();

            Statement stmt = connection.createStatement();

            String sql = "select * from details where username = '"+username+"'";
            ResultSet result_sql = stmt.executeQuery(sql);

            while (result_sql.next()) {
                
                System.out.println("************************************************************************");
                System.out.println("****                          User Details                          ****");
                System.out.println("************************************************************************");
                System.out.println();
                
                System.out.println("                               Name : "+result_sql.getString("name"));
                System.out.println("                      Mobile Number : "+result_sql.getBigDecimal("mobile_number"));
                System.out.println("                             E-mail : "+result_sql.getString("email"));
                System.out.println("                                Age : "+result_sql.getInt("age"));
                System.out.println("                           Username : "+result_sql.getString("username"));
                System.out.println("                           Password : "+result_sql.getString("password"));
                System.out.println("                              Money : "+result_sql.getInt("money"));

                System.out.println();
                System.out.println("************************************************************************");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        goEnterKey();
    }

    // Enter and go funtion

    public static void goEnterKey(){
        System.out.println("                       Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

}
