package scheduleMaker.client.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scheduleMaker.model.Employee;
import scheduleMaker.model.Task;
import scheduleMaker.services.ScheduleMakerException;

import java.util.List;

public class ControllerRegularWindow {
    private ClientController service;
    private double xOffset;
    private double yOffset;
    private BorderPane mainLayout;
    private ControllerLoginWindow loginWindow;
    private Employee me;

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
                    service.logoutRegular(me.getUsername());
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
    public void setClientController (ClientController clientController, String username){

        this.service = clientController;
        service.setControllerRegularWindow(this);
        VBox vBox=new VBox();
        mainLayout.setCenter(vBox);

        List<Task> tasks = service.findAllTasks(username);
        for(Task t:tasks){
            HBox hBox=new HBox();
            CheckBox checkBox=new CheckBox();
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                        service.updateTask(t,"done");
                    }else{
                        service.updateTask(t,"pending");
                    }
                }
            });
            if(t.getStatus().equals("done")){
                checkBox.setSelected(true);
            }
            Label message=new Label();
            message.setText(t.getMessage());
            hBox.getChildren().add(message);
            hBox.getChildren().add(checkBox);
            vBox.getChildren().add(hBox);
        }
        me=service.getEmployeeByUsername(username);
    }
    public void addTask(Task t){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                VBox vBox=(VBox)mainLayout.getCenter();

                HBox hBox=new HBox();

                CheckBox checkBox=new CheckBox();
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if(newValue){
                            service.updateTask(t,"done");
                        }else{
                            service.updateTask(t,"pending");
                        }
                    }
                });
                if(t.getStatus().equals("done")){
                    checkBox.setSelected(true);
                }
                Label message=new Label();
                message.setText(t.getMessage());
                hBox.getChildren().add(message);
                hBox.getChildren().add(checkBox);
                vBox.getChildren().add(hBox);
            }
        });
    }

}
