package spaceStation;


import spaceStation.core.Controller;
import spaceStation.core.ControllerImpl;
import spaceStation.core.EngineImpl;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Geodesist;

public class Main {
    public static void main(String[] args) {

        Controller controller = new ControllerImpl();
        EngineImpl engine = new EngineImpl(controller);
        engine.run();
    }
}
