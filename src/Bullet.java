import processing.core.PApplet;
import processing.core.PImage;

public class Bullet {
    protected int damage, x, y, xSpeed, ySpeed, size;
    protected Tank target;
    protected boolean alive;
    private PImage pill;

    public Bullet(Tank tank, int dmg, int x, int y, int xSpeed, int ySpeed, int size, PApplet game){
        damage = dmg;
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        target = tank;
        alive = true;
        pill = game.loadImage("Assets/pill.png");
    }

    public void update(PApplet game){
        if(alive) {
            x += xSpeed;
            y += ySpeed;
            if(colliding()){
                hit();
            }
        }
    }

    public boolean colliding(){
        return  size + target.getSize() >= distance();
    }
    private double distance(){
        float run = Math.abs(x-target.getX());
        float rise = Math.abs(y-target.getY());
        return rise+run;
    }

    public void draw(PApplet game){
        game.fill(180,180,0);
        game.image(this.pill, x-size/2, y-size/2,size*2,size*2);
    }


    public void hit(){
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

