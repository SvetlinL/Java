package viceCity.models.guns;

public class Rifle extends BaseGun {
    public Rifle(String name) {
        super(name, 50, 500);

    }

    @Override
    protected int bulletsPerShot(){
        return 5;
    }
}
