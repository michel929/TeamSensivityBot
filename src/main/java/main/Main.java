package main;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static Start INSTANCE;
    public static String DATABASE;
    public static String GUILD_ID;

    public static void main(String[] args) {
        shutdown();
        System.out.println("Warte auf Anweisung:");
        System.out.println("Normale Bot wird gestartet!");
        DATABASE = "TeamSensivity";
        GUILD_ID = "773995277840941067";
        try {
            INSTANCE = new Start(false);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutdown(){
        new Thread(() -> {
            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                while ((line = reader.readLine()) != null){
                    if(line.equalsIgnoreCase("start")){
                        try {
                            if(INSTANCE == null) {
                                System.out.println("Normale Bot wird gestartet!");
                                DATABASE = "TeamSensivity";
                                GUILD_ID = "773995277840941067";
                                INSTANCE = new Start(false);
                            }
                        } catch (LoginException e) {
                            e.printStackTrace();
                        }
                    }else if (line.equalsIgnoreCase("demo")){
                        try {
                            if(INSTANCE == null) {
                                System.out.println("Demo Bot wird gestartet");
                                DATABASE = "DemoTeamSensivity";
                                GUILD_ID = "1072494676579463228";
                                INSTANCE = new Start(true);
                            }
                        } catch (LoginException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }
}
