
import wrappers.IWrapper;
import wrappers.WrapperCSV;

public class Main {

    public static void main(String[] args) {

        IWrapper portals = new WrapperCSV("film");
        portals.parse();

    }
}
