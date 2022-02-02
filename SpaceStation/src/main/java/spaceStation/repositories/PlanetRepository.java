package spaceStation.repositories;

import spaceStation.models.planets.Planet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PlanetRepository implements Repository {
    private Collection<Planet> planets;

    public PlanetRepository() {
        this.planets = new ArrayList<>();
    }



    public Collection getModels() {
        return Collections.unmodifiableCollection(this.planets);
    }

    @Override
    public void add(Object model) {

    }

    @Override
    public boolean remove(Object model) {
        return false;
    }


    public void add(Planet model) {
        this.planets.add(model);
    }



    public boolean remove(Planet model) {
        if (this.planets.isEmpty()) {
            return false;
        }
        for (Planet planet : planets) {
            if (planet.equals(model)) {
                this.planets.remove((Planet) model);

            }
        }
        return false;
    }


    public Planet findByName(String name) {
        if (this.planets.isEmpty()) {
            return null;
        } else {
            for (Planet planet : planets) {
                if (planet.getName().equals(name)) {
                    return planet;
                }
            }
            return null;
        }

    }
}
