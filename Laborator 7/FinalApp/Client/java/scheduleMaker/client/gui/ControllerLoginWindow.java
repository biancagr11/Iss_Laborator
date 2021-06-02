package scheduleMaker.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import scheduleMaker.services.ScheduleMakerException;

import java.io.Serializable;
import java.time.LocalTime;

public class ControllerLoginWindow implements Serializable {

    private ClientController clientController;

    private double xOffset;
    private double yOffset;
    private Stage me;

    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    Button closeButton;
    @FXML
    Button minusButton;
    @FXML
    Button circleButton;
    @FXML
    Spinner<LocalTime> firstHour;
    @FXML
    Spinner<LocalTime> secondHour;
    @FXML
    HBox barBox;


    public void initialize() {
//        usernameField.setText("feliciabl");
//        passwordField.setText("felicia1");
        closeButton.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.close();
            }
        });

        barBox.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                stage.setX(mouseEvent.getScreenX()+xOffset);
                stage.setY(mouseEvent.getScreenY()+yOffset);
            }
        });
        barBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                xOffset=stage.getX()-mouseEvent.getScreenX();
                yOffset=stage.getY()-mouseEvent.getScreenY();
            }
        });
    }

    public void handleSingin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {

            String permissions=clientController.login(username,password);

            if(permissions.equals("admin")) {
                Stage stage = new Stage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainWindow.fxml"));
                BorderPane root = loader.load();
                ControllerMainWindow mainWindoowController = loader.getController();

                mainWindoowController.setClientController(clientController, username);
                mainWindoowController.setMainLayout(root);
                mainWindoowController.setLoginWindow(this);
                mainWindoowController.showEmployeeStage();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }else{
                Stage stage = new Stage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/regularWindow.fxml"));
                BorderPane root = loader.load();
                ControllerRegularWindow mainWindoowController = loader.getController();

                mainWindoowController.setMainLayout(root);
                mainWindoowController.setClientController(clientController, username);
                mainWindoowController.setLoginWindow(this);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }
            me=(Stage)((Node)(actionEvent.getSource())).getScene().getWindow();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

       } catch (ScheduleMakerException ex) {
            passwordField.setText("");
            MessageBox.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            MessageBox.showErrorMessage(ex.getMessage());
            usernameField.setText("");
            passwordField.setText("");
        }
    }

        public void setClientController (ClientController clientController){
            this.clientController = clientController;
        }

        public void showAgain(){
            me.show();
        }
}
