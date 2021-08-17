import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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

    public TextField username;
    public TextField password;
    public ListView files;
    public Button copyButton;
    public Button clearButton;
    public Button deleteButton;
    public int copyNum = 0;
    public int fileDireNum = 0;

    public void loginButtonClicked(){
        System.out.println("Button Clicked");
        accessAPI();
    }


    public void accessAPI(){
        try {
            apiKey WeatherAPIKey = new apiKey();


            String apiKey = WeatherAPIKey.getAPIKey();
            String city = "Toronto";

            String sURL = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
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

            System.out.println(currTemp);
            System.out.println(feelsLike);













        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}