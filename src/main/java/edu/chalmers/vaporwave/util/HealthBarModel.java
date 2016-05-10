package edu.chalmers.vaporwave.util;

/**
 * Created by Lina on 5/9/2016.
 */
public class HealthBarModel {
    int newHealth;
    int currentHealth;
    int change;

    public HealthBarModel(int i){
        this.currentHealth=i;
        this.change=2;
    }

    public void setHealth(int i){
        this.currentHealth=i;
    }

    public int getHealth(){
        return this.currentHealth;
    }

    public void updateHealth(int newHealth){
        this.newHealth=newHealth;

        int symbol=0;

        if(this.newHealth==this.currentHealth){

        } else if(this.currentHealth>this.newHealth) {
            symbol=-1;
        } else if(this.currentHealth<this.newHealth) {
            symbol=1;
        }
        this.currentHealth=currentHealth+(symbol*change);

    }

}
