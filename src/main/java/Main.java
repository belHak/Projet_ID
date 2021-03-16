
import wrappers.Wrapper;
import wrappers.WrapperCSV;

public class Main {

    public static void main(String[] args) {

//        Wrapper velib = new WrapperCSV("velib");
//        velib.parse();
//        Wrapper usa = new WrapperCSV("usa");
//        usa.parse();
        Wrapper portals = new WrapperCSV("portals");
        portals.parse();



    }
}
