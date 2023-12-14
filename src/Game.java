import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.*;
import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    private int money, tickCount, towerCost, wave, round, initialTowerRange, tankSpawnHealth, health, killReward;
    private ArrayList<Tank> tankList;
    private ArrayList<Bullet> bulletList;
    private ArrayList<Tower> towerList;
    private boolean towerBuyMode, towerSellMode, muted;
    private int towers;
    private Minim loader;
    private AudioPlayer moneySound;
    private PImage mutedIcon;

    private static boolean lowGraphicsMode = false;

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
        wave = 1;
        round = 0;
        initialTowerRange = 150;
        towerBuyMode = false;
        tankSpawnHealth = 100;
        health = 100;
        loader = new Minim(this);
        moneySound = loader.loadFile("Assets/money.wav");
        towers = 0;
        killReward = 20;
        mutedIcon = loadImage("Assets/mutedIcon.png");
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        if(health > 0) {
            background(0, 100, 0);
            fill(153, 153, 153);
            rect(0, 375, 200, 50);
            rect(200, 225, 50, 200, 10, 10, 10, 0);
            rect(200, 225, 200, 50, 10, 10, 0, 0);
            rect(350, 225, 50, 350, 0, 10, 10, 10);
            rect(150, 550, 250, 50, 10, 0, 10, 0);
            rect(150, 600, 50, 150, 0, 0, 0, 10);
            rect(200, 700, 450, 50, 0, 0, 0, 0);
            rect(650, 500, 50, 250, 0, 10, 10, 0);
            rect(500, 500, 200, 50, 0, 10, 0, 10);
            rect(500, 300, 50, 200, 10, 0, 0, 0);
            rect(550, 300, 150, 50, 0, 0, 0, 0);
            rect(700, 100, 50, 250, 10, 10, 10, 0);
            rect(700, 100, 700, 50, 10, 0, 0, 0);
            fill(0, 255, 0);
            textSize(24);
            text("Money: $" + money, 25, 50);
            text("Tower cost: $" + towerCost, 25, 100);
            if(towerBuyMode){
                fill(0,255,0);
                text("Mode: Buying Towers", 240, 50);
            }else if(towerSellMode){
                fill(255,0,0);
                text("Mode: Selling Towers", 240, 50);
            }else{
                fill(255,255,0);
                text("Mode: Upgrading Towers", 240, 50);
            }
            fill(0);
            text("Wave: " + wave, 570, 50);
            fill(0,200,255);
            text("Health: " + health, 570, 100);
            if(muted) {
                image(mutedIcon, 10, 750, 50, 50);
            }

            tickCount++;
            if (tickCount >= 60) {
                tickCount = 0;
                tankList.add(new Tank(tankSpawnHealth, 30, 400, 1, 0, 40, false, 0, this, killReward));
                round++;
            }
            if (round >= 10) {
                wave++;
                if (wave % 5 == 0) {
                    //spawn boss
                    killReward += 5;
                    Tank.increaseKillReward(5);
                    tankList.add(new Tank((tankSpawnHealth * 2 ) + 500, 30, 400, 1, 0, 40, true, 0, this, killReward*5));
                    towerCost += 100;
                }
                tankSpawnHealth = 100 + wave * 50;
                addMoney(150);
                round = 0;
            }
            //loop through tanks
            for (int i = 0; i < tankList.size() - 1; i++) {
                Tank tank = tankList.get(i);
                if (!tank.isAlive()) {
                    tankList.remove(tank);
                    i--;
                } else {
                    if (tank.getX() > 800) {
                        health -= tank.getHealth() / 10;
                        tankList.remove(tank);
                    }
                }
                addMoney(tank.update(this));
            }
            //loop through bullets
            for (int i = 0; i < bulletList.size() - 1; i++) {
                Bullet bullet = bulletList.get(i);
                if(bullet.getX() > 800 || bullet.getX() < 0 || bullet.getY() > 800 || bullet.getY() < 0){
                    bulletList.remove(bullet);
                    i--;
                }
                bullet.update(this, tankList);
                if (!bullet.isAlive()) {
                    bulletList.remove(bullet);
                    i--;
                }
            }
            //loop through towers
            for (Tower tower : towerList) {
                tower.update(this, tankList);
            }
            if (towerHovered() != null) {
                Tower tower = towerHovered();
                tower.setTowerHovered(true);
                int upgradeCost = tower.getUpgradeCost();
                fill(255, 255, 0);
                textSize(14);
                if(!towerBuyMode && !towerSellMode) {
                    if (upgradeCost == 450) {
                        text("MAX LEVEL", tower.getX() - 30, tower.getY() - 10);
                    } else {
                        text("Upgrade Cost: $" + upgradeCost, tower.getX() - 50, tower.getY() - 10);
                    }
                }else if(towerSellMode){
                    text("Sell Price: $" + Math.abs((towerCost + upgradeCost-100))/2, tower.getX() - 30, tower.getY() - 10);
                }
            }
            if (tankHovered() != null) {
                Tank tank = tankHovered();
                int health = tank.getHealth();
                fill(0);
                textSize(18);
                text(health + "/" + tank.getStartingHp(), tank.getX() - 40, tank.getY() + 50);
            }
        }else{
            if(round > 11){
                round = 0;
            }
            fill(255,0,0);
            textSize(50);
            text("YOU DIED L", 250,400);
            textSize(20);
            text("get better",300,450);
            fill(0);
            text("press 'R' to restart or 'L' to load last save", 200, 680);
        }
    }

    public void addMoney(int cash) {
        if(cash > 0){
            if(!muted){
                moneySound.play();
                moneySound.rewind();
            }
        }
        money += cash;
    }

    public void addToBulletList(Bullet bullet) {
        bulletList.add(bullet);
    }

    public void buyTower() {
        if (money >= towerCost && towers < wave+2) {
            towerList.add(new Tower(34, 1, 50, mouseX-25, mouseY-25, initialTowerRange, 0, this));
            money -= towerCost;
            towers++;
        }
    }

    public boolean canUpgradeTower(){
        if(towerHovered() != null){
            if(money >= towerHovered().getUpgradeCost()){
                return true;
            }
        }
        return false;
    }

    public Tower towerHovered(){
        for (Tower tower : towerList) {
            if (tower.contains(mouseX, mouseY)) {
                return tower;
            }
        }
        return null;
    }

    public Tank tankHovered(){
        for(Tank tank : tankList){
            if(tank.contains(mouseX, mouseY)){
                return tank;
            }
        }
        return null;
    }

    public void mouseReleased() {
        if(towerBuyMode){
            if(towerHovered() == null){
                buyTower();
            }
        }else if(towerSellMode){
            if(canUpgradeTower()){
                money += Math.abs((towerCost+towerHovered().getUpgradeCost()-100))/2;
                towerList.remove(towerHovered());
            }
        }else {
            if(towerHovered() != null){
                if(money >= towerHovered().getUpgradeCost()){
                    towerHovered().upgrade(this);
                }
            }
        }
    }

    public void keyReleased() {
        if (key == 'z') {
            towerBuyMode = !towerBuyMode;
            towerSellMode = false;
        }else if(key == 'm'){
            muted = !muted;
        }else if(key == 'x') {
            towerSellMode = !towerSellMode;
            towerBuyMode = false;
        }else if(key == 'g'){
            lowGraphicsMode = !lowGraphicsMode;
        } else if(key == 'r') {
            setup();
        } else if (key == 's') {
            if(health > 0){
                try {
                    PrintWriter tankSaver = new PrintWriter(new FileWriter("saveTanks.txt"));
                    PrintWriter towerSaver = new PrintWriter(new FileWriter("saveTowers.txt"));
                    PrintWriter statSaver = new PrintWriter(new FileWriter("saveStats.txt"));

                    for (Tank tank : tankList) {
                        tankSaver.println(tank.getHealth() + "," + tank.getX() + "," + tank.getY() + "," + tank.getxSpeed() + "," + tank.getySpeed() + "," + tank.getSize() + "," + tank.isBoss() + "," + tank.getIndex() + "," + Tank.getKillReward());
                    }
                    for (Tower tower : towerList) {
                        towerSaver.println(tower.getDamage() + "," + tower.getFireRate() + "," + tower.getUpgradeCost() + "," + tower.getX() + "," + tower.getY() + "," + tower.getRange() + "," + tower.getUpgradeCount());
                    }
                    statSaver.println(money + "," + wave + "," + round + "," + health + "," + tickCount + "," + towerCost);
                    tankSaver.close();
                    towerSaver.close();
                    statSaver.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (key == 'l') {
            //load stats
            try{
                BufferedReader in = new BufferedReader(new FileReader("saveStats.txt"));
                String line;
                while((line = in.readLine()) != null){
                    String[] vals = line.split(",");
                    money = Integer.parseInt(vals[0]);
                    wave = Integer.parseInt(vals[1]);
                    round = Integer.parseInt(vals[2]);
                    health = Integer.parseInt(vals[3]);
                    tickCount = Integer.parseInt(vals[4]);
                    towerCost = Integer.parseInt(vals[5]);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            //load tanks
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
                    boolean boss = Boolean.parseBoolean(vals[6]);
                    int index = Integer.parseInt(vals[7]);
                    int reward = Integer.parseInt(vals[8]);
                    Tank p = new Tank(health, x, y, xSpeed, ySpeed, size, boss, index, this, reward);
                    tankList.add(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //load towers
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
                    Tower p = new Tower(dmg, fr, upgradeCost, x, y, range, upgradeCount, this);
                    towerList.add(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isLowGraphicsMode() {
        return lowGraphicsMode;
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }
}