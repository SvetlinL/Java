package main.java.aquarium.entities.fish;

public class SaltwaterFish extends BaseFish{
    public SaltwaterFish(String name, String species, double price) {
        super(name, species, price);
        super.setSize(5);
    }


    @Override
    protected int sizeIncreaseValue(){
        return 2;
    }
}
