import processing.core.PApplet;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Tower {
    private double range;
    private int damage, upgradeCost, upgradeCount, x, y, tick;
    private double fireRate;

    public Tower(int dmg, double fr, int upgradeCost, int x, int y, int range){
        damage = dmg;
        fireRate = fr;
        this.upgradeCost = upgradeCost;
        this.x = x;
        this.y = y;
        this.range = range;
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
        game.rect(x,y,30,30);
    }

    public void shoot(ArrayList<Tank> tanks, Game game){
        Tank currTarget = findTarget(tanks);
        if(currTarget != null){
            Bullet bullet = new Bullet(currTarget, damage, x, y, (currTarget.getX()-x)/25, (currTarget.getY()-y)/25, 10);
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
    public void upgrade(Game game){
        upgradeCount++;
        game.addMoney(-upgradeCost);
        upgradeCost+=50;
        damage+=15;
        range+=150;
        fireRate+=1;

    }
}
