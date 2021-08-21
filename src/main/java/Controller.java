import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Controller {

    public TextField cityName;
    public TextField apiKey;

    public Stage stage;
    private Scene scene;
    private Parent root;



    public void switchToWeatherAppScene(ActionEvent event) throws IOException {

        String finalApiKey =apiKey.getText() ;
        String city = cityName.getText();

        accessAPI();

        root = FXMLLoader.load(getClass().getResource("mainSample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void accessAPI(){
        try {
            String sURL = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName.getText() + "&appid=" + apiKey.getText();
            URL networkURL = new URL(sURL);
            URLConnection conn = networkURL.openConnection();

            conn.setDoOutput(false);
            conn.setDoInput(true);
            InputStream in = conn.getInputStream();

            BufferedReader input = new BufferedReader(new InputStreamReader(in));

            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = input.readLine()) != null){
                buffer.append(line);
            }

            String jsonData = buffer.toString();
            JSONObject weatherData = new JSONObject(jsonData);
            JSONObject tempInfo = weatherData.getJSONObject("main");
            String currTemp = tempInfo.get("temp").toString();
            String feelsLike = tempInfo.get("feels_like").toString();

            JSONArray cloudInformation = weatherData.getJSONArray("weather");
            JSONObject cloudInfo = cloudInformation.getJSONObject(0);
            String cloudDiscription = cloudInfo.getString("description");



            System.out.println("Current temp is: " + currTemp);
            System.out.println("Feels like: " + feelsLike);
            System.out.println("Current clouds: " + cloudDiscription);




            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}