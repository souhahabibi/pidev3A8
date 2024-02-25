package models;

import java.util.List;

public class Reservation {
    private int id,fk_client_id,fk_competition_id,score;

    public Reservation(int a,int b,int c,int d)
    {
        id=a;
        fk_client_id=b;
        fk_competition_id=c;
        score=d;
    }
    public Reservation(int b,int c,int d)
    {
        fk_client_id=b;
        fk_competition_id=c;
        score=d;
    }
    public Reservation(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_client_id() {
        return fk_client_id;
    }

    public void setFk_client_id(int fk_client_id) {
        this.fk_client_id = fk_client_id;
    }

    public int getFk_competition_id() {
        return fk_competition_id;
    }

    public void setFk_competition_id(int fk_competition_id) {
        this.fk_competition_id = fk_competition_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "fk_client_id=" + fk_client_id +
                ", fk_competition_id=" + fk_competition_id +
                ", score=" + score +
                '}';
    }
}
