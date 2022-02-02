package CounterStriker.core;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.common.OutputMessages;
import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepository;
import CounterStriker.repositories.PlayerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private GunRepository guns;
    private PlayerRepository players;
    private Field field;

    public ControllerImpl() {
        this.guns = new GunRepository();
        this.players = new PlayerRepository();
        this.field = new FieldImpl();
    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        Gun gun = null;
        switch (type) {
            case "Pistol":
                gun = new Pistol(name, bulletsCount);
                break;
            case "Rifle":
                gun = new Rifle(name, bulletsCount);
                break;
        }
        if (gun == null) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_GUN_TYPE);
        }
        this.guns.add(gun);
        return String.format(OutputMessages.SUCCESSFULLY_ADDED_GUN, name);
    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        Player player = null;
        Gun gun = this.guns.findByName(gunName);
        if (gun == null) {
            throw new NullPointerException(ExceptionMessages.GUN_CANNOT_BE_FOUND);
        }
        switch (type) {
            case "Terrorist":
                player = new Terrorist(username, health, armor, gun);
                break;
            case "CounterTerrorist":
                player = new CounterTerrorist(username, health, armor, gun);
                break;
        }
        if (player == null) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_TYPE);
        }
        this.players.add(player);
        return String.format(OutputMessages.SUCCESSFULLY_ADDED_PLAYER, username);
    }

    @Override
    public String startGame() {
        return this.field.start(players.getModels());
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Player> ct = (ArrayList<Player>) this.players.getModels().stream().filter(e->e.getClass().getSimpleName().equals("CounterTerrorist"))
                .collect(Collectors.toList());
        ArrayList<Player> t = (ArrayList<Player>) this.players.getModels().stream().filter(e->e.getClass().getSimpleName().equals("Terrorist"))
                .collect(Collectors.toList());
        Comparator<Player> compareByHealthAndName = Comparator.comparing(Player::getHealth).reversed()
                .thenComparing(Player::getUsername);

        Collections.sort(ct, compareByHealthAndName);
        Collections.sort(t, compareByHealthAndName);

        ct.forEach(e-> sb.append(e.toString()).append(System.lineSeparator()));
        t.forEach(e-> sb.append(e.toString()).append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
