import processing.core.PApplet;

import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    private int money, tickCount, towerCost, wave,round;
    private ArrayList<Tank> tankList;
    private ArrayList<Bullet> bulletList;
    private ArrayList<Tower> towerList;


    public void settings() {
        size(800, 800);
    }

    public void setup() {
        frameRate(60);
        money = 300;
        tankList = new ArrayList<Tank>();
        bulletList = new ArrayList<Bullet>();
        towerList = new ArrayList<Tower>();
        tickCount = 0;
        towerCost = 100;
        wave = 0;
        round = 0;
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(255);
        rect(0,375, 800, 50);
        tickCount++;
        if(tickCount >= 60){
            tickCount = 0;
            tankList.add(new Tank(100+wave*15,30,400,1,0, 40));
            round++;
        }
        if(round >= 15){
            wave++;
        }
        //loop through tanks
        for(int i = 0; i < tankList.size()-1; i++) {
            Tank tank = tankList.get(i);
            addMoney(tank.update(this));
            if(!tank.isAlive()){
                tankList.remove(tank);i--;
            }
        }
        //loop through bullets
        for(int i = 0; i < bulletList.size()-1; i++){
            Bullet bullet = bulletList.get(i);
            bullet.update(this);
            if(!bullet.isAlive()){
                bulletList.remove(bullet);
                i--;
            }
        }
        //loop through towers
        for(Tower tower: towerList){
            tower.update(this,tankList);
        }
    }

    public void addMoney(int cash){
        money += cash;
    }

    public void addToBulletList(Bullet bullet){
        bulletList.add(bullet);
    }

    public void mouseReleased(){
        if(money >= towerCost){
            towerList.add(new Tower(30,2,50,mouseX,mouseY,150));
            money-=towerCost;
        }
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }
}
