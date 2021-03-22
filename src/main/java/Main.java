
import wrappers.Wrapper;
import wrappers.WrapperCSV;

public class Main {

    public static void main(String[] args) {

        Wrapper portals = new WrapperCSV("film");
        portals.parse();

    }
}
