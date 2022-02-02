package CounterStriker.models.players;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.guns.Gun;

public abstract class PlayerImpl implements Player{
    private String username;
    private int health;
    private int armor;
    private boolean isAlive;
    private Gun gun;

    protected PlayerImpl(String username, int health, int armor, Gun gun) {
        this.setUsername(username);
        this.setHealth(health);
        this.setArmor(armor);
        this.setGun(gun);
    }
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.INVALID_PLAYER_NAME);
        }
            this.username = username;
    }

    public void setHealth(int health) {
        if (health < 0){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_HEALTH);
        }
        this.health = health;
    }

    public void setArmor(int armor) {
        if (armor < 0){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_ARMOR);
        }
        this.armor = armor;
    }

    public void setGun(Gun gun) {
        if (gun == null){
            throw new NullPointerException(ExceptionMessages.INVALID_GUN);
        }
        this.gun = gun;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getArmor() {
        return this.armor;
    }

    @Override
    public Gun getGun() {
        return this.gun;
    }

    @Override
    public boolean isAlive() {
        return getHealth() > 0;
    }

    @Override
    public void takeDamage(int points) {
        int damage = points;
        if (damage <= getArmor()){
            setArmor(getArmor()-damage);
        }else {
            damage -= getArmor();
            setArmor(0);
            if (getHealth() < damage){
                setHealth(0);
            }else {
                setHealth(getHealth() - damage);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s: %s",getClass().getSimpleName(),this.username)).append(System.lineSeparator());
        sb.append(String.format("--Health: %d",getHealth())).append(System.lineSeparator());
        sb.append(String.format("--Armor: %d",getArmor())).append(System.lineSeparator());
        sb.append(String.format("--Gun: %s",getGun().getName())).append(System.lineSeparator());

        return sb.toString().trim();
    }
}
