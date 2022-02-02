package bakery.repositories.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;

import java.util.ArrayList;
import java.util.Collection;

public class FoodRepositoryImpl implements FoodRepository<BakedFood>{
    private Collection<BakedFood> models;

    public FoodRepositoryImpl(){
        this.models = new ArrayList<>();
    }

    @Override
    public Collection<BakedFood> getAll() {
        return this.models;
    }


    @Override
    public void add(BakedFood bakedFood) {
        this.models.add(bakedFood);
    }

    @Override
    public BakedFood getByName(String name) {
        return this.models.stream().filter(e-> e.getName().equals(name)).findFirst().orElse(null);
    }
}
