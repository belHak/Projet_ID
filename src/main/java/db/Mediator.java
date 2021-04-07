package db;

import Connect.Connect;
import wrappers.Wrapper;
import wrappers.WrapperBD;
import wrappers.WrapperCSV;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mediator {

        private static final List<Wrapper> Wrappers = new ArrayList<>();

    /**
     *
     * @param sql
     */
    public static void request(String sql)  {


            String select = sql.substring(sql.indexOf("SELECT")+6, sql.indexOf("FROM"));
            String from = "";
            String where = "";
            if(sql.contains("WHERE")){
                from = sql.substring(sql.indexOf("FROM")+4, sql.indexOf("WHERE"));
                where = sql.substring(sql.indexOf("WHERE")+5);
            }
            else{
                from = sql.substring(sql.indexOf("FROM")+4);
            }
            String[] views  =from.split(",");
            initWrappers(views);
            Wrappers.forEach(Wrapper::parse);
            sql = "SELECT "+select+" FROM "+from;
            if(!where.equals(""))
                sql+=where ;

            try (Connection c = Connect.connect()) {
                Statement stmt = c.createStatement();
                System.out.println(sql);
                ResultSet resultSet = stmt.executeQuery(sql);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(" | ");
                    System.out.print( rsmd.getColumnName(i));
                }
                System.out.println("");
                while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(" , ");
                        String columnValue = resultSet.getString(i);
                        System.out.print(columnValue + " " /*+ rsmd.getColumnName(i)*/);
                    }
                    System.out.println("");
                }
                stmt.close();
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
            Wrappers.forEach(Wrapper::dropTable);
        }

    /**
     *
     * @param views
     */
    public static void initWrappers(String... views){
            for(String view : views){
                ;
                view = view.replaceAll("\\s+","");
                String[] sources = Catalog.getView(view);
                for(String source : sources){
                    String type  = Catalog.getType(source);
                    switch (type){
                        case "CSV" : Wrappers.add(new WrapperCSV(source));break;
                        case "BD" : Wrappers.add(new WrapperBD(source));break;
                    }
                }
            }
        }

    public static void main(String[] args) throws SQLException {

        request("SELECT * FROM citoyens");
        request("SELECT * FROM articles_voitures");
        //request("SELECT * FROM articles_films");

    }
}
