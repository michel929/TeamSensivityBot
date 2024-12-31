package request;

import java.util.TimerTask;

public class TwentySec extends TimerTask {

    @Override
    public void run() {
        //Status
        Api.getAPI("http://10.10.1.2:3001/api/push/HKZLfIWfU7uH1ctW1dBXcBAIpiOa9fzG?status=up&msg=OK&ping=");
    }
}
