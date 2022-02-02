package restaurant.repositories.interfaces;

import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;

public class HealthFoodRepositoryImpl implements HealthFoodRepository<HealthyFood>{
    Collection<HealthyFood> entities;

    public HealthFoodRepositoryImpl(){
        this.entities = new ArrayList<>();
    }

    @Override
    public HealthyFood foodByName(String name) {
        return this.entities.stream().filter(e-> e.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Collection<HealthyFood> getAllEntities() {
        return this.entities;
    }

    @Override
    public void add(HealthyFood entity) {
        this.entities.add(entity);
    }
}
