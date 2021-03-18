package db;

import Connect.Connect;
import wrappers.IWrapper;
import wrappers.WrapperBD;
import wrappers.WrapperCSV;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mediator {

        private static List<IWrapper> IWrappers = new ArrayList<>();

        public static void request(String select, String from, String where) throws SQLException {

            String[] views  =from.split(",");
            initWrappers(views);
            IWrappers.forEach(IWrapper::parse);
            String sql = "SELECT "+select+" FROM "+from;
            if(!where.equals(""))
                sql+=where ;

            try (Connection c = Connect.connect()) {
                Statement stmt = c.createStatement();
                System.out.println(sql);
                ResultSet resultSet = stmt.executeQuery(sql);
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
            IWrappers.forEach(IWrapper::drop);
        }

        public static void initWrappers(String... views){
            for(String view : views){
                ;
                view = view.replaceAll("\\s+","");
                String[] sources = Catalog.getView(view);
                for(String source : sources){
                    String type  = Catalog.getType(source);
                    switch (type){
                        case "CSV" : IWrappers.add(new WrapperCSV(source));break;
                        case "BD" : IWrappers.add(new WrapperBD(source));break;
                    }
                }
            }
        }

    public static void main(String[] args) throws SQLException {
        request("*","tables"," ");
//        request("*","articles","");
    }
}
