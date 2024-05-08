package pl.lechowicz.texttospeechgui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class TextToSpeechGui extends Application {
    private static final int APP_WIDTH = 375;
    private static final int APP_HEIGHT = 475;

    private TextArea textArea;
    private ComboBox<String> voices, rates, volumes;

    @Override
    public void start(Stage stage) {
        Scene scene = createScene();
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(
                "style.css"
        )).toExternalForm());
        stage.setTitle("Text-To-Speech App");
        stage.setScene(scene);
        stage.show();
    }

    private Scene createScene() {
        VBox box = new VBox();
        box.getStyleClass().add("body");
        box.getChildren().addAll(getTextToSpeechLabel(),
                getTextToSpeakStackPane(),
                createSettingsComponent(),
                getSpeakButtonStackPane()
        );

        return new Scene(box, APP_WIDTH, APP_HEIGHT);
    }

    private StackPane getSpeakButtonStackPane() {
        Button speakButton = createImageButton();
        speakButton.setOnAction(actionEvent -> {
            String msg = textArea.getText();
            String voice = voices.getValue();
            String rate = rates.getValue();
            String volume = volumes.getValue();

            TextToSpeechController.speak(msg, voice, rate, volume);
        });

        StackPane speakButtonPane = new StackPane();
        speakButtonPane.setPadding(new Insets(40, 20, 0, 20));
        speakButtonPane.getChildren().add(speakButton);
        return speakButtonPane;
    }

    private StackPane getTextToSpeakStackPane() {
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.getStyleClass().add("text-area");

        StackPane textAreaPane = new StackPane();
        textAreaPane.setPadding(new Insets(0, 15, 0, 15));
        textAreaPane.getChildren().add(textArea);
        return textAreaPane;
    }

    private static Label getTextToSpeechLabel() {
        Label textToSpeechLabel = new Label("Text-To-Speech");
        textToSpeechLabel.getStyleClass().add("text-to-speech-label");
        textToSpeechLabel.setMaxWidth(Double.MAX_VALUE);
        textToSpeechLabel.setAlignment(Pos.CENTER);
        return textToSpeechLabel;
    }

    private Button createImageButton() {
        Button button = new Button("Speak");
        button.getStyleClass().add("speak-btn");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(
                new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("speak.png"))
                )
        );

        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        button.setGraphic(imageView);

        return button;
    }

    private GridPane createSettingsComponent() {
        GridPane gridPane = getGridPaneForSettings();

        createLabel("Voice", gridPane, 0);
        createLabel("Rate", gridPane, 1);
        createLabel("Volume", gridPane, 2);

        voices = comboBoxInit(TextToSpeechController.getVoices(), 0);
        rates = comboBoxInit(TextToSpeechController.getSpeedRates(), 0);
        var volumeLevels = TextToSpeechController.getVolumeLevels();
        int mediumVolumeLevel = volumeLevels.size()/2;
        volumes = comboBoxInit(volumeLevels, mediumVolumeLevel);

        gridPane.add(voices, 0, 1);
        gridPane.add(rates, 1, 1);
        gridPane.add(volumes, 2, 1);

        return gridPane;
    }

    private ComboBox<String> comboBoxInit(ArrayList<String> values, int defaultSelect) {
        var comboBox = new ComboBox<String>();
        comboBox.getItems().addAll(
                values
        );
        comboBox.setValue(comboBox.getItems().get(defaultSelect));
        comboBox.getStyleClass().add("setting-combo-box");

        return comboBox;
    }

    private void createLabel(String Voice, GridPane gridPane, int column) {
        Label voiceLabel = new Label(Voice);
        voiceLabel.getStyleClass().add("setting-label");
        gridPane.add(voiceLabel, column, 0);
        GridPane.setHalignment(voiceLabel, HPos.CENTER);
    }

    private GridPane getGridPaneForSettings() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 0, 0, 0));
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    public static void main(String[] args) {
        launch();
    }
}