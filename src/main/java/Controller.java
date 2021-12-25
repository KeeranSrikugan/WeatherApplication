
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Controller {

    public TextField cityName;
    public TextField apiKey;
    public Text failLoginMessage;
    public ImageView images;

    @FXML
    public Text tempText;


    public Stage stage;
    private Scene scene;
    private Parent root;

    public boolean checkLink(){
        try {
            String sURL = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName.getText() + "&appid=" + apiKey.getText();
            URL networkURL = new URL(sURL);
            URLConnection conn = networkURL.openConnection();

            conn.setDoOutput(false);
            conn.setDoInput(true);
            InputStream in = conn.getInputStream();
            return true;
        }  catch (IOException e) {
            return false;
        }
    }

    public void switchToWeatherAppScene(ActionEvent event) throws IOException {

        if (checkLink() == true) {

            //This is where the city name and api key are sent to main to be accessed by the other controller file
            Main.setCity(cityName.getText());
            Main.setAPI(apiKey.getText());

            root = FXMLLoader.load(getClass().getResource("mainSample.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            failLoginMessage.setText("Incorrect Login Information");
        }
    }
}