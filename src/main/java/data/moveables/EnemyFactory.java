package data.moveables;

import data.moveables.enemies.Enemy;
import data.moveables.enemies.Lizard;

import java.util.logging.Logger;

public class EnemyFactory {

    private Logger log = Logger.getLogger(this.toString());

    public Enemy buildEnemy(int race) {

        log.info("Building new Enemy...");

        switch (race) {
            case (1):
                return new Lizard(new Coords(0,0));
            default:
                log.warning("Wrong enemy type");
                return new Lizard(new Coords(0,0));
        }
    }
}
