package spaceStation.core;

public interface Controller {
    String addAstronaut(String type, String astronautName);

    String addPlanet(String planetName, String... items);

    String retireAstronaut(String astronautName) throws NoSuchFieldException;

    String explorePlanet(String planetName);

    String report();
}
