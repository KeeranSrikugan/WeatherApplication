import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public void updateInfo(){
        this.cityName = Main.cityName;
        this.apiKey = Main.api;
        System.out.println(cityName);
        System.out.println(apiKey);
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
            String currTemp = tempInfo.get("temp").toString();
            String feelsLike = tempInfo.get("feels_like").toString();
            int currTempKelvin = tempInfo.getInt("temp");

            JSONArray cloudInformation = weatherData.getJSONArray("weather");
            JSONObject cloudInfo = cloudInformation.getJSONObject(0);
            String cloudDiscription = cloudInfo.getString("description");


            //This calculates and sets the temperature
            double currTempCelcius = Math.ceil(currTempKelvin - 273.15);
            int newTemp = (int)currTempCelcius;
            String newerTemp= String.valueOf(newTemp);
            System.out.println(newerTemp);
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

            System.out.println(day + ", " + finalMonth);
            monthText.setText(finalMonth);
            dayText.setText(String.valueOf(day));

            //Default image is set to clear skies
            Image myImage = new Image(getClass().getResourceAsStream("Images\\01d@4x.png"));

            //Bunch of if statements to change the image depending on what the weather description is
            if(cloudInfo.get("description").equals("few clouds")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\02d@4x.png"));
            }
            if(cloudInfo.get("description").equals("scattered clouds")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\03d@4x.png"));
            }
            if(cloudInfo.get("description").equals("broken clouds")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\04d@4x.png"));
            }
            if(cloudInfo.get("description").equals("shower rain")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\09d@4x.png"));
            }
            if(cloudInfo.get("description").equals("rain")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\10d@4x.png"));
            }
            if(cloudInfo.get("description").equals("thunderstorm")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\11d@4x.png"));
            }
            if(cloudInfo.get("description").equals("snow")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\13d@4x.png"));
            }
            if(cloudInfo.get("description").equals("mist")) {
                myImage = new Image(getClass().getResourceAsStream("Images\\50d@4x.png"));
            }

            //This is where the image is set
            images.setImage(myImage);

            //Closing input stream
            in.close();

        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
