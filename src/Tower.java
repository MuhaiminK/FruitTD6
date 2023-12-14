import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Tower{
    private double range;
    private int damage, upgradeCost, upgradeCount, x, y, tick, size;
    private double fireRate;
    private PImage doctor;
    private boolean towerHovered;

    public Tower(int dmg, double fr, int upgradeCost, int x, int y, double range, int upgradeCount, PApplet game){
        damage = dmg;
        fireRate = fr;
        this.upgradeCost = upgradeCost;
        this.x = x;
        this.y = y;
        this.range = range;
        size = 50;
        this.upgradeCount = upgradeCount;
        doctor = game.loadImage("Assets/doctor.png");
        towerHovered = false;
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
        game.image(this.doctor, x, y,size,size);
        if(towerHovered) {
            game.stroke(255, 0, 0);
            game.strokeWeight(2);
            game.fill(0, 0);
            game.ellipse(x + size / 2, y + size / 2, (int) range * 2 + 2, (int) range * 2 + 2);
            game.stroke(0, 0, 0);
            game.strokeWeight(1);
            towerHovered = false;
        }
    }

    public void shoot(ArrayList<Tank> tanks, Game game){
        Tank currTarget = findTarget(tanks);
        if(currTarget != null){
            Bullet bullet = new Bullet(damage, x+(size/2), y+(size/2), (currTarget.getX()-x)/10, (currTarget.getY()-y)/10, 10, game);
            game.addToBulletList(bullet);
        }
    }


    public Tank findTarget(ArrayList<Tank> tanks){
        float distance;
        float closest = 1000;
        Tank target = null;

        for(Tank tank : tanks){
            float run = Math.abs(x-tank.getX());
            float rise = Math.abs(y-tank.getY());
            distance = rise+run;
            if (distance < closest){
                if (range >= distance){
                    closest = distance;
                    target = tank;
                }
            }
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
            if(range <= 225) {
                range += 25;
            }else range = 250;
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
    public void setTowerHovered(boolean towerHovered) {
        this.towerHovered = towerHovered;
    }
}
