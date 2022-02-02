package bakery.entities.tables.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.Drink;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseTable implements Table{
    private Collection<BakedFood> foodOrders;
    private Collection<Drink> drinkOrders;
    private int tableNumber;
    private int capacity;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReserved = false;
    private double price;

    protected BaseTable(int tableNumber, int capacity, double pricePerPerson){
        this.tableNumber = tableNumber;
        this.setCapacity(capacity);
        this.pricePerPerson = pricePerPerson;
        this.foodOrders = new ArrayList<>();
        this.drinkOrders = new ArrayList<>();

    }

    private void setCapacity(int capacity){
        if (capacity < 0){
            throw new IllegalArgumentException("Capacity has to be greater than 0");
        }
        this.capacity = capacity;
    }

    @Override
    public int getTableNumber() {
        return this.tableNumber;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getNumberOfPeople() {
        return this.numberOfPeople;
    }

    @Override
    public double getPricePerPerson() {
        return this.pricePerPerson;
    }

    @Override
    public boolean isReserved() {
        return this.isReserved;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void reserve(int numberOfPeople) {
        if (numberOfPeople < 0 || numberOfPeople > this.capacity ){
            throw new IllegalArgumentException("Cannot place zero or less people!");
        }
        this.numberOfPeople = numberOfPeople;
        this.isReserved = true;
        this.price = this.numberOfPeople * this.pricePerPerson;
    }

    @Override
    public void orderFood(BakedFood food) {
        this.foodOrders.add(food);
    }

    @Override
    public void orderDrink(Drink drink) {
        this.drinkOrders.add(drink);
    }

    @Override
    public double getBill() {
        double totalPriceDrink = drinkOrders.stream().mapToDouble(Drink::getPrice).sum();
        double totalPriceFood = foodOrders.stream().mapToDouble(BakedFood::getPrice).sum();
        double total = totalPriceDrink + totalPriceFood + this.getPrice();
        return total;
    }

    @Override
    public void clear() {
        this.drinkOrders.clear();
        this.foodOrders.clear();
        this.numberOfPeople = 0;
        this.isReserved = false;
        this.price = 0.0;
    }

    @Override
    public String getFreeTableInfo() {
        //"Table: {table number}"
        //"Type: {table type}"
        //"Capacity: {table capacity}"
        //"Price per Person: {price per person for the current table}"
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Table: %d",this.tableNumber)).append(System.lineSeparator());
        sb.append(String.format("Type: %s",getClass().getSimpleName())).append(System.lineSeparator());
        sb.append(String.format("Capacity: %d",this.capacity)).append(System.lineSeparator());
        sb.append(String.format("Price per Person: %.2f",this.pricePerPerson));
        sb.append(System.lineSeparator());
        return sb.toString().trim();
    }
}
