package wrappers;



import java.sql.*;

public class WrapperBD extends AbstractWrapper implements Wrapper {

    public WrapperBD(String source) {
        super(source);
    }


    /**
     *
     * parse BD into SQL table
     */
    @Override
    public void parse() {
        String sql = "SELECT * FROM " + source;
        try (Connection c = connectToBdSource()) {
            Statement stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            setColumns(new String[columnsNumber]);
            for (int i = 1; i <= columnsNumber; i++) {
                getColumns()[i-1] = rsmd.getColumnName(i) ;
            }
            createTable();

            while (resultSet.next()) {
                String[] values = new String[getColumns().length];
                for (int i = 1; i <= getColumns().length; i++) {
                    String columnValue = resultSet.getString(i);
                    values[i-1]  = columnValue ;
                }
                insertValue(values);
            }
            stmt.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
    public Connection connectToBdSource() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/java/sources/" + source+".db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        WrapperBD wrapperBD = new WrapperBD("critics");
        wrapperBD.parse();
    }

}
