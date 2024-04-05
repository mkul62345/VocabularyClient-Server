module com.example.wordquizclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.wordquizclient.model to com.google.gson;
    opens com.example.wordquizclient to javafx.fxml;

    exports com.example.wordquizclient.model;
    exports com.example.wordquizclient;

}