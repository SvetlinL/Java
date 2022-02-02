package viceCity.models.guns;

public abstract class BaseGun implements Gun{
    private String name;
    private int bulletsPerBarrel;
    private int totalBullets;
    private boolean canFire;
    //dopulnitelni
    private int barrelCapacity;


    protected BaseGun(String name, int bulletsPerBarrel, int totalBullets){
        this.setName(name);
        this.setBulletsPerBarrel(bulletsPerBarrel);
        this.setTotalBullets(totalBullets);
        this.barrelCapacity = bulletsPerBarrel;
    }


    protected int getBarrelCapacity(){
        return this.barrelCapacity;
    }

    protected void setTotalBullets(int totalBullets){
        if (totalBullets < 0){
            throw new IllegalArgumentException("Total bullets cannot be below zero!");
        }
        this.totalBullets = totalBullets;
    }

    protected void setBulletsPerBarrel(int bulletsPerBarrel){
        if (bulletsPerBarrel < 0){
            throw new IllegalArgumentException("Bullets cannot be below zero!");
        }
        this.bulletsPerBarrel = bulletsPerBarrel;
    }

    private void setName(String name){
        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException("Name cannot be null or whitespace!");
        }
        this.name = name;
    }

    protected int bulletsPerShot(){
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getBulletsPerBarrel() {
        return this.bulletsPerBarrel;
    }

    @Override
    public boolean canFire() {
        if (this.totalBullets >= bulletsPerShot()){
            this.totalBullets -= bulletsPerShot();
            this.canFire = true;
        }else {
            this.canFire = false;
        }
        return this.canFire;
    }

    @Override
    public int getTotalBullets() {
        return this.totalBullets;
    }

    @Override
    public int fire() {
        return bulletsPerShot();
    }

}
