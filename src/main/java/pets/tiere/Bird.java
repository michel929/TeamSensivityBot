package pets.tiere;

import org.joda.time.DateTime;

public class Bird {

    private int type;
    private String name;
    private DateTime bday;
    private DateTime hunger;
    private DateTime durst;
    private int happy;

    public Bird(int type, String name, DateTime bday, int happy, DateTime hunger, DateTime durst){
        this.name = name;
        this.type = type;
        this.bday = bday;
        this.happy = happy;
        this.durst = durst;
        this.hunger = hunger;
    }

    public DateTime getBday() {
        return bday;
    }

    public int getHappy() {
        return happy;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public DateTime getDurst() {
        return durst;
    }

    public DateTime getHunger() {
        return hunger;
    }

    public void setHunger(DateTime hunger) {
        this.hunger = hunger;
    }

    public void setDurst(DateTime durst) {
        this.durst = durst;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }
}
