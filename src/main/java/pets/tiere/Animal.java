package pets.tiere;

public class Animal {
    private String url;
    private int points;
    private String name;
    private int id;
    public Animal(String url, int points, String name, int id){
        this.id = id;
        this.name = name;
        this.url = url;
        this.points = points;
    }

    public String getName() {
        return name;
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
