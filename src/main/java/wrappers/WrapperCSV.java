package wrappers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WrapperCSV extends AbstractWrapper implements Wrapper {




    public WrapperCSV(String source){
        super(source);
    }

    /**
     *
     * parse csv file into SQL table
     */
    @Override
    public void parse() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/main/java/sources/"+source+".csv"));
            String line = reader.readLine();
            setColumns(line.split(";"));
            createTable();

            while ((line = reader.readLine()) != null) {
                insertValue(line.split(";"));
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
