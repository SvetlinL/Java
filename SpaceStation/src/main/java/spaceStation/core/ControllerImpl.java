package spaceStation.core;

import spaceStation.models.astronauts.*;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class ControllerImpl implements Controller {

    private AstronautRepository astronautRepository;
    private PlanetRepository planetRepository;
    private int exploredPlanetsCount;

    public ControllerImpl() {
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        if (!type.equals(Biologist.class.getSimpleName()) && !type.equals(Geodesist.class.getSimpleName()) &&
                !type.equals(Meteorologist.class.getSimpleName())) {
            throw new IllegalArgumentException("Astronaut type doesn't exists!");
        }
        Astronaut astronaut = null;
        switch (type) {
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
        }
        this.astronautRepository.add(astronaut);
        return String.format("Successfully added %s: %s!", type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName);
        if (items != null) {
            for (String item : items) {
                planet.getItems().add(item);
            }
        }
        this.planetRepository.add(planet);
        return String.format("Successfully added Planet: %s!", planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        for (Astronaut astronaut : astronautRepository.getModels()) {
            if (astronaut.getName().equals(astronautName)) {
                String name = astronaut.getName();
                astronautRepository.remove(astronaut);
                return String.format("Astronaut %s was retired!",name);
            }
        }
        throw new IllegalArgumentException(String.format("Astronaut %s doesn't exists!", astronautName));
    }

    @Override
    public String explorePlanet(String planetName) {
        List<Astronaut> viableAstronauts = astronautRepository.getModels().stream()
                .filter(astronaut -> astronaut.getOxygen() > 60)
                .collect(Collectors.toList());
        if (viableAstronauts.isEmpty()) {
            throw new IllegalArgumentException("You need at least one astronaut to explore the planet!");
        }
        Mission mission = new MissionImpl();
        Planet planet = planetRepository.findByName(planetName);
        mission.explore(planet, viableAstronauts);
        int deadAstronauts = 0;
        for (Astronaut astronaut : viableAstronauts) {
            if (astronaut.getOxygen() == 0) {
                deadAstronauts++;
            }
        }
        exploredPlanetsCount++;
        return String.format("Planet: %s was explored! Exploration finished with %d dead astronauts!",
                planetName, deadAstronauts);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d planets were explored!", exploredPlanetsCount)).append(System.lineSeparator());
        sb.append("Astronauts info:").append(System.lineSeparator());
        for (Astronaut astronaut : astronautRepository.getModels()) {
            sb.append(String.format("Name: %s", astronaut.getName())).append(System.lineSeparator());
            sb.append(String.format("Oxygen: %.0f", astronaut.getOxygen())).append(System.lineSeparator());
            sb.append("Bag items: ");
            if (astronaut.getBag().getItems().isEmpty()) {
                sb.append("none");
            } else {
                sb.append(String.join(", ", astronaut.getBag().getItems()));
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();

    }
}
