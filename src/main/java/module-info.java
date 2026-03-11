module com.example.seniorfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires jbcrypt;


    opens com.example.seniorfinal to javafx.fxml;
    exports com.example.seniorfinal;
    exports com.example.seniorfinal.Model.DAO;
    opens com.example.seniorfinal.Model.DAO to javafx.fxml;
    exports com.example.seniorfinal.Controllers;
    opens com.example.seniorfinal.Controllers to javafx.fxml;
    exports com.example.seniorfinal.Utilities;
    opens com.example.seniorfinal.Utilities to javafx.fxml;
    exports com.example.seniorfinal.Core;
    opens com.example.seniorfinal.Core to javafx.fxml;
}