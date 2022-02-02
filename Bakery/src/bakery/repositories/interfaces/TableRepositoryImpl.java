package bakery.repositories.interfaces;

import bakery.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;

public class TableRepositoryImpl implements TableRepository<Table>{
    private Collection<Table> models;

    public TableRepositoryImpl() {
        this.models = new ArrayList<>();
    }

    @Override
    public Collection<Table> getAll() {
        return this.models;
    }

    @Override
    public void add(Table table) {
        this.models.add(table);
    }

    @Override
    public Table getByNumber(int number) {
        return this.models.stream().filter(e -> e.getTableNumber()==number).findFirst().orElse(null);
    }
}
