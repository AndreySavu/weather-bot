package com.example.weather_bot.service;

import java.io.IOException;
import java.net.URLEncoder;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.myrobotlab.framework.ServiceType;
import org.myrobotlab.logging.LoggerFactory;
import org.slf4j.Logger;

public class WeatherService extends HttpClient {

    private static final long serialVersionUID = 1L;
    private String apiBase = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String apiForecast = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private String units = "imperial"; // metric
    private String lang = "en";
    private String apiKey = "9e5f7afb5b8ce9fbdb4f54afcc335230";
    //public final static Logger log = LoggerFactory.getLogger(OpenWeatherMap.class);

    public OpenWeatherMap(String reservedKey) {
        super(reservedKey);
    }

    /**
     * Return a sentence describing the weather
     */
    private JSONObject fetch(String location) throws ClientProtocolException, IOException, JSONException {
        String apiUrl = apiBase + URLEncoder.encode(location, "utf-8") + "&appid=" + apiKey + "&mode=json&units=" + units + "&lang=" + lang;
        String response = this.get(apiUrl);
        JSONObject obj = new JSONObject(response);
        return obj;
    }

    private JSONObject fetch(String location, int nbDay) throws ClientProtocolException, IOException, JSONException {
        String apiUrl = apiForecast + URLEncoder.encode(location, "utf-8") + "&appid=" + apiKey + "&mode=json&units=" + units + "&lang=" + lang + "&cnt=" + nbDay;
        String response = this.get(apiUrl);
        //log.debug("Respnse: {}", response);
        JSONObject obj = new JSONObject(response);
        return obj;
    }

    public String[] fetchCurrentWeather(String location) throws ClientProtocolException, IOException, JSONException {
        String[] result = new String[10];
        result[0] = "error";
        try {
            JSONObject obj = fetch(location);
            result[0] = obj.getJSONArray("weather").getJSONObject(0).get("description").toString();
            result[1] = obj.getJSONObject("main").get("temp").toString();
            result[2] = location;
            result[3] = obj.getJSONArray("weather").getJSONObject(0).get("id").toString();
            result[4] = obj.getJSONObject("main").get("pressure").toString();
            result[5] = obj.getJSONObject("main").get("humidity").toString();
            result[6] = obj.getJSONObject("main").get("temp_min").toString();
            result[7] = obj.getJSONObject("main").get("temp_max").toString();
            result[8] = obj.getJSONObject("wind").get("speed").toString();
            result[9] = obj.getJSONObject("wind").get("deg").toString();
            result[9] = obj.getJSONObject("clouds").get("all").toString();
            return result;
        } catch (Exception e) {
            //log.error("openweathermap error ( api ? ) : ", e);
            return result;
        }
    }

    public String[] fetchForecast(String location, int dayIndex) throws ClientProtocolException, IOException, JSONException {
        String[] result = new String[11];
        String localUnits = "fahrenheit";
        if (units.equals("metric")) {
            // for metric, celsius
            localUnits = "celsius";
        }
        if ((dayIndex >= 1) && (dayIndex <= 40)) {
            JSONObject jsonObj = null;
            try {
                jsonObj = fetch(location, (dayIndex));
            } catch (IOException | JSONException e) {
                error("OpenWeatherMap : fetch error",e);
                return null;
            }
            //log.info(jsonObj.toString());
            // Getting the list node
            JSONArray list;
            try {
                list = jsonObj.getJSONArray("list");
            } catch (JSONException e) {
                error("OpenWeatherMap : API key or the city is not recognized",e);
                return null;
            }
            // Getting the required element from list by dayIndex
            JSONObject item = list.getJSONObject(dayIndex - 1);
            result[0] = item.getJSONArray("weather").getJSONObject(0).get("description").toString();
            JSONObject main = item.getJSONObject("main");
            result[1] = main.get("temp").toString();
            result[2] = location;
            result[3] = item.getJSONArray("weather").getJSONObject(0).get("id").toString();
            result[4] = main.get("pressure").toString();
            result[5] = main.get("humidity").toString();
            result[6] = main.get("temp_min").toString();
            result[7] = main.get("temp_max").toString();
            JSONObject wind = item.getJSONObject("wind");
            result[8] = wind.get("speed").toString();
            result[9] = wind.get("deg").toString();
            result[10] = localUnits;
        } else {
            error("OpenWeatherMap : Index is out of range");
            return null;
        }
        return result;
    }

    @Deprecated
    public String fetchWeather(String location) throws ClientProtocolException, IOException, JSONException {
        return fetchForecast(location,0)[0];
    }

    public String getApiBase() {
        return apiBase;
    }

    public String getApiForecast() {
        return apiForecast;
    }

    /**
     * @param apiBase
     *          The base url that is assocaited with the open weather map api.
     */
    public void setApiBase(String apiBase) {
        this.apiBase = apiBase;
    }

    public void setApiForecast(String apiBase) {
        this.apiForecast = apiForecast;
    }

    public String getUnits() {
        return units;
    }

    /**
     * @param units
     *          The units, can be either imperial or metric.
     */
    public void setUnits(String units) {
        this.units = units;
    }

    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey
     *          REQUIRED: specify your API key with this method.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public static void main(String[] args) {

        OpenWeatherMap owm = new OpenWeatherMap("weather");
        owm.setApiKey("KEY!!");
        owm.startService();
        try {
            String[] fetchForecast = owm.fetchForecast("Boston", 0);
            String sentence = "("+fetchForecast[3]+") In " + fetchForecast[2] + " the weather is " + fetchForecast[0] + ".  " + fetchForecast[1] + " degrees " + fetchForecast[10];
            log.info(sentence);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
