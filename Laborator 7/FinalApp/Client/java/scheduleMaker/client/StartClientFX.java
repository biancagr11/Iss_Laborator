package scheduleMaker.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import scheduleMaker.client.gui.ClientController;
import scheduleMaker.client.gui.ControllerLoginWindow;
import scheduleMaker.services.IScheduleMakerServices;

public class StartClientFX extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    public void start(Stage primaryStage) throws Exception {

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IScheduleMakerServices server=(IScheduleMakerServices)factory.getBean("scheduleMakerServicesServer");
        System.out.println("Obtained a reference to remote chat server");
        ClientController ctrl=new ClientController(server);

        try {
            //login window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginWindow.fxml"));
            Parent root = loader.load();
            ControllerLoginWindow loginController = loader.getController();

            loginController.setClientController(ctrl);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();

        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
