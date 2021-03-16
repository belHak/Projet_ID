package wrappers;


import Connect.Connect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class WrapperCSV implements Wrapper{

    private String[] columns ;
    private String source ;

    public WrapperCSV(String source){
        this.source = source ;
    }

    /**
     *
     * parse csv file into SQL table
     */
    public  void parseCSV(){
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/main/java/wrappers/sources/"+source+".csv"));
            String line = reader.readLine();

            columns =line.split(",");
            createTable(source);

            while ((line = reader.readLine()) != null) {
                insertValue(source,line.split(","));
            }

            reader.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void createTable(String tableName) throws SQLException {
        StringBuilder sql = new StringBuilder( ) ;
        sql.append ( "DROP Table IF EXISTS "+tableName+" ;\nCREATE TABLE "+tableName +" ( " ) ;
        for(int index = 0 ; index < columns.length ; index++){
            if(index == 0 )
                sql.append(columns[index] +" INT PRIMARY KEY NOT NULL , ");
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

    public  void  insertValue(String tableName,String[] values){
        StringBuilder sql = new StringBuilder( ) ;
        sql.append ( "INSERT INTO "+tableName +" ( " ) ;
        for(int index = 0 ; index < columns.length ; index++){
            if(index == columns.length-1)
                sql.append(columns[index]);
            else
                sql.append(columns[index]+" , ");

        }
        sql.append  ( ") VALUES ( " );


        sql.append(values[0]);

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

    @Override
    public void parse() {
        parseCSV();
    }

    @Override
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
}
