import processing.core.PApplet;

import java.io.*;
import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    private int money, tickCount, towerCost, wave, round, initialTowerRange;
    private ArrayList<Tank> tankList;
    private ArrayList<Bullet> bulletList;
    private ArrayList<Tower> towerList;
    private boolean towerBuyMode;


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
        initialTowerRange = 250;
        towerBuyMode = false;
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(0,100,0);
        fill(0,255,0);
        textSize(24);
        text("Money: " + money, 50,50);
        fill(0);
        text("Placing Towers: " + towerBuyMode, 250,50);
        text("Wave: " + wave, 600, 50);
        tickCount++;
        if (tickCount >= 60) {
            tickCount = 0;
            tankList.add(new Tank(100 + wave * 10, 30, 400, 1, 0, 40));
            round++;
        }
        if (round >= 15) {
            wave++;
            addMoney(150);
            round = 0;
        }
        //loop through tanks
        for (int i = 0; i < tankList.size() - 1; i++) {
            Tank tank = tankList.get(i);
            if (!tank.isAlive()) {
                tankList.remove(tank);
                i--;
            }
            addMoney(tank.update(this));
        }
        //loop through bullets
        for (int i = 0; i < bulletList.size() - 1; i++) {
            Bullet bullet = bulletList.get(i);
            bullet.update(this);
            if (!bullet.isAlive()) {
                bulletList.remove(bullet);
                i--;
            }
        }
        //loop through towers
        for (Tower tower : towerList) {
            tower.update(this, tankList);
        }
    }

    public void addMoney(int cash) {
        money += cash;
    }

    public void addToBulletList(Bullet bullet) {
        bulletList.add(bullet);
    }

    public void buyTower() {
        if (money >= towerCost) {
            towerList.add(new Tower(34, 1, 50, mouseX, mouseY, initialTowerRange, 0));
            money -= towerCost;
        }
    }

    public boolean towerClicked(){
        for (Tower tower : towerList) {
            if (tower.contains(mouseX, mouseY)) {
                if (money >= tower.getUpgradeCost()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Tower clickedTower(){
        for (Tower tower : towerList) {
            if (tower.contains(mouseX, mouseY)) {
                if (money >= tower.getUpgradeCost()) {
                    return tower;
                }
            }
        }
        return null;
    }

    public void mouseReleased() {
        if(towerBuyMode){
            if(!towerClicked()){
                buyTower();
            }
        }else {
            if(towerClicked()){
                clickedTower().upgrade(this);
            }
        }
    }

    public void keyReleased() {
        if (key == 'z') {
            towerBuyMode = !towerBuyMode;
        } else if (key == 's') {
            try {
                PrintWriter tankSaver = new PrintWriter(new FileWriter("saveTanks.txt"));
                PrintWriter towerSaver = new PrintWriter(new FileWriter("saveTowers.txt"));

                for (Tank tank : tankList) {
                    tankSaver.println(tank.getX() + "," + tank.getY() + "," + tank.getHealth() + "," + tank.getxSpeed() + "," + tank.getySpeed() + "," + tank.getSize());
                }
                for (Tower tower : towerList) {
                    towerSaver.println(tower.getDamage() + "," + tower.getFireRate() + "," + tower.getUpgradeCost() + "," + tower.getX() + "," + tower.getY() + "," + tower.getRange() + "," + tower.getUpgradeCount());
                }
                tankSaver.close();
                towerSaver.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (key == 'l') {
            try {
                BufferedReader in = new BufferedReader(new FileReader("saveTanks.txt"));
                tankList.clear();
                String line;
                while ((line = in.readLine()) != null) {
                    String[] vals = line.split(",");
                    int health = Integer.parseInt(vals[0]);
                    int x = Integer.parseInt(vals[1]);
                    int y = Integer.parseInt(vals[2]);
                    int xSpeed = Integer.parseInt(vals[3]);
                    int ySpeed = Integer.parseInt(vals[4]);
                    int size = Integer.parseInt(vals[5]);
                    Tank p = new Tank(health, x, y, xSpeed, ySpeed, size);
                    tankList.add(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader in = new BufferedReader(new FileReader("saveTowers.txt"));
                towerList.clear();
                String line;
                while ((line = in.readLine()) != null) {
                    String[] vals = line.split(",");
                    int dmg = Integer.parseInt(vals[0]);
                    double fr = Double.parseDouble(vals[1]);
                    int upgradeCost = Integer.parseInt(vals[2]);
                    int x = Integer.parseInt(vals[3]);
                    int y = Integer.parseInt(vals[4]);
                    double range = Double.parseDouble(vals[5]);
                    int upgradeCount = Integer.parseInt(vals[6]);
                    Tower p = new Tower(dmg, fr, upgradeCost, x, y, range, upgradeCount);
                    towerList.add(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {
        PApplet.main("Game");
    }
}