package hosting;

import geheim.Hosting;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class Funktionen {
    public static void deleteUser(int id) throws IOException {
        URL url = null;
        try {
            url = new URL("http://10.10.3.2/api/application/users/" + id);
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
            url = new URL("http://10.10.3.2/api/application/users/external/" + id);
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

    public static ArrayList<SelectOption> getServer(String id){
        ArrayList<SelectOption> arrayList = new ArrayList<>();

        URL url = null;
        try {
            url = new URL("http://10.10.3.2/api/application/servers");
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

            JSONArray array = (JSONArray) object.get("data");

            for (int i = 0; i < array.size(); i++) {
                JSONObject object1 = (JSONObject) array.get(i);
                JSONObject object2 = (JSONObject) object1.get("attributes");

                if(object2.get("user").toString().equals(id)){
                    SelectOption option = SelectOption.of(object2.get("name").toString(), object2.get("uuid").toString());
                    arrayList.add(option);
                }
            }

            return arrayList;

        } catch (ParseException | IOException e) {
            return arrayList;
        }
    }
}
