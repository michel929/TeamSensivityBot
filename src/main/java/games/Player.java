package games;

import net.dv8tion.jda.api.entities.Member;

public class Player {

    private Member m;
    private int einsatz;

    public Player(Member m){
        this.m = m;
        einsatz = 0;
    }

    public Member getM() {
        return m;
    }

    public int getEinsatz() {
        return einsatz;
    }

    public void setEinsatz(int einsatz) {
        this.einsatz = einsatz;
    }
}
