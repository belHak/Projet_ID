package db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalog {


    private static final Map<String, String[]> views  = new HashMap<String, String[]>(
            Map.of(
                    "articles_films", new String[]{ "criticsFilms","film"},
                    "articles_voitures", new String[]{"cars", "criticsCars"},
                    "citoyens",new String[]{"usa"}
            )
    );

    private static final Map<String, String> sourceType  = new HashMap<String, String>(
            Map.of(
                    "film", "CSV",
                    "criticsFilms","BD",
                    "cars","BD",
                    "usa","CSV",
                    "criticsCars","CSV"

            )
    );

    public static String[] getView(String view){
        return views.get(view);
    }

    public static String getType(String source){
        return sourceType.get(source);
    }

}
