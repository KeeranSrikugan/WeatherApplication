import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {
    public static String cityName;
    public static String api;




    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }

    //This is a set function created so the city name can be shared between controller classes
    public static void setCity(String city) {
        cityName = city;
    }

    //This is a set function created so the api key can be shared between controller classes
    public static void setAPI(String apiNum) {
        api = apiNum;
    }


}
