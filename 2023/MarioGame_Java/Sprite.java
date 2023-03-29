import java.awt.*;

abstract public class Sprite
{
    int x, y, w, h;
    boolean onFire = false; // whether a goomba has collided with a fireball or not
    int timeOnFire = 0; // how long goomba has been on fire

    abstract boolean isPipe();

    abstract boolean isGoomba();

    abstract boolean isFireball();

    abstract void update();

    abstract void draw(Graphics g, int scrollPos);

    abstract void avoidCollision(Sprite p); // move to avoid collision with sprite p

    abstract boolean clickExisting(int mouseX, int mouseY); // click an existing sprite

    abstract public void setPreviousPosition();
}
