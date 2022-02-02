package bakery.core;

import bakery.common.enums.DrinkType;
import bakery.common.enums.TableTYpe;
import bakery.core.interfaces.Controller;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.bakedFoods.interfaces.Bread;
import bakery.entities.bakedFoods.interfaces.Cake;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.drinks.interfaces.Tea;
import bakery.entities.drinks.interfaces.Water;
import bakery.entities.tables.interfaces.InsideTable;
import bakery.entities.tables.interfaces.OutsideTable;
import bakery.entities.tables.interfaces.Table;
import bakery.repositories.interfaces.DrinkRepository;
import bakery.repositories.interfaces.FoodRepository;
import bakery.repositories.interfaces.TableRepository;

import java.util.Collection;

import static bakery.common.ExceptionMessages.FOOD_OR_DRINK_EXIST;
import static bakery.common.ExceptionMessages.TABLE_EXIST;
import static bakery.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private FoodRepository<BakedFood> foodRepository;
    private DrinkRepository<Drink> drinkRepository;
    private TableRepository<Table> tableRepository;
    private double totalIncome;

    public ControllerImpl(FoodRepository<BakedFood> foodRepository, DrinkRepository<Drink> drinkRepository, TableRepository<Table> tableRepository) {
        this.foodRepository = foodRepository;
        this.drinkRepository = drinkRepository;
        this.tableRepository = tableRepository;
    }


    @Override
    public String addFood(String type, String name, double price) {
        BakedFood food = this.foodRepository.getByName(name);
        if (food != null){
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST,type,name));
        }
        switch (type){
            case "Bread":
                food = new Bread(name, price);
                break;
            case "Cake":
                food = new Cake(name, price);
                break;
        }
        this.foodRepository.add(food);
        return String.format(FOOD_ADDED,name,type);
    }

    @Override
    public String addDrink(String type, String name, int portion, String brand) {
        Drink byNameAndBrand = drinkRepository.getByNameAndBrand(name, brand);
        if (byNameAndBrand != null) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
        }
        DrinkType drinkType = DrinkType.valueOf(type);
        if (drinkType.equals(DrinkType.Tea)) {
            byNameAndBrand = new Tea(name, portion, brand);
        } else if (drinkType.equals(DrinkType.Water)) {
            byNameAndBrand = new Water(name, portion, brand);
        }
        drinkRepository.add(byNameAndBrand);
        return String.format(DRINK_ADDED, name, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table byNumber = tableRepository.getByNumber(tableNumber);
        if (byNumber != null) {
            throw new IllegalArgumentException(String.format(TABLE_EXIST, tableNumber));
        }
        TableTYpe tableTYpe = TableTYpe.valueOf(type);
        if (tableTYpe.equals(TableTYpe.OutsideTable)) {
            byNumber = new OutsideTable(tableNumber, capacity);
        } else if (tableTYpe.equals(TableTYpe.InsideTable)) {
            byNumber = new InsideTable(tableNumber, capacity);
        }
        tableRepository.add(byNumber);
        return String.format(TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserveTable(int numberOfPeople) {
        Collection<Table> tables = this.tableRepository.getAll();
        for (Table table : tables) {
            if (!table.isReserved() && table.getCapacity() >= numberOfPeople) {
                table.reserve(numberOfPeople);
                return String.format(TABLE_RESERVED, table.getTableNumber(), numberOfPeople);
            }
        }
        return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
    }

    @Override
    public String orderFood(int tableNumber, String foodName) {
        Table table = tableRepository.getByNumber(tableNumber);
        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }
        BakedFood food = foodRepository.getByName(foodName);
        if (food == null) {
            return String.format(NONE_EXISTENT_FOOD, foodName);
        }
        if (table.isReserved()) {
            table.orderFood(food);
            return String.format(FOOD_ORDER_SUCCESSFUL, tableNumber, foodName);
        }
        return String.format(WRONG_TABLE_NUMBER, tableNumber);
    }

    @Override
    public String orderDrink(int tableNumber, String drinkName, String drinkBrand) {
        Table table = tableRepository.getByNumber(tableNumber);
        Drink drink = drinkRepository.getByNameAndBrand(drinkName, drinkBrand);
        if (drink == null) {
            return String.format(NON_EXISTENT_DRINK, drinkName, drinkBrand);
        }
        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }
        if (table.isReserved()) {
            tableRepository.getByNumber(tableNumber).orderDrink(drink);
            return String.format(DRINK_ORDER_SUCCESSFUL, tableNumber, drinkName, drinkBrand);
        }
        return String.format(WRONG_TABLE_NUMBER, tableNumber);
    }

    @Override
    public String leaveTable(int tableNumber) {
        Table table = this.tableRepository.getByNumber(tableNumber);
        double bill = table.getBill();
        this.totalIncome += bill;
        table.clear();

        return String.format(BILL, tableNumber, bill);
    }

    @Override
    public String getFreeTablesInfo() {
        StringBuilder sb = new StringBuilder();
        for (Table table : this.tableRepository.getAll()) {
            if (!table.isReserved()) {
                sb.append(table.getFreeTableInfo()).append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String getTotalIncome() {
        return String.format("Total income: %.2flv", this.totalIncome);
    }
}

