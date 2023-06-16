package pets.tiere;

import main.Main;
import org.joda.time.DateTime;

public class Tier {

    private String name;
    private Animal type;
    private DateTime bday;
    private DateTime hunger;
    private DateTime durst;
    private int happy;
    private int durstheute;
    private int hungerheute;
    private int level;
    private String discord_id;
    private int points;
    private int amount_drink;
    private int amount_food;

    public Tier(String name, int id, String bday, String hunger, String durst, int happy, int level, String discord_id, int durstheute, int hungerheute, int points, int amount_drink, int amount_food){
        this.name = name;
        type = Main.INSTANCE.getPets().getAnimalByID(id);
        this.bday = DateTime.parse(bday.replace(" ", "T"));
        this.hunger = DateTime.parse(hunger.replace(" ", "T"));
        this.durst = DateTime.parse(durst.replace(" ", "T"));
        this.happy = happy;
        this.level = level;
        this.discord_id = discord_id;
        this.durstheute = durstheute;
        this.hungerheute = hungerheute;
        this.points = points;
        this.amount_drink = amount_drink;
        this.amount_food = amount_food;
    }

    public DateTime getHunger() {
        return hunger;
    }

    public DateTime getDurst() {
        return durst;
    }

    public DateTime getBday() {
        return bday;
    }

    public String getName() {
        return name;
    }

    public int getHappy() {
        return happy;
    }

    public Animal getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public String getDiscord_id() {
        return discord_id;
    }

    public int getDurstheute() {
        return durstheute;
    }

    public int getHungerheute() {
        return hungerheute;
    }

    public int getPoints() {
        return points;
    }

    public int getAmount_drink() {
        return amount_drink;
    }

    public int getAmount_food() {
        return amount_food;
    }
}
