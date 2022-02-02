package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;

public class GangNeighbourhood implements Neighbourhood {

    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        for (Gun gun : mainPlayer.getGunRepository().getModels()) {
            for (Player civilPlayer : civilPlayers) {
                while (civilPlayer.isAlive()) {
                    if (!gun.canFire()) {
                        break;
                    }
                    civilPlayer.takeLifePoints(gun.fire());
                }
            }
        }

        for (Player civilPlayer : civilPlayers) {
            for (Gun gun : civilPlayer.getGunRepository().getModels()) {
                while (gun.canFire()) {
                    if (!mainPlayer.isAlive()) {
                        break;
                    }
                    mainPlayer.takeLifePoints(gun.fire());
                }
            }
        }
    }
}
