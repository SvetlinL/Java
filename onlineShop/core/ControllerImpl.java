package onlineShop.core;

import onlineShop.common.constants.ExceptionMessages;
import onlineShop.common.constants.OutputMessages;
import onlineShop.core.interfaces.Controller;
import onlineShop.models.products.Product;
import onlineShop.models.products.components.*;
import onlineShop.models.products.computers.Computer;
import onlineShop.models.products.computers.DesktopComputer;
import onlineShop.models.products.computers.Laptop;
import onlineShop.models.products.peripherals.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    List<Computer> computers;
    List<Component> components;
    List<Peripheral> peripherals;


    public ControllerImpl(){
        this.computers = new ArrayList<>();
        this.components = new ArrayList<>();
        this.peripherals = new ArrayList<>();
    }

    @Override
    public String addComputer(String computerType, int id, String manufacturer, String model, double price) {
        for (Computer c : computers) {
            if (c.getId() == id){
                throw new IllegalArgumentException(ExceptionMessages.EXISTING_COMPUTER_ID);
            }
        }
        Computer computer = null;
        switch (computerType){
            case "DesktopComputer":
                computer = new DesktopComputer(id,manufacturer,model,price);
                break;
            case "Laptop":
                computer = new Laptop(id,manufacturer,model,price);
                break;
        }
        if (computer == null){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_COMPUTER_TYPE);
        }
        computers.add(computer);
        return String.format(OutputMessages.ADDED_COMPUTER,computer.getId());
    }

    @Override
    public String addPeripheral(int computerId, int id, String peripheralType, String manufacturer, String model, double price, double overallPerformance, String connectionType) {
        for (Peripheral p : peripherals) {
            if (p.getId() == id){
                throw new IllegalArgumentException(ExceptionMessages.EXISTING_PERIPHERAL_ID);
            }
        }
        Peripheral peripheral = null;
        switch (peripheralType){
            case "Headset":
                peripheral = new Headset(id,manufacturer,model,price,overallPerformance,connectionType);
                break;
            case "Keyboard":
                peripheral = new Keyboard(id,manufacturer,model,price,overallPerformance,connectionType);
                break;
            case "Monitor":
                peripheral = new Monitor(id,manufacturer,model,price,overallPerformance,connectionType);
                break;
            case "Mouse":
                peripheral = new Mouse(id,manufacturer,model,price,overallPerformance,connectionType);
                break;
        }
        if (peripheral == null){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PERIPHERAL_TYPE);
        }
        for (Computer computer : computers) {
            if (computer.getId()== computerId){
                computer.addPeripheral(peripheral);
                this.peripherals.add(peripheral);
                return String.format(OutputMessages.ADDED_PERIPHERAL,peripheralType,id,computerId);
            }
        }
        throw new IllegalArgumentException(ExceptionMessages.NOT_EXISTING_COMPUTER_ID);
    }

    @Override
    public String removePeripheral(String peripheralType, int computerId) {
        for (Computer comp : computers) {
            if (comp.getId() == computerId){
                Peripheral peripheralToRemove = comp.removePeripheral(peripheralType);
                this.peripherals.remove(peripheralToRemove);
                return String.format(OutputMessages.REMOVED_PERIPHERAL,peripheralType,peripheralToRemove.getId());
            }
        }
        throw new IllegalArgumentException(ExceptionMessages.NOT_EXISTING_COMPUTER_ID);
    }

    @Override
    public String addComponent(int computerId, int id, String componentType, String manufacturer, String model, double price, double overallPerformance, int generation) {
        for (Component c : this.components) {
            if (c.getId() == id){
                throw new IllegalArgumentException(ExceptionMessages.EXISTING_COMPONENT_ID);
            }
        }
        Component component = null;
        switch (componentType){
            case "CentralProcessingUnit":
                component = new CentralProcessingUnit(id,manufacturer,model,price,overallPerformance,generation);
                break;
            case "Motherboard":
                component = new Motherboard(id,manufacturer,model,price,overallPerformance,generation);
                break;
            case "PowerSupply":
                component = new PowerSupply(id,manufacturer,model,price,overallPerformance,generation);
                break;
            case "RandomAccessMemory":
                component = new RandomAccessMemory(id,manufacturer,model,price,overallPerformance,generation);
                break;
            case "SolidStateDrive":
                component = new SolidStateDrive(id,manufacturer,model,price,overallPerformance,generation);
                break;
            case "VideoCard":
                component = new VideoCard(id,manufacturer,model,price,overallPerformance,generation);
                break;
        }
        if (component == null){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_COMPONENT_TYPE);
        }
        for (Computer computer : computers) {
            if (computer.getId()==computerId){
                computer.addComponent(component);
                this.components.add(component);
                return String.format(OutputMessages.ADDED_COMPONENT,componentType,id,computerId);

            }
        }
        throw new IllegalArgumentException(ExceptionMessages.NOT_EXISTING_COMPUTER_ID);
    }

    @Override
    public String removeComponent(String componentType, int computerId) {
        for (Computer c : computers) {
            if (c.getId()==computerId){
                Component componentToRemove = c.removeComponent(componentType);
                this.components.remove(componentToRemove);
                return String.format(OutputMessages.REMOVED_COMPONENT,componentType,componentToRemove.getId());
            }
        }
        throw new IllegalArgumentException(ExceptionMessages.NOT_EXISTING_COMPUTER_ID);
    }

    @Override
    public String buyComputer(int id) {
        Computer computer = this.computers.stream().filter(c-> c.getId()==id).findFirst().orElse(null);
        if (computer == null){
            throw new IllegalArgumentException(ExceptionMessages.NOT_EXISTING_COMPUTER_ID);
        }
        this.computers.remove(computer);
        return computer.toString();
    }

    @Override
    public String BuyBestComputer(double budget) {
        Computer computer = this.computers.stream().filter(c -> c.getPrice() <= budget)
                .max(Comparator.comparingDouble(Computer::getOverallPerformance))
                .stream().findFirst().orElse(null);

        if (computer == null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAN_NOT_BUY_COMPUTER,budget));
        }
        this.computers.remove(computer);
        return computer.toString();
    }

    @Override
    public String getComputerData(int id) {
        Computer computer = this.computers.stream().filter(c-> c.getId()==id).findFirst().orElse(null);
        if (computer==null){
            throw new IllegalArgumentException(ExceptionMessages.NOT_EXISTING_COMPUTER_ID);
        }
        return computer.toString();
    }
}
