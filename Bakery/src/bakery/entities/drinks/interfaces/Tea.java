package bakery.entities.drinks.interfaces;

public class Tea extends BaseDrink{
    public Tea(String name, int portion, String brand) {
        super(name, portion, 2.50, brand);
    }
}
