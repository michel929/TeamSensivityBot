package functions;

import java.io.*;
import java.net.URL;

public class GetInfos {

    public static int stream(URL url) {
        int i = 0;
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            i = Integer.parseInt(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return i;
    }

    public static void streamBOT(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPoints(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;

           while ((c = reader.read()) != -1) {
                json.append((char) c);
            }

           return json.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
