import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//JavaFX import
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            URL url = new URL("https://api.wegmans.io/products/11914?api-version=2018-10-18");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestProperty("Cache-Control", "no-cache");
            huc.setRequestProperty("Subscription-Key", "98ca8bd853ca4621acd6bde0008bfea9");

            huc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(sb.toString());
            System.out.println("Product ID: " + jsonObject.get("sku"));
            searchProduct("milk");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    // https://api.wegmans.io/products/search?query={query}[&results][&page]
    public static void searchProduct(String query) {
        try {
            URL url = new URL("https://api.wegmans.io/products/search?query=" + query + "&results=100&page=1&api-version=2018-10-18");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestProperty("Cache-Control", "no-cache");
            huc.setRequestProperty("Subscription-Key", "98ca8bd853ca4621acd6bde0008bfea9");

            huc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray results = (JSONArray) jsonObject.get("results");
            JSONArray names = new JSONArray();
            for(int i = 0; i < results.length(); i++) {
                names.put(((JSONObject) results.get(i)).get("name"));
            }
            System.out.println(names.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
