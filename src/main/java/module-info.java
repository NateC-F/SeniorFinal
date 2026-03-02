module com.example.seniorfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;


    opens com.example.seniorfinal to javafx.fxml;
    exports com.example.seniorfinal;
}