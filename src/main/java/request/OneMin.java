package request;

import functions.GetInfos;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

public class OneMin extends TimerTask {
    @Override
    public void run() {
        //StatusPage Monitoring
        try {
            GetInfos.stream(new URL("https://monitor.sensivity.team/api/push/VgnoB3yJjV?status=up&msg=OK&ping="));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
