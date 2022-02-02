package bakery.entities.drinks.interfaces;

public class Water extends BaseDrink{
    public Water(String name, int portion, String brand) {
        super(name, portion, 1.50, brand);
    }
}
