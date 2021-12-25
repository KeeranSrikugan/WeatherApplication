import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class SecondController{
    private String cityName;
    private String apiKey;

    @FXML private Text tempText;
    @FXML private Text descriptionText;
    @FXML private Text cityText;
    @FXML private Text monthText;
    @FXML private Text dayText;
    @FXML private Text pressureText;
    @FXML private Text windText;
    @FXML private Text humidityText;
    @FXML private Text pressureNumberText;
    @FXML private Text windSpeedText;
    @FXML private Text humidityNumberText;
    @FXML private ImageView images;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Button button3;
    @FXML private AnchorPane background1;
    @FXML private Pane bottomHalf;
    @FXML private Pane bottomHalf1;
    @FXML private Pane bottomHalf2;
    @FXML private Pane upperHalf1;


    public void updateInfo(){
        button1.setVisible(false);
        button2.setVisible(true);
        button3.setVisible(true);
        this.cityName = Main.cityName;
        this.apiKey = Main.api;
        button1.cancelButtonProperty();
        accessAPI();
    }

    public void accessAPI(){
        try {
            String sURL = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;
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
            int currTempKelvin = tempInfo.getInt("temp");

            JSONArray cloudInformation = weatherData.getJSONArray("weather");
            JSONObject cloudInfo = cloudInformation.getJSONObject(0);
            String cloudDiscription = cloudInfo.getString("description");




            //This calculates and sets the temperature
            double currTempCelcius = Math.ceil(currTempKelvin - 273.15);
            int newTemp = (int)currTempCelcius;
            String newerTemp= String.valueOf(newTemp);
            char degree = 176;
            tempText.setText(newerTemp +degree+"C");

            //This sets the description of the weather
            descriptionText.setText(cloudDiscription);

            //This sets the city name
            cityText.setText(cityName);


            //Here I set the titles for the UI
            pressureText.setText("Pressure");
            windText.setText("Wind");
            humidityText.setText("Humidity");

            //This is where the pressure number is set
            String pressureNum = tempInfo.get("pressure").toString();
            pressureNumberText.setText(pressureNum + " hPa");

            //This is where the wind speed number is set
            JSONObject windInfo = weatherData.getJSONObject("wind");
            String windSpeed = windInfo.get("speed").toString();
            windSpeedText.setText(windSpeed + " m/s");

            //This is where the humidity number is set
            String humidityPercent = tempInfo.get("humidity").toString();
            humidityNumberText.setText(humidityPercent + "%");

            //This gets and sets the current date;
            LocalDate currentDate = LocalDate.now();
            int day = currentDate.getDayOfMonth();
            Month month = currentDate.getMonth();
            String finalMonth = month.toString().substring(0,Math.min(month.toString().length(), 3));

            monthText.setText(finalMonth);
            dayText.setText(String.valueOf(day));

            //Here I set the colors for the entire light mode for the weather application.
            //When boote up, it uses the css file to start with how it looks, then uses this when the light button is clicked
            background1.setStyle("-fx-background-color: LightCyan");
            bottomHalf.setStyle("-fx-background-color: DarkSlateGrey");
            bottomHalf1.setStyle("-fx-background-color: Teal");
            bottomHalf2.setStyle("-fx-background-color: Teal");
            upperHalf1.setStyle("-fx-background-color: Teal");
            tempText.setFill(Color.rgb(1,91,73));
            descriptionText.setFill(Color.rgb(1,91,73));
            cityText.setFill(Color.rgb(1,91,73));
            dayText.setFill(Color.WHITE);
            monthText.setFill(Color.WHITE);
            pressureText.setFill(Color.WHITE);
            windText.setFill(Color.WHITE);
            humidityText.setFill(Color.WHITE);
            pressureNumberText.setFill(Color.WHITE);
            windSpeedText.setFill(Color.WHITE);
            humidityNumberText.setFill(Color.WHITE);

            //Default image is set to clear skies
            Image myImage = new Image(getClass().getResourceAsStream("Images\\50d@4x.png"));

            //Bunch of if statements to change the image depending on what the weather description is
            if(cloudInfo.get("main").equals("Clear")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\01d@4x.png"));
            }
            if(cloudInfo.get("description").equals("few clouds")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\02d@4x.png"));
            }
            if((cloudInfo.get("description").equals("scattered clouds")) || (cloudInfo.get("description").equals("overcast clouds"))) {
                myImage = new Image(getClass().getResourceAsStream("Images\\03d@4x.png"));
            }
            if(cloudInfo.get("description").equals("broken clouds")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\04d@4x.png"));
            }
            if(cloudInfo.get("main").equals("Drizzle")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\09d@4x.png"));
            }
            if(cloudInfo.get("main").equals("Rain")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\10d@4x.png"));
            }
            if(cloudInfo.get("main").equals("Thunderstorm")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\11d@4x.png"));
            }
            if(cloudInfo.get("main").equals("Snow")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\13d@4x.png"));
            }


            //This is where the image is set
            images.setImage(myImage);

            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    background1.setStyle("-fx-background-color: #353635");
                    bottomHalf.setStyle("-fx-background-color: DimGrey");
                    bottomHalf1.setStyle("-fx-background-color: Grey");
                    bottomHalf2.setStyle("-fx-background-color: Grey");
                    upperHalf1.setStyle("-fx-background-color: Grey");
                    tempText.setFill(Color.GREY);
                    descriptionText.setFill(Color.GREY);
                    cityText.setFill(Color.GREY);
                    dayText.setFill(Color.rgb(53,54,53));
                    monthText.setFill(Color.rgb(53,54,53));
                    pressureText.setFill(Color.rgb(53,54,53));
                    windText.setFill(Color.rgb(53,54,53));
                    humidityText.setFill(Color.rgb(53,54,53));
                    pressureNumberText.setFill(Color.rgb(53,54,53));
                    windSpeedText.setFill(Color.rgb(53,54,53));
                    humidityNumberText.setFill(Color.rgb(53,54,53));


                    //Default image is set to clear skies
                    Image myImage2 = new Image(getClass().getResourceAsStream("Images\\50n@4x.png"));

                    //Bunch of if statements to change the image depending on what the weather description is
                    if(cloudInfo.get("main").equals("Clear")) {
                        myImage2 = new Image(getClass().getResourceAsStream("Images\\01n@4x.png"));
                    }
                    if(cloudInfo.get("description").equals("few clouds")) {
                        myImage2= new Image(getClass().getResourceAsStream("Images\\02n@4x.png"));
                    }
                    if((cloudInfo.get("description").equals("scattered clouds")) || (cloudInfo.get("description").equals("overcast clouds"))) {
                        myImage2 = new Image(getClass().getResourceAsStream("Images\\03n@4x.png"));
                    }
                    if(cloudInfo.get("description").equals("broken clouds")) {
                        myImage2 = new Image(getClass().getResourceAsStream("Images\\04n@4x.png"));
                    }
                    if(cloudInfo.get("main").equals("Drizzle")) {
                        myImage2 = new Image(getClass().getResourceAsStream("Images\\09n@4x.png"));
                    }
                    if(cloudInfo.get("main").equals("Rain")) {
                        myImage2 = new Image(getClass().getResourceAsStream("Images\\10n@4x.png"));
                    }
                    if(cloudInfo.get("main").equals("Thunderstorm")) {
                        myImage2 = new Image(getClass().getResourceAsStream("Images\\11n@4x.png"));
                    }
                    if(cloudInfo.get("main").equals("Snow")) {
                        myImage2 = new Image(getClass().getResourceAsStream("Images\\13n@4x.png"));
                    }
                   images.setImage(myImage2);
                }
            });


            //Closing input stream
            in.close();

        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
