package bakery.repositories.interfaces;

import bakery.entities.drinks.interfaces.Drink;

import java.util.ArrayList;
import java.util.Collection;

public class DrinkRepositoryImpl implements DrinkRepository<Drink>{
    private Collection<Drink> models;

    public DrinkRepositoryImpl(){
        this.models = new ArrayList<>();
    }

    @Override
    public Collection<Drink> getAll() {
        return this.models;
    }

    @Override
    public void add(Drink drink) {
        this.models.add(drink);
    }

    @Override
    public Drink getByNameAndBrand(String drinkName, String drinkBrand) {
        return this.models.stream().filter(e-> e.getName().equals(drinkName) && e.getBrand().equals(drinkBrand)).findFirst().orElse(null);
    }
}
