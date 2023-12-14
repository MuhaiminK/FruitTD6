import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Bullet{
    private int damage, x, y, xSpeed, ySpeed, size, age;
    private boolean alive;
    private PImage pill;

    public Bullet(int dmg, int x, int y, int xSpeed, int ySpeed, int size, PApplet game){
        damage = dmg;
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        alive = true;
        pill = game.loadImage("Assets/pill.png");
        age = 0;
    }

    public void update(PApplet game, ArrayList<Tank> tankList){
        if(alive) {
            age++;
            x += xSpeed;
            y += ySpeed;
            draw(game);
            for(Tank tank : tankList){
                if(colliding(tank)){
                    hit(tank);
                }
            }
        }
    }

    public boolean colliding(Tank target){
        return  size + target.getSize()-2 >= distance(target);
    }
    private double distance(Tank target){
        float run = Math.abs(x-target.getX());
        float rise = Math.abs(y-target.getY());
        return rise+run;
    }

    public void draw(PApplet game){
        game.fill(180,180,0);
        //game.ellipse(x,y,size,size);
        game.image(this.pill, x-size/2, y-size/2,size*2,size*2);
    }


    public void hit(Tank target){
        target.getHit(damage);
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

