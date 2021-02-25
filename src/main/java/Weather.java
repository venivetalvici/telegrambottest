import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
//4ab4424fdf2249697f40a2bb40d1bbda
    public static String getWeather(String message,Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=be4153f7cd303acddd48c8a94b850272");

        Scanner in = new Scanner((InputStream) url.getContent());
    String result = "";
    while (in.hasNext()){
        result += in.nextLine();
    }
        JSONObject object = new JSONObject(result);
    model.setName(object.getString("name"));

    JSONObject main = object.getJSONObject("main");
    model.setTemp(main.getDouble("temp"));
    model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for(int i=0;i<getArray.length(); i++){
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }

    return "Город: " + model.getName() + "\n" +
             "Температура сейчас:" + model.getTemp() + "\n" +
            "Влажность: " + model.getHumidity() + "%" + "\n" +
            "Main: " +model.getMain() + "\n" +
            " http://openweathermap.org/img/wn/" +model.getIcon()+ ".png";
    }

}
