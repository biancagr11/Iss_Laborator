package scheduleMaker.client.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MessageBox {
    static void showErrorMessage( String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    static void showWarningMessage(String text) {
        Alert message = new Alert(Alert.AlertType.WARNING);
        message.setTitle("Warning");
        message.setContentText(text);
        message.showAndWait();
    }
    static Optional<ButtonType> confirmationMessage(String text) {
        Alert message = new Alert(Alert.AlertType.CONFIRMATION);
        message.setTitle("Warning");
        message.setContentText(text);
        return message.showAndWait();
    }

    static void showMessage(String text) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Mesaj");
        message.setContentText(text);
        message.showAndWait();
    }
}
