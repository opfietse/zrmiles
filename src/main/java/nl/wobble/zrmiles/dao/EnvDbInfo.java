package nl.wobble.zrmiles.dao;

public class EnvDbInfo extends DbInfo {

    public EnvDbInfo() {
        driver = System.getenv("ZRMILES_DB_CLASS");
        System.out.println("Db class: " + driver);
        url = System.getenv("ZRMILES_DB_URL");
        user = System.getenv("ZRMILES_DB_USER");
        password = System.getenv("ZRMILES_DB_PASSWORD");
    }
}
