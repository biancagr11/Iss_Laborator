package scheduleMaker.client.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scheduleMaker.model.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ControllerScheduleScene{

    private ClientController service;
    private ObservableList<Employee> employeesModel= FXCollections.observableArrayList();

    @FXML
    TableView<Employee> employeeTableView;
    @FXML
    TableColumn<Employee,String> firstNameColumn;
    @FXML
    TableColumn<Employee,String> lastNameColumn;
    @FXML
    TableColumn<Employee,String> jobColumn;
    @FXML
    Button leftIcon;
    @FXML
    Button rightIcon;

    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        jobColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("job"));
        employeeTableView.setItems(employeesModel);

        firstNameColumn.getStyleClass().add("foo");
        lastNameColumn.getStyleClass().add("foo");
        jobColumn.getStyleClass().add("foo");

        leftIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean result=service.involveEmployee();
                if(result==false){
                    MessageBox.showWarningMessage("There are not other avoided employees!");
                }
                else
                {
                    employeesModel.setAll(service.allInvolvedEmployees());
                }
            }
        });
    }

    public void setService(ClientController service){
        this.service=service;
    }

    public void handleDoctorsButton() {
        employeesModel.setAll(service.allDoctors());
    }
    public void handleNursesButton() {
        employeesModel.setAll(service.allNurses());
    }
    public void handleOrderliesButton() {
        employeesModel.setAll(service.allOrderlies());
    }
    public void handleSelection(){
        Employee employee=employeeTableView.getSelectionModel().getSelectedItem();
        service.avoidEmployee(employee);
        employeesModel.setAll(service.allInvolvedEmployees());
    }
    public void handleScheduleButton() throws IOException{
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/graphicScene.fxml"));
        BorderPane root=loader.load();
        ControllerGraficScene controllerGraficScene =loader.getController();
        controllerGraficScene.setService(service);

        ScrollPane scheduleScrollPane=new ScrollPane();
        ScrollPane nameScrollPane=new ScrollPane();
        GridPane nameGridPane=new GridPane();
        GridPane scheduleGridPane=new GridPane();

        scheduleScrollPane.setContent(scheduleGridPane);
        nameScrollPane.setContent(nameGridPane);

        root.setLeft(nameScrollPane);
        root.setCenter(scheduleScrollPane);

        nameGridPane.setGridLinesVisible( true );
        nameGridPane.setVgap(8);
        nameGridPane.setHgap(8);

        nameGridPane.setPadding(new Insets(8, 8, 8, 8));


        nameGridPane.add(new Label("Names:"),0,0);
        List<Employee> involvedEmployees=service.allInvolvedEmployees();
        for(int i=0;i<involvedEmployees.size();i++){
            Label label=new Label();
            label.setText(involvedEmployees.get(i).toString());
            nameGridPane.add(label,0,i+1);
        }
        for(Integer i=1; i<=30;i++){
            Label label=new Label();
            label.setText(i.toString());
            scheduleGridPane.add(label,i,0);
        }
        scheduleGridPane.setPadding(new Insets(10, 10, 10, 10));
        for(int i=1; i<=30;i++){
            List<ComboBox<String>> list=new ArrayList<>();
            for(int j=1;j<=involvedEmployees.size();j++){
                ComboBox<String> comboBox=new ComboBox<String>();
                comboBox.setItems(FXCollections.observableArrayList("Z", "N", "L", "CO"));
                scheduleGridPane.add(comboBox,i,j);
                list.add(comboBox);
            }
        }

        Stage stage=new Stage();
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, 320, 450));
        stage.show();
    }

}
