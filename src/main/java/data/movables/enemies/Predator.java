package data.movables.enemies;

import data.movables.Coords;

import java.io.Serializable;

public final class Predator extends Enemy implements Serializable {

    private static final long serialVersionUID = 6L;

    public Predator(Coords coords) {
        super(coords);
        setVisionRadius(3);
        setHP(50); //55
        setAttack(10);//12
        setDefense(6);//8
        setExpReward(250);
    }

    //+ some extra class specific skills to do
}
