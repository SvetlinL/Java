package CounterStriker.models.field;

import CounterStriker.common.OutputMessages;
import CounterStriker.models.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FieldImpl implements Field{

    @Override
    public String start(Collection<Player> players) {
        List<Player> terrorists = players.stream().filter(player -> player.getClass()
                .getSimpleName().equals("Terrorist")).collect(Collectors.toList());
        List<Player> counterTerrorists = players.stream().filter(player -> player.getClass()
                .getSimpleName().equals("CounterTerrorist")).collect(Collectors.toList());

        while (terrorists.stream().mapToInt(Player::getHealth).sum()>0 &&
                counterTerrorists.stream().mapToInt(Player::getHealth).sum()>0){
            for (Player counterTerrorist : counterTerrorists) {
                if (counterTerrorist.isAlive()){
                    for (Player terrorist : terrorists) {
                        if (terrorist.isAlive() && counterTerrorist.isAlive()) {
                            counterTerrorist.takeDamage(terrorist.getGun().fire());
                        }
                    }
                }
            }
            for (Player terrorist : terrorists) {
                if (terrorist.isAlive()){
                    for (Player counterTerrorist : counterTerrorists) {
                        if (counterTerrorist.isAlive() && terrorist.isAlive()){
                            terrorist.takeDamage(counterTerrorist.getGun().fire());
                        }
                    }
                }
            }
        }
        if(terrorists.stream().mapToInt(Player::getHealth).sum() > counterTerrorists.stream().mapToInt(Player::getHealth).sum()){
            return OutputMessages.TERRORIST_WINS;
        }else {
            return OutputMessages.COUNTER_TERRORIST_WINS;
        }
    }
}
