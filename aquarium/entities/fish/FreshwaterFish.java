package main.java.aquarium.entities.fish;

public class FreshwaterFish extends BaseFish{
    private static final int INITIAL_SIZE = 3;
    public FreshwaterFish(String name, String species, double price) {
        super(name, species, price);
        super.setSize(3);
    }

    @Override
    protected int sizeIncreaseValue(){
        return 3;
    }

}
