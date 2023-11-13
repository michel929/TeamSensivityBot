package hosting;

import geheim.Hosting;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Funktionen {
    public static void deleteUser(int id) throws IOException {
        URL url = null;
        try {
            url = new URL("http://192.168.178.202/api/application/users/" + id);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.setRequestProperty("Authorization", "Bearer " + Hosting.HostingToken);
            httpCon.setRequestMethod("DELETE");
            httpCon.connect();

        } catch (MalformedURLException | ProtocolException e) {
            throw new RuntimeException(e);
        }
    }

    public static int userExist(String id){
        URL url = null;
        try {
            url = new URL("http://192.168.178.202/api/application/users/external/" + id);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.setRequestProperty("Authorization", "Bearer " + Hosting.HostingToken);
            httpCon.setRequestMethod("GET");
            httpCon.connect();

            BufferedReader br = null;
            StringBuilder body = null;
            String line = "";
                br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                body = new StringBuilder();
                while ((line = br.readLine()) != null)
                    body.append(line);


                JSONParser parse = new JSONParser();
                JSONObject object = (JSONObject) parse.parse(body.toString());

                JSONObject object1 = (JSONObject) object.get("attributes");

                int hosting_id = Integer.parseInt(object1.get("id").toString());

                return hosting_id;

        } catch (ParseException | IOException e) {
            return 0;
        }
    }
}
