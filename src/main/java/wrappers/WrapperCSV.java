package wrappers;


import Connect.Connect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class WrapperCSV {

    private static String[] columns ;

    /**
     *
     * @param csvPath
     * @param tableName
     * parse csv file into SQL table
     */
    public static String parseCSV(String csvPath,String tableName){
        BufferedReader reader;
        StringBuilder query = new StringBuilder();
        try {

            reader = new BufferedReader(new FileReader(csvPath));
            String line = reader.readLine();

            columns =line.split(";");
            query.append(createTable(tableName));
            while (line != null) {
                query.append(insertValue(tableName,line.split(";")));
                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return query.toString();
    }

    public static String createTable(String tableName){
        StringBuilder sql = new StringBuilder( ) ;
        sql.append ( "CREATE TABLE IF NOT EXISTS "+tableName +" ( " ) ;
        for(int index = 0 ; index < columns.length ; index++){
            if(index == 0 )
                sql.append(columns[index] +" INT PRIMARY KEY NOT NULL , ");
            else if(index == columns.length-1)
                sql.append(columns[index]+" VARCHAR(60) ");
            else
                sql.append(columns[index]+" VARCHAR(100), ");
        }
        sql.append  ( ");\n " );

       return sql.toString();
    }

    public static String  insertValue(String tableName,String[] values){
        StringBuilder sql = new StringBuilder( ) ;
        sql.append ( "INSERT INTO "+tableName +" ( " ) ;
        for(int index = 0 ; index < columns.length ; index++){
            if(index == columns.length-1)
                sql.append(columns[index]);
            else
                sql.append(columns[index]+" , ");

        }
        sql.append  ( ") VALUES ( " );
        for(int index = 0 ; index < values.length ; index++) {

            if(index == 0 )
                sql.append(values[index]+" , ");
            else if(index == values.length-1)
                sql.append("'"+values[index]+"'");
            else
                sql.append("'"+values[index]+"'"+" , ");

        }
        sql.append  ( "); \n" );

        return sql.toString();
    }

    public static void main(String[] args) {
        try (Connection c = Connect.connect()){
            Statement stmt = c.createStatement();
            System.out.println(parseCSV("src/main/java/data/velib.csv","velib"));
            //stmt.executeUpdate(parseCSV("src/main/java/data/velib.csv","velib"));
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
