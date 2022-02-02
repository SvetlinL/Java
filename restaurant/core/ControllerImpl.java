package restaurant.core;

import restaurant.common.ExceptionMessages;
import restaurant.common.OutputMessages;
import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.interfaces.Fresh;
import restaurant.entities.drinks.interfaces.Smoothie;
import restaurant.entities.healthyFoods.interfaces.Food;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.healthyFoods.interfaces.Salad;
import restaurant.entities.healthyFoods.interfaces.VeganBiscuits;
import restaurant.entities.tables.interfaces.InGarden;
import restaurant.entities.tables.interfaces.Indoors;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.*;

import java.util.Collection;

public class ControllerImpl implements Controller {
    private HealthFoodRepository<HealthyFood> healthFoodRepository;
    private BeverageRepository<Beverages> beverageRepository;
    private TableRepository<Table> tableRepository;
    private double totalIncome;

    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository, BeverageRepository<Beverages> beverageRepository, TableRepository<Table> tableRepository) {
        this.healthFoodRepository = healthFoodRepository;
        this.beverageRepository = beverageRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood foodCompare = this.healthFoodRepository.foodByName(name);
        Food newFood = null;
        switch (type){
            case "Salad":
                newFood = new Salad(name, price);
                break;
            case "VeganBiscuits":
                newFood = new VeganBiscuits(name, price);
                break;
        }
        if (foodCompare != null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.FOOD_EXIST,name));
        }
        this.healthFoodRepository.add(newFood);
        return String.format(OutputMessages.FOOD_ADDED,name);
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name){
        Beverages beverageCompare = beverageRepository.beverageByName(name,brand);
        Beverages newBeverage = null;
        switch (type){
            case "Smoothie":
                newBeverage = new Smoothie(name,counter,brand);
                break;
            case "Fresh":
                newBeverage = new Fresh(name,counter,brand);
                break;
        }
        if (beverageCompare != null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.BEVERAGE_EXIST,name));
        }
        this.beverageRepository.add(newBeverage);
        return String.format(OutputMessages.BEVERAGE_ADDED,type,brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table tableCompare = tableRepository.byNumber(tableNumber);
        Table newTable = null;
        switch (type){
            case "Indoors":
                newTable = new Indoors(tableNumber,capacity);
                break;
            case "InGarden":
                newTable = new InGarden(tableNumber, capacity);
                break;

        }
        if (tableCompare != null) {
             throw new IllegalArgumentException(String.format(ExceptionMessages.TABLE_IS_ALREADY_ADDED,tableNumber));
        }

        tableRepository.add(newTable);
        return String.format(OutputMessages.TABLE_ADDED,tableNumber);
    }

    @Override
    public String reserve(int numberOfPeople) {
        //Collection<Table> tables = this.tableRepository.getAllEntities();
        for (Table table : this.tableRepository.getAllEntities()) {
            if (!table.isReservedTable() && table.getSize() >= numberOfPeople) {
                table.reserve(numberOfPeople);
                return String.format(OutputMessages.TABLE_RESERVED,table.getTableNumber(),numberOfPeople);
            }
        }
        return String.format(OutputMessages.RESERVATION_NOT_POSSIBLE,numberOfPeople);
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {
        Table table = tableRepository.byNumber(tableNumber);
        if (table == null) {
            return String.format(OutputMessages.WRONG_TABLE_NUMBER,tableNumber);
        }
        HealthyFood food = healthFoodRepository.foodByName(healthyFoodName);
        if (food == null) {
            return String.format(OutputMessages.NONE_EXISTENT_FOOD,healthyFoodName);
        }
        if (table.isReservedTable()) {
            table.orderHealthy(food);
            return String.format(OutputMessages.FOOD_ORDER_SUCCESSFUL,healthyFoodName,tableNumber);
        }
        return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        Table table = tableRepository.byNumber(tableNumber);
        Beverages drink = beverageRepository.beverageByName(name, brand);
        if (table == null) {
            return String.format(OutputMessages.WRONG_TABLE_NUMBER,tableNumber);
        }
        if (drink == null) {
            return String.format(OutputMessages.NON_EXISTENT_DRINK,name,brand);
        }
        if (table.isReservedTable()) {
            tableRepository.byNumber(tableNumber).orderBeverages(drink);
            return String.format(OutputMessages.BEVERAGE_ORDER_SUCCESSFUL,name,tableNumber);
        }
        return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
    }

    @Override
    public String closedBill(int tableNumber) {
        Table table = this.tableRepository.byNumber(tableNumber);
        double bill = table.bill();
        this.totalIncome += bill;
        table.clear();
        return String.format(OutputMessages.BILL,tableNumber,bill);
    }


    @Override
    public String totalMoney() {
        return String.format(OutputMessages.TOTAL_MONEY,this.totalIncome);
    }
}
