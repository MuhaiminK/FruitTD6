import processing.core.PApplet;

public class Tank {
    private int health, x, y, xSpeed, ySpeed, size, startingHp;
    private boolean alive, boss;

    public Tank(int hp, int x, int y, int xS, int yS, int size, boolean boss){
        health = hp;
        startingHp = hp;
        this.x = x;
        this.y = y;
        xSpeed = xS;
        ySpeed = yS;
        this.size = size;
        alive = true;
        this.boss = boss;
    }

    public int update(PApplet PApplet){
        if(alive){
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
        game.ellipse(x,y,size,size);
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
}
