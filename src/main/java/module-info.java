module pl.lechowicz.texttospeechgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;


    opens pl.lechowicz.texttospeechgui to javafx.fxml;
    exports pl.lechowicz.texttospeechgui;
}