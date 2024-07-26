package SchachAPI;

public class EloEntity {
    private long elo;
    private String name;

    public EloEntity(long elo, String name) {
        this.elo = elo;
        this.name = name;
    }

    public long getElo() {
        return elo;
    }

    public String getName() {
        return name;
    }

    public void setElo(long elo) {
        this.elo = elo;
    }

    public void setName(String name) {
        this.name = name;
    }
}
