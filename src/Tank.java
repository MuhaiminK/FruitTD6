import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Tank extends  PApplet{
    private int health, x, y, xSpeed, ySpeed, size, startingHp, index;
    private boolean alive, boss;
    private PImage apple;
    private int[] waypoints = {225,400,225,250,375,250,375,575,175,575,175,725,675,725,675,525,525,525,525,325,725,325,725,125,900,125};

    public Tank(int hp, int x, int y, int xS, int yS, int size, boolean boss, int index, PApplet game){
        health = hp;
        startingHp = hp;
        this.x = x;
        this.y = y;
        xSpeed = xS;
        ySpeed = yS;
        this.size = size;
        alive = true;
        this.boss = boss;
        this.index = index;
        if(boss){
            apple = game.loadImage("Assets/greenApple.png");
        }else {
            apple = game.loadImage("Assets/apple.png");
        }
    }

    public int update(PApplet PApplet){
        if(alive){
            pathfind();
            x += xSpeed;
            y += ySpeed;
            draw(PApplet);
            if (health <= 0){
                alive = false;
                return 20;
            } else return 0;
        }else{
            return 0;
        }
    }

    public void getHit(int dmg){
        health -= dmg;
    }

    public void draw(PApplet game){
        if(boss){
            game.fill(150,0,200);
        }else{
            game.fill(150,0,0);
        }
        game.image(this.apple, x-size/2, y-size/2, size, size);
        //game.ellipse(x,y,size,size);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getxSpeed() {
        return xSpeed;
    }
    public int getySpeed() {
        return ySpeed;
    }
    public int getHealth() {
        return health;
    }
    public boolean isAlive() {
        return alive;
    }
    public boolean isBoss() {
        return boss;
    }
    public int getSize() {
        return size;
    }
    public int getStartingHp() {
        return startingHp;
    }
    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    public boolean contains(int x, int y){
        return (Math.abs(this.x-x)+Math.abs(this.y-y)) <= size;
    }
    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    public void pathfind() {
        if (this.getX() == waypoints[index] && this.getY() == waypoints[index + 1]) {
            index += 2;
        }
        if (this.getX() == waypoints[index] && this.getY() < waypoints[index + 1]) {
            this.setxSpeed(0);
            this.setySpeed(1);
        }else if (this.getX() == waypoints[index] && this.getY() > waypoints[index + 1]) {
            this.setxSpeed(0);
            this.setySpeed(-1);
        }else if (this.getX() < waypoints[index] && this.getY() == waypoints[index + 1]) {
            this.setxSpeed(1);
            this.setySpeed(0);
        }else if (this.getX() > waypoints[index] && this.getY() == waypoints[index + 1]) {
            this.setxSpeed(-1);
            this.setySpeed(0);
        }
    }

    public int getIndex() {
        return index;
    }
}
