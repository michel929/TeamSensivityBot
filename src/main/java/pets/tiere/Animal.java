package pets.tiere;

public class Animal {
    private String url;
    private int points;
    private String name;
    private int id;
    private int pos;
    public Animal(String url, int points, String name, int id, int pos){
        this.id = id;
        this.name = name;
        this.url = url;
        this.points = points;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public int getPos() {
        return pos;
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public String getUrl() {
        return url;
    }
}
