package wrappers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WrapperCSV {

    public static void parseCSV(String csvPath){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "/Users/pankaj/Downloads/myfile.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
