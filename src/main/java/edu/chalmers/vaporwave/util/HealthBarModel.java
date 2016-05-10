package edu.chalmers.vaporwave.util;

/**
 * Created by Lina on 5/9/2016.
 */
public class HealthBarModel {
    int newHealth;
    int lastHealth;
    int currentHealth;

    public HealthBarModel(int i){
        this.currentHealth=i;
    }

    public void setHealth(int i){
        this.currentHealth=i;
    }

    public int getHealth(){
        return this.currentHealth;
    }

    public void updateHealth(int newHealth){
        this.newHealth=newHealth;
        if(this.newHealth==this.currentHealth){

        } else if(this.currentHealth>this.newHealth) {
            this.currentHealth--;
        } else if(this.currentHealth<this.newHealth) {
            this.currentHealth++;
        }
//        System.out.println(currentHealth);

    }

}
