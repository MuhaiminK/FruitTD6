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
            if (this.getX() == 225 && this.getY() == 400) {
                this.setxSpeed(0);
                this.setySpeed(-1);
            }else if (this.getX() == 225 && this.getY() == 250) {
                this.setxSpeed(1);
                this.setySpeed(0);
            }else if (this.getX() == 375 && this.getY() == 250) {
                this.setxSpeed(0);
                this.setySpeed(1);
            }else if (this.getX() == 375 && this.getY() == 575) {
                this.setxSpeed(-1);
                this.setySpeed(0);
            }else if (this.getX() == 175 && this.getY() == 575) {
                this.setxSpeed(0);
                this.setySpeed(1);
            }else if (this.getX() == 175 && this.getY() == 725) {
                this.setxSpeed(1);
                this.setySpeed(0);
            }else if (this.getX() == 675 && this.getY() == 725) {
                this.setxSpeed(0);
                this.setySpeed(-1);
            }else if (this.getX() == 675 && this.getY() == 525) {
                this.setxSpeed(-1);
                this.setySpeed(0);
            }else if (this.getX() == 525 && this.getY() == 525) {
                this.setxSpeed(0);
                this.setySpeed(-1);
            }else if (this.getX() == 525 && this.getY() == 325) {
                this.setxSpeed(1);
                this.setySpeed(0);
            }else if (this.getX() == 725 && this.getY() == 325) {
                this.setxSpeed(0);
                this.setySpeed(-1);
            }else if (this.getX() == 725 && this.getY() == 125) {
                this.setxSpeed(1);
                this.setySpeed(0);
            }
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
