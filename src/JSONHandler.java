import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONHandler  {

    public JSONObject getJSONObject(int sku) {
        JSONObject jsonObject = null;
        try {
            URL url = new URL("https://api.wegmans.io/products/" + sku + "?api-version=2018-10-18");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestProperty("Cache-Control", "no-cache");
            huc.setRequestProperty("Subscription-Key", "98ca8bd853ca4621acd6bde0008bfea9");

            huc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            jsonObject = new JSONObject(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void searchProduct(String query) {
        try {
            URL url = new URL("https://api.wegmans.io/products/search?query=" + query + "&results=100&page=1&api-version=2018-10-18");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestProperty("Cache-Control", "no-cache");
            huc.setRequestProperty("Subscription-Key", "98ca8bd853ca4621acd6bde0008bfea9");

            huc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray results = (JSONArray) jsonObject.get("results");
            JSONArray names = new JSONArray();
            JSONArray skus = new JSONArray();
            for (int i = 0; i < results.length(); i++) {
                names.put(((JSONObject) results.get(i)).get("name"));
                skus.put(((JSONObject) results.get(i)).get("sku"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void locateProduct(int sku) throws IOException {
        URL url = new URL("https://api.wegmans.io/products/" + sku + "/locations/25?api-version=2018-10-18\n"); // Pittsford store # is 25
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
    }
}
