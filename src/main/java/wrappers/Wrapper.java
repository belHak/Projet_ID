package wrappers;

import Connect.Connect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Wrapper  {
    String source;
    private String[] columns;

    public Wrapper(String source) {
        this.source = source;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public void drop() {
        String sql = "DROP TABLE "+source+" ;" ;
        try (Connection c = Connect.connect()) {
            Statement stmt = c.createStatement();
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void createTable()  {
        StringBuilder sql = new StringBuilder( ) ;
        sql.append ( "DROP Table IF EXISTS "+source+" ;\nCREATE TABLE "+source +" ( " ) ;
        for(int index = 0 ; index < columns.length ; index++){
            if(index == 0 )
                sql.append(columns[index] +" VARCHAR(16) PRIMARY KEY NOT NULL , ");
            else if(index == columns.length-1)
                sql.append(columns[index]+" VARCHAR(60) ");
            else
                sql.append(columns[index]+" VARCHAR(100), ");
        }
        sql.append  ( ");\n " );

        try (Connection c = Connect.connect()) {
            Statement stmt = c.createStatement();

            System.out.println(sql);
            stmt.executeUpdate(sql.toString());
            stmt.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void insertValue(String[] values){
        StringBuilder sql = new StringBuilder( ) ;
        sql.append ( "INSERT INTO "+source +" ( " ) ;
        sql.append(columns[0]+" , ");
        for(int index = 1 ; index < columns.length ; index++){
            if(index == columns.length-1)
                sql.append(columns[index]);
            else
                sql.append(columns[index]+" , ");
        }
        sql.append  ( ") VALUES ( " );

        sql.append("'"+values[0]+"'");
        for(int index = 1 ; index < values.length ; index++) {
            sql.append(",");
            String value = values[index].equals("") ?  "NULL" : "'"+values[index]+"'";
            sql.append(value);
        }
        sql.append  ( "); \n" );
        try (Connection c = Connect.connect()) {
            Statement stmt = c.createStatement();
            System.out.println(sql);
            stmt.executeUpdate(sql.toString());
            stmt.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
}
