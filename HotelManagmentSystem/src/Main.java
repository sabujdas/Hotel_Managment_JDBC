import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Main{
    private static final String url = "jdbc:mysql://localhost:3306/hotelreservationsystem";
    private static final String username = "root";
    private static final String password = "Sabuj12345*";
    public static void main(String[] args)throws ClassNotFoundException,SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. NEW RESERVATIONS");
                System.out.println("2. CHECK RESERVATIONS");
                System.out.println("3. GET ROOM NO.");
                System.out.println("4. UPDATE RESERVATIONS");
                System.out.println("5. DELETE RESERVATIONS");
                System.out.println("6. EXIT");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        new_reservation(connection,scanner);
                        break;
                    case 2:
                        view_reservation(connection);
                        break;
                    case 3:
                        get_room_no(connection,scanner);
                        break;
                    case 4:
                        update_reservation(connection,scanner);
                        break;
                    case 5:
                        delete_reservations(connection,scanner);
                        break;
                    case 6:
                        exit();
                        break;
                    default:
                        System.out.println("you have choose invalid options");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

private static void new_reservation(Connection connection,Scanner scanner) {
    try {
        System.out.println("Enter your name:");
        String name = scanner.next();
        scanner.nextLine();
        System.out.println("Enter room no:");
        int no = scanner.nextInt();
        System.out.println("Contact No:");
        String contact = scanner.next();
        String query = "insert into reservations(guest_name,room_no,Contact_No)" + "values('" + name + "'," + no + ",'" + contact + "')";
        try (Statement statement = connection.createStatement()) {
            int rows = statement.executeUpdate(query);
            if (rows > 0) {
                System.out.println("guest details has been registered");
            } else
                System.out.println("Not been registered due to invalid values");
        }
    }catch (SQLException e){
        e.printStackTrace();
            }
    }

private static void view_reservation(Connection connection)throws SQLException{
        String query = "select id,guest_name,room_no,Contact_No,reservation_date from reservations";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String guest = resultSet.getString("guest_name");
                int room_no = resultSet.getInt("room_no");
                String Contact_no = resultSet.getString("Contact_no");
                Date date = resultSet.getDate("reservation_date");
                System.out.println("id: " + id);
                System.out.println("guest Name: " + guest);
                System.out.println("Room No: " + room_no);
                System.out.println("Contact No: " + Contact_no);
                System.out.println("Date: " + date);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
}

private static void get_room_no(Connection connection,Scanner scanner)throws SQLException{
        try{
            System.out.println("Enter the guest id : ");
            int id = scanner.nextInt();
            System.out.println("Enter the guest name :");
            String name = scanner.next();

            String query = "Select room_no from reservations" +
                    "where id = " + id + "AND guest_name = '"+ name +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                int room_no = resultSet.getInt("room_no");
                System.out.println("Room No: "+room_no);
            }
            else {
                System.out.println("Wrong room");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
}

private static void update_reservation(Connection connection,Scanner scanner) {
    try {
        System.out.println("Enter the guest ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume new line character

        if (!ReservationExist(connection, id)) {
            System.out.println("Reservation was never exists");
            return;
        }

        System.out.println("Enter the guest Name: ");
        String name = scanner.nextLine();
        System.out.println("Room No:");
        int no = scanner.nextInt();
        System.out.println("Contact No:");
        String contact_no = scanner.next();


        String query = "UPDATE reservations SET guest_name ='" + name + "'," +
                "room_no = " + no + "," +
                "Contact_No = '" + contact_no + "'"+
                "where id = " + id;
        try (Statement statement = connection.createStatement()) {
            int affectedrows = statement.executeUpdate(query);
            if (affectedrows > 0) {
                System.out.println("Reservations has been updated");

            } else {
                System.out.println("Not been updated");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
private static void delete_reservations(Connection connection,Scanner scanner){
    System.out.println("Enter the guest_id that want to delete :");
    int id = scanner.nextInt();

    try {
        if (!ReservationExist(connection, id)) {
            System.out.println("id doesn't exist");
            return;
        }
        String query = "DELETE from reservations where id = " + id;
        try (Statement statement = connection.createStatement()) {
            int affectedrows  = statement.executeUpdate(query);
            if(affectedrows > 0){
                System.out.println("Details has been Deleted");
            }
            else {
                System.out.println("not deleted");
            }
        }
    }catch (SQLException e){
        e.printStackTrace();
    }
}

private static boolean ReservationExist(Connection connection , int id){
        try{
        String query = "Select id from reservations where id = "+id;
        try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)){
            return resultSet.next();
        }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
}
public static void exit()throws InterruptedException{
    System.out.println("Exiting System");
        int i = 5;
        while (i!=0){
            System.out.println(".");
            Thread.sleep(450);
            i--;
        }
    System.out.println();
    System.out.println("THANKS FOR VISITING HOTEL MANAGEMENT SYSTEM");
}
}