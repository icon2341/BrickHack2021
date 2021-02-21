import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
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
                sb.append(line).append("\n");
            }
            br.close();

            ArrayList<String> listdata = new ArrayList<String>();
            JSONArray jsonArray = new JSONArray(sb.toString());

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject product = jsonArray.getJSONObject(i);
                listdata.add(product.optString("nr"));
            }

            System.out.println(Arrays.toString(listdata.toArray()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
