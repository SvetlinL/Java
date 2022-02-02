package main.java.aquarium.repositories;

import main.java.aquarium.entities.decorations.Decoration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DecorationRepository implements Repository{
    private Collection<Decoration> decorations;

    public DecorationRepository(){
        this.decorations = new ArrayList<>();
    }

    @Override
    public void add(Decoration decoration) {
        this.decorations.add(decoration);
    }

    @Override
    public boolean remove(Decoration decoration) {
        if (this.decorations.isEmpty()){
            return false;
        }
        for (Decoration decor : decorations) {
            if (decor.equals(decoration)){
                return this.decorations.remove(decor);
            }
        }
        return false;
    }

    @Override
    public Decoration findByType(String type) {
        return decorations.stream().filter(decor-> decor.getClass().getSimpleName().equals(type))
                .findFirst().orElse(null);
    }

}
