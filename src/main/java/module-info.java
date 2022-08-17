module com.cbs.commerclient {
    requires javafx.controls;
    requires javafx.fxml;    
    requires com.fasterxml.jackson.annotation;
    requires javax.ws.rs.api;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens com.cbs.commerclient to javafx.fxml;
    exports com.cbs.commerclient;
    exports com.cbs.commerclient.model; 
}
