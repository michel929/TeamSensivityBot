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
    private int level;

    public Tier(String name, int id, String bday, String hunger, String durst, int happy, int level){
        this.name = name;
        type = Main.INSTANCE.getPets().getAnimalByID(id);
        this.bday = DateTime.parse(bday);
        this.hunger = DateTime.parse(hunger);
        this.durst = DateTime.parse(durst);
        this.happy = happy;
        this.level = level;
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
}
