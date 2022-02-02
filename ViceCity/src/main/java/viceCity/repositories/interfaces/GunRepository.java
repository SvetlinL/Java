package viceCity.repositories.interfaces;

import viceCity.models.guns.Gun;

import java.util.ArrayList;
import java.util.Collection;

public class GunRepository implements Repository{
    private Collection<Gun> models;

    public GunRepository(){
        this.models = new ArrayList<>();
    }

    @Override
    public Collection getModels() {
        return this.models;
    }

    public void add(Gun model){
        if (!this.models.contains(model)){
            this.models.add(model);
        }
    }

    @Override
    public void add(Object model) {
    }

    @Override
    public boolean remove(Object model) {
        return false;
    }
    public boolean remove(Gun model){
        if (this.models.isEmpty()){
            return false;
        }else {
            for (Gun gun : models) {
                if (gun.equals(model)){
                    return this.models.remove(model);
                }
            }
        }
        return false;
    }

    @Override
    public Gun find(String name) {
        Gun returnGun = null;
        for (Gun gun : models) {
            if (gun.getName().equals(name)){
                returnGun = gun;
                break;
            }
        }
        return returnGun;
    }
}
