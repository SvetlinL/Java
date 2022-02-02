package spaceStation.models.astronauts;

import spaceStation.models.bags.Backpack;
import spaceStation.models.bags.Bag;

public abstract class BaseAstronaut implements Astronaut{
    private String name;
    private double oxygen;
    private Bag bag;

    protected BaseAstronaut(String name, double oxygen){
        this.setName(name);
        this.setOxygen(oxygen);
        this.bag = new Backpack();
    }

    private void setName(String name){
        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException("Astronaut name cannot be null or empty.");
        }
        this.name = name;
    }

    private void setOxygen(double oxygen){
        if (oxygen < 0){
            throw new IllegalArgumentException("Cannot create Astronaut with negative oxygen!");
        }
        this.oxygen = oxygen;
    }
    @Override
    public void breath(){
        this.oxygen -= breathValue();
        if (this.oxygen < 0){
            this.oxygen = 0;
        }
    }

    protected double breathValue(){
        return 10;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public double getOxygen() {
        return this.oxygen;
    }

    @Override
    public boolean canBreath() {
        return this.oxygen > 0;
    }

    @Override
    public Bag getBag() {
        return this.bag;
    }
}
