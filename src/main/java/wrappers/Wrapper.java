package wrappers;

public interface Wrapper {

    void parse();
    void dropTable();
    void insertValue(String[] values);
    void createTable();

}
