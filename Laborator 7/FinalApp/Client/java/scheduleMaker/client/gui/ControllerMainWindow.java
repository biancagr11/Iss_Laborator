package scheduleMaker.client.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.MainApp;
import scheduleMaker.services.ScheduleMakerException;


import java.io.IOException;
import java.io.Serializable;

public class ControllerMainWindow implements Serializable {
    private ClientController service;
    private double xOffset;
    private double yOffset;
    private BorderPane mainLayout;
    private ControllerLoginWindow loginWindow;
    private String username;

    public void setMainLayout(BorderPane mainLayout) {
       this.mainLayout = mainLayout;
    }

    @FXML
    Button closeIcon;
    @FXML
    Button minusIcon;
    @FXML
    Button circleIcon;

    @FXML
    Button employeeButton;
    @FXML
    HBox barBox;
    @FXML
    Button logoutButton;




    public void initialize(){
    closeIcon.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
        @Override
        public void handle(javafx.scene.input.MouseEvent mouseEvent) {
            Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            try{
                service.logout();
                loginWindow.showAgain();
            }catch (ScheduleMakerException ex){
                MessageBox.showErrorMessage("Could not logout! Try again!");
            }
            stage.close();
        }
    });
        logoutButton.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                try{
                    service.logout();
                    loginWindow.showAgain();
                }catch (ScheduleMakerException ex){
                    MessageBox.showErrorMessage("Could not logout! Try again!");
                }
                stage.close();
            }
        });

    circleIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            if(stage.isFullScreen())
            {
                stage.setFullScreen(false);
                stage.setHeight(472);
                stage.setWidth(759);
            }
            else
            {
                stage.setFullScreen(true);
            }

        }
    });
    minusIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setIconified(true);
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

    public void setLoginWindow(ControllerLoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }

    public void handleEmployeeButton() throws IOException {
       this.showEmployeeStage();
    }
    public void handleScheduleButton()throws IOException{
        //main.main.MainApp.showScheduleStage();
        this.showEmployeeStage();
    }
        public void showLogoScene() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/views/logoStage.fxml"));
        AnchorPane pane=loader.load();
        mainLayout.setCenter(pane);

    }

    public void showEmployeeStage() throws IOException{
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/views/employeeScene.fxml"));
        AnchorPane pane=loader.load();
        mainLayout.setCenter(pane);
        ControllerEmployeeScene controllerEmployeeScene=loader.getController();
        controllerEmployeeScene.setService(service);
        controllerEmployeeScene.loadList();
    }
    public void showScheduleStage() throws IOException{
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/views/scheduleScene.fxml"));
        AnchorPane pane=loader.load();
        mainLayout.setCenter(pane);
        ControllerScheduleScene controllerEmployeeScene=loader.getController();
        controllerEmployeeScene.setService(service);
    }
    public void showMessageStage() throws IOException{
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/views/messageScene.fxml"));
        AnchorPane pane=loader.load();
        mainLayout.setCenter(pane);
        ControllerMessageScene controllerEmployeeScene=loader.getController();
        controllerEmployeeScene.setService(service, username);
    }
    public void setClientController (ClientController clientController, String username){
        this.service = clientController;
        this.username = username;
    }

}
