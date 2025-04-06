package messenger.object;

public class Message {

    private String discord_id;
    private int id;
    private String titel;
    private String text;
    private String color;
    private String icon;
    private String link;
    private String link_text;


    public Message(String discord_id, int id, String titel, String text, String color, String icon) {
        this.discord_id = discord_id;
        this.id = id;
        this.titel = titel;
        this.text = text;
        this.color = color;
        this.icon = icon;
        this.link = null;
    }

    public Message(String discord_id, int id, String titel, String text, String color, String icon, String link, String link_text) {
        this.discord_id = discord_id;
        this.id = id;
        this.titel = titel;
        this.text = text;
        this.color = color;
        this.icon = icon;
        this.link = link;
        this.link_text = link_text;
    }

    public String getDiscord_id() {
        return discord_id;
    }

    public void setDiscord_id(String discord_id) {
        this.discord_id = discord_id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink_text() {
        return link_text;
    }

    public void setLink_text(String link_text) {
        this.link_text = link_text;
    }
}
