package games.lobby;

import games.Player;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;

public class Lobby {
    private String id;
    private String game;
    private boolean start;
    private Message message;
    private String host;
    private int minPlayer;
    private TextChannel channel;
    private ArrayList<Player> player;

    public Lobby (String id, String game, String host){
        this.game = game;
        this.id = id;
        start = false;
        message = null;
        this.host = host;
        player = new ArrayList<>();
        channel = null;

        if(game.equals("jack")){
            minPlayer = 1;
        }
    }

    public String getGame() {
        return game;
    }

    public String getId() {
        return id;
    }

    public Message getMessageId() {
        return message;
    }

    public boolean isStart() {
        return start;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setMessageId(Message message) {
        this.message = message;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public ArrayList<Player> getPlayer() {
        return player;
    }
    public void removePlayer(Member m){
        for (Player p: player) {
            if(p.getM() == m){
                player.remove(p);
            }
        }
    }
    public void addPlayer(Member m){
        player.add(new Player(m));
    }
    public int getMinPlayer() {
        return minPlayer;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public void setChannel(TextChannel channel) {
        this.channel = channel;
    }
}
