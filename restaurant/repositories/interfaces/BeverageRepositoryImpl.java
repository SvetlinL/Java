package restaurant.repositories.interfaces;

import restaurant.entities.drinks.interfaces.Beverages;

import java.util.ArrayList;
import java.util.Collection;

public class BeverageRepositoryImpl implements BeverageRepository<Beverages>{
    Collection<Beverages> entities;

    public BeverageRepositoryImpl(){
        this.entities = new ArrayList<>();
    }
    @Override
    public Beverages beverageByName(String drinkName, String drinkBrand) {
        return this.entities.stream().filter(e-> e.getName().equals(drinkName) && e.getBrand().equals(drinkBrand))
                .findFirst().orElse(null);
    }

    @Override
    public Collection<Beverages> getAllEntities() {
        return this.entities;
    }

    @Override
    public void add(Beverages entity) {
        this.entities.add(entity);
    }
}
