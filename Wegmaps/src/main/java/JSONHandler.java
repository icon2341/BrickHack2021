import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class JSONHandler {

    public JSONObject getProductFromSku(int sku) {
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

    public HashMap<String, Integer> searchProduct(String query) {
        HashMap<String, Integer> results = null;
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
            JSONArray jsonArray = (JSONArray) jsonObject.get("results");
            results = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = ((JSONObject) jsonArray.get(i));
                results.put((String) object.get("name"), (Integer) object.get("sku"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
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
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        JSONObject product = new JSONObject(sb.toString());
        JSONObject info = new JSONObject(product.get("locations"));
        //System.out.println(info.toString());
    }
}
