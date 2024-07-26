package request;

import java.util.TimerTask;

public class TwentySec extends TimerTask {
    @Override
    public void run() {
        //Status
        Api.getAPI("https://status.sensivity.team/api/push/bFsXUiNZZ4?status=up&msg=OK&ping=");
    }
}
