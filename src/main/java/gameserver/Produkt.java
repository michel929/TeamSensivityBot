package gameserver;

public class Produkt {
    private int id;
    private String name;
    private String image;
    private int points;
    private String description;
    private long tag_id;
    private double price;


    public Produkt(int id, String name, String image, int points, String description, long tag_id, double price){
        this.id = id;
        this.name = name;
        this.image = image;
        this.points = points;
        this.description = description;
        this.tag_id = tag_id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTag_id() {
        return tag_id;
    }

    public void setTag_id(long tag_id) {
        this.tag_id = tag_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
