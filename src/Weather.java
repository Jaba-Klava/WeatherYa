import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray; // добавлена библиотека json.org version 20231013
import org.json.JSONObject;

public class Weather {
    public static void main(String[] args) throws Exception {
        String accessKey = "4f9f0819-0c41-436f-acd5-87b4126d77e6";
        HttpURLConnection connection = (HttpURLConnection) new URL("https://api.weather.yandex.ru/v2/forecast?lat=52.37125&lon=4.89388").openConnection();
        connection.setRequestProperty("X-Yandex-Weather-Key", accessKey);
        Scanner scanner = new Scanner(connection.getInputStream());
        String json = scanner.useDelimiter("\\A").next();
        scanner.close();

        JSONObject jsonObject = new JSONObject(json);

        // Получение текущей температуры
        double currentTemperature = jsonObject.getJSONObject("fact").getDouble("temp");
        System.out.println("Текущая температура: " + currentTemperature + "°C");

        // Получаем массив forecasts и hours
        JSONArray forecasts = jsonObject.getJSONArray("forecasts");
        JSONArray hoursForecast = forecasts.getJSONObject(0).getJSONArray("hours");

        // Переменные для суммирования температуры за текущие сутки
        double totalTemperature = 0.0;
        int count = 0;

        // Перебор массива для получения суммы температур за сутки
        for (int i = 0; i < hoursForecast.length(); i++) {
            JSONObject hourData = hoursForecast.getJSONObject(i);
            double temperatureHour = hourData.getDouble("temp");
            totalTemperature += temperatureHour;
            count++;
        }

        // Вычисление среднего значения температуры за сутки
        if (count > 0) {
            double dailyAverageTemperature = totalTemperature / count;
            System.out.printf("Среднее значение температуры за текущие сутки: %.2f°C%n", dailyAverageTemperature);
        }
    }
}