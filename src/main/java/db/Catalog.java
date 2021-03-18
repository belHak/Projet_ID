package db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalog {


    private static final Map<String, String[]> views  = new HashMap<String, String[]>(
            Map.of(
                    "articles", new String[]{"film", "critics"},
                    "tables", new String[]{"film", "critics", "cars", "usa", "velib"}
            )
    );

    private static final Map<String, String> sourceType  = new HashMap<String, String>(
            Map.of(
                    "film", "CSV",
                    "critics","BD",
                    "cars","CSV",
                    "usa","CSV",
                    "velib","CSV"

            )
    );

    public static String[] getView(String view){
        return views.get(view);
    }

    public static String getType(String source){
        return sourceType.get(source);
    }



}
