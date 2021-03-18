package Connect;

import java.sql.*;


public class Connect {

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:src/main/java/db/database.db";

            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM velib";
        try (Connection c = Connect.connect()) {
            Statement stmt = c.createStatement();
            System.out.println(sql);
            resultSet = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " /*+ rsmd.getColumnName(i)*/);
                }
                System.out.println("");
            }
            stmt.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }
}