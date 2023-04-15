package games.blackjack;

import java.util.concurrent.ConcurrentHashMap;

public class SelectSave {
    public static ConcurrentHashMap<String, String> list;

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

}
