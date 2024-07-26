package functions;

import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GetGameRoles {
    private ConcurrentHashMap<String, Role> roles = new ConcurrentHashMap<>();

    public GetGameRoles(List<Role> grole){
        for (Role r: grole) {
            if(r.getColorRaw() == 11027200){
                roles.put(r.getName(), r);
            }
        }
    }

    public ConcurrentHashMap<String, Role> getRoles() {
        return roles;
    }
}
