package viceCity.core.interfaces;

import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;
import viceCity.repositories.interfaces.GunRepository;
import viceCity.repositories.interfaces.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerImpl implements Controller{
    private List<Player> civilPlayers;
    private Player mainPlayer;
    private Deque<Gun> gunsDeque;
    private Neighbourhood neighbourhood;

    public ControllerImpl(){
        this.civilPlayers = new ArrayList<>();
        this.mainPlayer = new MainPlayer();
        this.gunsDeque = new ArrayDeque<>();
        this.neighbourhood = new GangNeighbourhood();
    }

    @Override
    public String addPlayer(String name) {
        Player player = new CivilPlayer(name);
        this.civilPlayers.add(player);
        return String.format("Successfully added civil player: %s!",name);
    }

    @Override
    public String addGun(String type, String name) {
        String message;
        Gun gun = null;
        switch (type){
            case "Pistol":
               gun = new Pistol(name);
               message = String.format("Successfully added %s of type: %s",name,type);
                this.gunsDeque.add(gun);
               break;
            case "Rifle":
                gun = new Rifle(name);
                message = String.format("Successfully added %s of type: %s",name,type);
                this.gunsDeque.add(gun);
                break;
            default:
                message = "Invalid gun type!";
        }
        return message;
    }

    @Override
    public String addGunToPlayer(String name) {
        String message;
        if (gunsDeque.isEmpty()){
            message = "There are no guns in the queue!";
            return message;
        }
        if (name.equals("Vercetti")){
            Gun gun = gunsDeque.poll();
            mainPlayer.getGunRepository().getModels().add(gun);
            String gunName = gun.getName();
            message = String.format("Successfully added %s to the Main Player: Tommy Vercetti",gunName);
        }else {
            message = "Civil player with that name doesn't exists!";
            for (Player civilPlayer : civilPlayers) {
                if (civilPlayer.getName().equals(name)){
                    Gun gun = gunsDeque.poll();
                    civilPlayer.getGunRepository().getModels().add(gun);
                    message = String.format("Successfully added %s to the Civil Player: %s",gun.getName(),name);
                }
            }
        }
        return message;
    }

    @Override
    public String fight() {
        StringBuilder sb = new StringBuilder();
        neighbourhood.action(mainPlayer, civilPlayers);
        int killedCivs = 0;
        boolean civHarmed = false;

        for (Player civilPlayer : civilPlayers) {
            if (!civilPlayer.isAlive()){
                killedCivs++;
            }
            if (civilPlayer.getLifePoints() != 50){
                civHarmed = true;
            }
        }
        if (mainPlayer.getLifePoints()==100 && !civHarmed && killedCivs == 0){
            sb.append("Everything is okay!");
        }else {
            sb.append("A fight happened:").append(System.lineSeparator());
            sb.append(String.format("Tommy live points: %d!",mainPlayer.getLifePoints())).append(System.lineSeparator());
            sb.append(String.format("Tommy has killed: %d players!",killedCivs)).append(System.lineSeparator());
            civilPlayers = civilPlayers.stream().filter(e -> !(e.getLifePoints() == 0)).collect(Collectors.toList());
            sb.append(String.format("Left Civil Players: %d!",civilPlayers.size()));
        }
        return sb.toString().trim();
    }
}
