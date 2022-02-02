package restaurant.repositories.interfaces;

import restaurant.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;

public class TableRepositoryImpl implements TableRepository<Table>{
    Collection<Table> entities;

    public TableRepositoryImpl(){
        this.entities = new ArrayList<>();
    }

    @Override
    public Table byNumber(int number) {
        return this.entities.stream().filter(e-> e.getTableNumber()==number).findFirst().orElse(null);
    }

    @Override
    public Collection<Table> getAllEntities() {
        return this.entities;
    }

    @Override
    public void add(Table entity) {
        this.entities.add(entity);
    }
}
