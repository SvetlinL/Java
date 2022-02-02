package spaceStation.models.astronauts;

public class Geodesist extends BaseAstronaut{
    private static final double STARTING_OXYGEN = 50;
    public Geodesist(String name) {
        super(name, STARTING_OXYGEN);
    }
}
