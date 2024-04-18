import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp {
    public static void afisare_tabela(Statement statement, String mesaj) {
        String sql
                ="select * from persoane";
        System.out.println("\n---"
                +mesaj
                +"---");
        try(ResultSet rs
                    =statement.executeQuery(sql)) {
            while (rs.next())
                System.out.println("idpersoana=" + rs.getInt(1) + ", nume=" + rs.getString(2) + ",varsta="
                        + rs.getInt(3));
        } catch (SQLException
                e) {
            e.printStackTrace();
        }
    }
    public static void adaugare(Connection connection, int idpersoana, String nume, int varsta) {
        String sql="insert into persoane values (?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, idpersoana);
            ps.setString(2, nume);
            ps.setInt(3, varsta);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de adaugare="+nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }
    public static void actualizare(Connection connection,int idpersoana,int varsta){
        String sql="update persoane set varsta=? where id=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, varsta);
            ps.setInt(2, idpersoana);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de modificare="+nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }
    public static void stergere(Connection connection,int idpersoana ){
        String sql="delete from persoane where id=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, idpersoana);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de modificare="+nr_randuri);
        }
        catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/lab9";
        try {
            Connection connection = DriverManager.getConnection(url, "root", "root");
            Statement statement = connection.createStatement();
            afisare_tabela(statement,"Continut initial");
            adaugare(connection,4,"Dana",23);
            afisare_tabela(statement,"Dupa adaugare");
            actualizare(connection,4,24);
            afisare_tabela(statement,"Dupa modificare");

            stergere(connection,4);
            afisare_tabela(statement,"Dupa stergere");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}














//public class MainApp {
//    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost/excursii";
//
//    static final String USER = "root";
//    static final String PASS = "root";
//
//    public static void main(String[] args) {
//        Connection connection = null;
//        Statement statement = null;
//
//        try {
//            Class.forName(JDBC_DRIVER);
//            connection = DriverManager.getConnection(DB_URL, USER, PASS);
//            statement = connection.createStatement();
//
//            Scanner scanner = new Scanner(System.in);
//            int choice = 0;
//
//            while (true) {
//                System.out.println("\nMeniu:");
//                System.out.println("1. Adaugare persoana");
//                System.out.println("2. Adaugare excursie");
//                System.out.println("3. Afisare persoane si excursiile lor");
//                System.out.println("4. Afisare excursii pentru o persoana");
//                System.out.println("9. Iesire");
//
//                System.out.print("Alegeti o optiune: ");
//                choice = scanner.nextInt();
//
//                switch (choice) {
//                    case 1:
//
//                        System.out.print("Introduceti numele persoanei: ");
//                        String nume = scanner.next();
//                        System.out.print("Introduceti varsta persoanei: ");
//                        int varsta = scanner.nextInt();
//
//                        String query = "INSERT INTO persoane (nume, varsta) VALUES ('" + nume + "', '" + varsta + "')";
//                        statement.executeUpdate(query);
//                        System.out.println("Persoana adaugata cu succes!");
//                        break;
//
//
//                    case 2:
//                        System.out.print("Introduceti id-ul persoanei pentru care adaugati excursia: ");
//                        int idpersoana = scanner.nextInt();
//
//                        String checkQuery = "SELECT * FROM persoane WHERE id='" + idpersoana + "'";
//                        ResultSet rsCheck = statement.executeQuery(checkQuery);
//
//                        if (rsCheck.next()) {
//                            System.out.print("Introduceti destinatia excursiei: ");
//                            String destinatie = scanner.next();
//                            System.out.print("Introduceti anul excursiei: ");
//                            int an = scanner.nextInt();
//
//                            String insertQuery = "INSERT INTO excursii (idpersoana, destinatie, an) VALUES ('" + idpersoana + "', '" + destinatie + "', '" + an + "')";
//                            statement.executeUpdate(insertQuery);
//                            System.out.println("Excursia adaugata cu succes!");
//                        } else {
//                            System.out.println("Persoana nu a fost gasita!");
//                        }
//                        break;
//                    case 3:
//                        String query3 = "SELECT * FROM persoane";
//                        ResultSet rs3 = statement.executeQuery(query3);
//
//                        while (rs3.next()) {
//                            int idPersoana = rs3.getInt("idpers");
//                            String numePersoana = rs3.getString("nume");
//
//                            System.out.println("\nPersoana: " + numePersoana);
//
//                            String queryExcursii = "SELECT * FROM excursii WHERE idpersoana='" + idPersoana + "'";
//                            ResultSet rsExcursii = statement.executeQuery(queryExcursii);
//
//                            while (rsExcursii.next()) {
//                                String destinatie = rsExcursii.getString("destinatie");
//                                int an = rsExcursii.getInt("an");
//
//                                System.out.println("Excursie: " + destinatie + ", An: " + an);
//                            }
//                        }
//                        break;
//                    case 4:
//                        System.out.print("Introduceti numele persoanei: ");
//                        String numePersoana = scanner.next();
//
//                        String query4 = "SELECT * FROM persoane WHERE nume='" + numePersoana + "'";
//                        ResultSet rs4 = statement.executeQuery(query4);
//
//                        if (rs4.next()) {
//                            int idPersoana = rs4.getInt("id");
//
//                            String queryExcursiiPersoana = "SELECT * FROM excursii WHERE idpersoana='" + idPersoana + "'";
//                            ResultSet rsExcursiiPersoana = statement.executeQuery(queryExcursiiPersoana);
//
//                            while (rsExcursiiPersoana.next()) {
//                                String destinatie = rsExcursiiPersoana.getString("destinatie");
//                                int an = rsExcursiiPersoana.getInt("an");
//
//                                System.out.println("Excursie: " + destinatie + ", An: " + an);
//                            }
//                        } else {
//                            System.out.println("Persoana nu a fost gasita!");
//                        }
//                        break;
//                    case 9:
//                        System.out.println("Iesire din aplicatie");
//                        connection.close();
//                        return;
//                    default:
//                        System.out.println("Optiune invalida!");
//                        break;
//                }
//            }
//        } catch (SQLException se) {
//            se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException se2) {
//            }
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//        }
//    }
//}