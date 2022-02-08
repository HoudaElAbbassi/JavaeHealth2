package utilities.distanceOfSearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

/**
 * A utility class to get the coordinates of an address using the open website OpenStreetMap
 * @Author Ahmed Agdmoun
 */
public class MapUtils {
    public final static Logger log = Logger.getLogger("MapUtils"); // creates an instance of the class Logger to retrieve a logger of the class or create a  new one if iz doesn't exist
    private static MapUtils instance = null; // creates a new instance of the class and initialise it with null
    private JSONParser jsonParser; // creates a new Json parser

    /** creates the object of the class with a json parser object */
    public MapUtils() {
        jsonParser = new JSONParser();
    }

    /**
     * ensures that only one instance of this class will be created
     * if no instance exists create a new one else return the existing one
     * @return the single instance of the class
     */
    public static MapUtils getInstance() {
        if (instance == null) {
            instance = new MapUtils();
        }
        return instance;
    }

    /**
     * this method sends a get request to get data from a certain source
     * @param url specifies the source to get the data from
     * @return the response of the server on the get request
     * @throws Exception in case the connection to the server fails
     */
    private String getRequest(String url) throws Exception {

        final URL obj = new URL(url);
        final HttpURLConnection con = (HttpURLConnection) obj.openConnection(); // establishes a http connection

        con.setRequestMethod("GET"); // sets the GET request

        if (con.getResponseCode() != 200) {
            return null;
        }

        // instantiates a buffer reader to read the result from the GET request
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    /**
     * gets the latitude and the longitude of an address
     * @param address represents the address to look up in OpenStreetMap
     * @return a map of latitude and longitude and their values
     */
    public Map<String, Double> getCoordinates(String address) {
        Properties prop = new Properties();
        prop.setProperty("log4j.rootLogger", "WARN");
        PropertyConfigurator.configure(prop);
        Map<String, Double> res;
        StringBuffer query;
        String[] split = address.split(" ");
        String queryResult = null;

        query = new StringBuffer();
        res = new HashMap<String, Double>();

        query.append("https://nominatim.openstreetmap.org/search?q=");

        if (split.length == 0) {
            return null;
        }

        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1"); // to get the address details in json format

        log.debug("Query:" + query);

        try {
            queryResult = getRequest(query.toString());
        } catch (Exception e) {
            log.error("Error when trying to get data with the following query " + query);
        }

        if (queryResult == null) {
            return null;
        }

        Object obj = JSONValue.parse(queryResult);
        log.debug("obj=" + obj);

        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) obj;
            if (array.size() > 0) {
                JSONObject jsonObject = (JSONObject) array.get(0);

                String lon = (String) jsonObject.get("lon");
                String lat = (String) jsonObject.get("lat");
                log.debug("lon=" + lon);
                log.debug("lat=" + lat);
                res.put("lon", Double.parseDouble(lon));
                res.put("lat", Double.parseDouble(lat));
            }
        }
        return res;
    }
}
