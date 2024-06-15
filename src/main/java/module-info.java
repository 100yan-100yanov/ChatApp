module com.texting.chatapp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.texting.chatapp to javafx.fxml;
    exports com.texting.chatapp;
}