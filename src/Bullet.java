import processing.core.PApplet;

public class Bullet {
    private int damage, x, y, xSpeed, ySpeed, size;
    private Tank target;
    private boolean alive;

    public Bullet(Tank tank, int dmg, int x, int y, int xSpeed, int ySpeed, int size){
        damage = dmg;
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        target = tank;
        alive = true;
    }

    public void update(PApplet game){
        if(alive) {
            x += xSpeed;
            y += ySpeed;
            draw(game);
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
        game.ellipse(x,y,size,size);
    }


    public void hit(){
        target.getHit(damage);
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
