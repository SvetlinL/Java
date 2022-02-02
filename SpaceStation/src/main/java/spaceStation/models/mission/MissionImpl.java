package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.Collection;
import java.util.List;

public class MissionImpl implements Mission {

    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {
        List<String> planetItems = (List<String>) planet.getItems();
        for (Astronaut astronaut : astronauts) {
            if (planetItems.isEmpty()){
                break;
            }
            while (!planetItems.isEmpty()) {
                if (astronaut.canBreath()) {
                    String foundItem = planetItems.get(0);
                    astronaut.getBag().getItems().add(foundItem);
                    planetItems.remove(foundItem);
                    astronaut.breath();
                }else {
                    break;
                }
            }
        }

    }
}
