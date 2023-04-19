package games.lobby;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class SelectSave {
    public static ConcurrentHashMap<String, String> list;
    ArrayList<Lobby> lobby = new ArrayList<>();

    public SelectSave(){
        list = new ConcurrentHashMap<>();
    }

    public void addUserSelect(String id, String value){
        if(list.contains(id)){
            list.remove(id);
            list.put(id, value);
        }else {
            list.put(id, value);
        }
    }

    public boolean isInList(String id){
        if(list.contains(id)){
            return true;
        }else {
            return false;
        }
    }

    public String getValue(String id){
        return list.get(id);
    }

    public ArrayList<Lobby> getLobby() {
        return lobby;
    }
}
