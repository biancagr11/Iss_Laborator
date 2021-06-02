package scheduleMaker.client.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import scheduleMaker.model.Employee;
import scheduleMaker.model.Task;

import java.util.List;

public class ControllerMessageScene {

    private ClientController service;
    private ObservableList<Employee> employeesModel= FXCollections.observableArrayList();
    private ObservableList<String> messageModel= FXCollections.observableArrayList();
    private String to;
    private Employee me;
    @FXML
    TableView<Employee> employeeTableView;
    @FXML
    TableColumn<Employee,String> lastNameColumn;
    @FXML
    TextArea messageBox;
    @FXML
    ListView<String> messageList;

    public void initialize() {
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        employeeTableView.setItems(employeesModel);
        lastNameColumn.getStyleClass().add("foo");
        messageList.setItems(messageModel);
        employeeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                messageModel.clear();
                to= employeeTableView.getSelectionModel().getSelectedItem().getUsername();
                List<Task> tasks=service.findAllTasks(to);
                System.out.println(to);
                for(Task t:tasks){
                    messageModel.add(t.toString());
                }
            }
        });
    }
    public void loadList(){
        List<Employee> employeeList=service.findAllEmployees();
        employeesModel.setAll(employeeList);
    }

    public void handleSend(){
        String message=messageBox.getText();
        System.out.println(message);
        try
        {
            service.addTask(message,"pending",to);
        }catch (Exception ex){
            MessageBox.showErrorMessage(ex.getMessage());
        }
        messageBox.setText("");
    }

    public void setService(ClientController service, String username) {
        this.service = service;
        employeesModel.setAll(service.findAllEmployees());
        me=service.getEmployeeByUsername(username);
    }
}
