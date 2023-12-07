import processing.core.PApplet;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Tower {
    private double range;
    private int damage, upgradeCost, upgradeCount, x, y, tick, size;
    private double fireRate;

    public Tower(int dmg, double fr, int upgradeCost, int x, int y, double range, int upgradeCount){
        damage = dmg;
        fireRate = fr;
        this.upgradeCost = upgradeCost;
        this.x = x;
        this.y = y;
        this.range = range;
        size = 30;
        upgradeCount = 0;
    }

    public void update(Game game, ArrayList<Tank> tanks){
        tick++;
        draw(game);
        if(tick*fireRate >= 60){
        shoot(tanks, game);
        tick = 0;
        }
    }


    public void draw(Game game){
        game.fill(40,40,40);
        game.rect(x,y,size,size);
    }

    public void shoot(ArrayList<Tank> tanks, Game game){
        Tank currTarget = findTarget(tanks);
        if(currTarget != null){
            Bullet bullet = new Bullet(currTarget, damage, x+(size/2), y+(size/2), (currTarget.getX()-x)/10, (currTarget.getY()-y)/10, 10);
            game.addToBulletList(bullet);
        }
    }


    public Tank findTarget(ArrayList<Tank> tanks){
        int distance = 0;
        int closest = 1000;
        Tank target = null;

        for(Tank tank: tanks){
            distance+= Math.abs(tank.getX()-x);
            distance+= Math.abs(tank.getY()-y);
            if(distance<=range){
                if(distance < closest){
                    closest = distance;
                    target = tank;
                }
            }
            distance = 0;
        }


        return target;
    }

    public boolean contains(int x, int y){
        return (x > this.x && x < this.x+size && y > this.y && y < this.y+size);
    }

    public void upgrade(Game game){
        if(upgradeCount < 4) {

            upgradeCount++;
            game.addMoney(-upgradeCost);
            upgradeCost += 100;
            damage += 5;
            if(range <= 300) {
                range += 75;
            }else range = 300;
            fireRate += 0.5;
        }

    }

    public int getUpgradeCost() {
        return upgradeCost;
    }
    public double getRange() {
        return range;
    }
    public int getDamage() {
        return damage;
    }
    public int getUpgradeCount() {
        return upgradeCount;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getFireRate() {
        return fireRate;
    }
}
