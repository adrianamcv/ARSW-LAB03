package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;

    private int health;

    private int defaultDamageValue;

    private final CopyOnWriteArrayList<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());
    private volatile  Boolean vivo=true;

    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation =(CopyOnWriteArrayList<Immortal>) immortalsPopulation;
        this.health = health;
        this.defaultDamageValue=defaultDamageValue;
    }

    public void run() {

        while (vivo && !ControlFrame.isStop()) {

            synchronized (this){
                if(ControlFrame.isPausa()) {
                    try {
                        wait();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Immortal im;

            int myIndex = immortalsPopulation.indexOf(this);

            int nextFighterIndex = r.nextInt(immortalsPopulation.size());

            //avoid self-fight
            if (nextFighterIndex == myIndex) {
                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
            }

            im = immortalsPopulation.get(nextFighterIndex);

            this.fight(im);



        }

    }

    public void fight(Immortal i2) {
        Immortal im1;
        Immortal im2;
        if (getId() > i2.getId()){
            im1 = this;
            im2 = i2;
        }else{
            im1 = i2;
            im2 = this;
        }
        synchronized (im1){
            synchronized (im2){
                if(i2.getHealth() > 0 && vivo){
                    i2.changeHealth(i2.getHealth() - defaultDamageValue);
                    this.health += defaultDamageValue;
                    updateCallback.processReport("Fight: " + this + " vs " + i2+"\n");
                }else{
                    updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
                }
            }
        }


    }

    public void changeHealth(int v) {
        health = v;
        if(v <= 0){
            vivo = false;
            immortalsPopulation.remove(this);
        }
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

    public synchronized void resumen(){
        this.notify();
    }

}
