package spaceStation.models.astronauts;


public class Biologist extends BaseAstronaut {
    private static final double STARTING_OXYGEN = 70;

    public Biologist(String name) {
        super(name, STARTING_OXYGEN);
    }

    @Override
    protected double breathValue(){
        return 5;
    }



}
