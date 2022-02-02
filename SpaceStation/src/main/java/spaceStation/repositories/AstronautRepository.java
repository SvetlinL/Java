package spaceStation.repositories;

import spaceStation.models.astronauts.Astronaut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class AstronautRepository implements Repository {
    private Collection<Astronaut> astronauts;

    public AstronautRepository() {
        this.astronauts = new ArrayList<>();
    }


    public Collection<Astronaut> getModels() {
        return Collections.unmodifiableCollection(this.astronauts);
    }

    @Override
    public void add(Object model) {

    }

    @Override
    public boolean remove(Object model) {
        return false;
    }


    public void add(Astronaut model) {
        this.astronauts.add(model);
    }


    public boolean remove(Astronaut model) {
        if (this.astronauts.isEmpty()) {
            return false;
        }
        for (Astronaut astronaut : astronauts) {
            if (astronaut.equals(model)) {
                return this.astronauts.remove(model);
            }
        }
        return false;
    }

    public Astronaut findByName(String name) {
        if (this.astronauts.isEmpty()) {
            return null;
        } else {
            for (Astronaut astronaut : astronauts) {
                if (astronaut.getName().equals(name)) {
                    return astronaut;
                }
            }
            return null;
        }
    }
}
