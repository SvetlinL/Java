package main.java.aquarium.core;

import main.java.aquarium.entities.aquariums.Aquarium;
import main.java.aquarium.entities.aquariums.FreshwaterAquarium;
import main.java.aquarium.entities.aquariums.SaltwaterAquarium;
import main.java.aquarium.entities.decorations.Decoration;
import main.java.aquarium.entities.decorations.Ornament;
import main.java.aquarium.entities.decorations.Plant;
import main.java.aquarium.entities.fish.Fish;
import main.java.aquarium.entities.fish.FreshwaterFish;
import main.java.aquarium.entities.fish.SaltwaterFish;
import main.java.aquarium.repositories.DecorationRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller{
    private DecorationRepository decorations;
    private Collection<Aquarium> aquariums;

    public ControllerImpl(){
        this.aquariums = new ArrayList<>();
        this.decorations = new DecorationRepository();
    }
    @Override
    public String addAquarium(String aquariumType, String aquariumName) {
        if (!aquariumType.equals("FreshwaterAquarium") && !aquariumType.equals("SaltwaterAquarium")){
            throw new NullPointerException("Invalid aquarium type.");
        }
        Aquarium aquarium = null;
        switch (aquariumType){
            case "FreshwaterAquarium":
                aquarium = new FreshwaterAquarium(aquariumName);
                break;
            case "SaltwaterAquarium":
                aquarium = new SaltwaterAquarium(aquariumName);
                break;
        }
        this.aquariums.add(aquarium);
        return String.format("Successfully added %s.",aquariumType);
    }

    @Override
    public String addDecoration(String type) {
        if (!type.equals("Ornament") && !type.equals("Plant")){
            throw new IllegalArgumentException("Invalid decoration type.");
        }
        Decoration decoration = null;
        switch (type){
            case "Ornament":
                decoration = new Ornament();
                break;
            case "Plant":
                decoration = new Plant();
                break;
        }
        decorations.add(decoration);
        return String.format("Successfully added %s.",type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        if (decorations.findByType(decorationType)==null){
            throw new IllegalArgumentException(String.format("There isn't a decoration of type %s.",decorationType));
        }
        Decoration decoration = decorations.findByType(decorationType);
        decorations.remove(decoration);
        for (Aquarium aquarium : aquariums) {
            if (aquarium.getName().equals(aquariumName)){
                aquarium.addDecoration(decoration);
            }
        }
        return String.format("Successfully added %s to %s.",decorationType,aquariumName);
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        if (!fishType.equals("FreshwaterFish") && !fishType.equals("SaltwaterFish")){
            throw new IllegalArgumentException("Invalid fish type.");
        }
        String message = "Water not suitable.";
        Fish fish = null;
        switch (fishType){
            case "FreshwaterFish":
                fish = new FreshwaterFish(fishName,fishSpecies,price);
                break;
            case "SaltwaterFish":
                fish = new SaltwaterFish(fishName,fishSpecies,price);
                break;
        }
        for (Aquarium aquar : aquariums) {
            if (aquar.getName().equals(aquariumName)){
                if (aquar.getClass().getSimpleName().contains("Salt") && fish.getClass().getSimpleName().contains("Salt")){
                    try {
                        aquar.addFish(fish);
                        message = String.format("Successfully added %s to %s.",fishType,aquariumName);
                    }catch (IllegalArgumentException exception){
                        message = exception.getMessage();
                    }
                }else if (aquar.getClass().getSimpleName().contains("Fresh") && fish.getClass().getSimpleName().contains("Fresh")){
                    try {
                        aquar.addFish(fish);
                        message = String.format("Successfully added %s to %s.",fishType,aquariumName);
                    }catch (IllegalArgumentException exception){
                        message = exception.getMessage();
                    }
                }
            }
        }
        return message;
    }

    @Override
    public String feedFish(String aquariumName) {
        int size = 0;
        for (Aquarium aquarium : aquariums) {
            if (aquarium.getName().equals(aquariumName)){
                aquarium.feed();
                size = aquarium.getFish().size();
            }
        }
        return String.format("Fish fed: %d",size);
    }

    @Override
    public String calculateValue(String aquariumName) {
        double total = 0.0;
        for (Aquarium aquarium : aquariums) {
            if (aquarium.getName().equals(aquariumName)){
                total += aquarium.getFish().stream().mapToDouble(Fish::getPrice).sum();
                total += aquarium.getDecorations().stream().mapToDouble(Decoration::getPrice).sum();
            }
        }
        return String.format("The value of Aquarium %s is %.2f.",aquariumName,total);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        for (Aquarium aquarium : aquariums) {
            sb.append(aquarium.getInfo()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

}
