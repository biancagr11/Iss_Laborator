package scheduleMaker.client.gui;


import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import scheduleMaker.model.Employee;

public class ControllerAddEmployeeWindow {

    private ClientController service;
    private double xOffset;
    private double yOffset;

    @FXML
    Button closeIcon;
    @FXML
    Button circleIcon;
    @FXML
    HBox barBox;

    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    ChoiceBox<String> jobChoiceBox;

    public void initialize(){
        closeIcon.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
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
        jobChoiceBox.setItems(FXCollections.observableArrayList("Asistent", "Medic", "Infirmier", "Ingrijitor", "Brancardier"));
    }

    public void setService(ClientController service){
        this.service=service;
    }

    public void handleAddButton() {
        String firstName=firstNameField.getText();
        String lastName=lastNameField.getText();
        String job=jobChoiceBox.getValue();
        String error="";
        if(firstName.equals("")){
            error+="\"First name\" can not be null!\n";
        }
        if(lastName.equals("")){
            error+="\"Last name\" can not be null!\n";
        }
        if(job==null){
            error+="\"Job\" can not be null!\n";
        }
        if(!error.equals("")){
            MessageBox.showErrorMessage(error);
        }
        else {
            Employee result = service.addEmployee(firstName, lastName, job);
            if (result == null) {
                MessageBox.showErrorMessage("Could not save this employee! Try again later!");
            } else {
                firstNameField.setText("");
                lastNameField.setText("");
                jobChoiceBox.setValue(null);
                MessageBox.showMessage("Employee saved!");
            }
        }
    }
}
