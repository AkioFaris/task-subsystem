package moocplatform.task.enums;

public enum DbConfigurations {
    DRIVER("com.mysql.jdbc.Driver"),
    HOST("localhost"),
    PORT("3306"),
    DB_NAME("apprz_db"),
    JDBC_URL("jdbc:mysql://" + HOST.text + ":" + PORT.text + "/" + DB_NAME.text),
    USER_NAME("FAN"),
    PASS("2018");

    public String text;

    DbConfigurations(String text) {
        this.text = text;
    }
}
