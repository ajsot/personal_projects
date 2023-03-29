import java.awt.*;
import java.awt.image.BufferedImage;

public class Fireball extends Sprite
{
    static BufferedImage image;

    double vertVelocity;

    public Fireball(int x, int y)
    {
        this.x = x;
        this.y = y;
        if (image == null)
        {
            image = View.loadImage("fireball.png");
        }
        this.w = image.getWidth();
        this.h = image.getHeight();
    }

    boolean isPipe()
    {
        return false;
    }

    boolean isGoomba()
    {
        return false;
    }

    @Override
    boolean isFireball()
    {
        return true;
    }

    void update()
    {
        vertVelocity += 9.8;
        y += vertVelocity;
        x += 30;

        if(y > 400 - h) // if below the ground
        {
            y = 400 - h; // snap back to the ground
            vertVelocity = -48;
        }
    }

    void draw(Graphics g, int scrollPos)
    {
        g.drawImage(image, x- scrollPos, y, w, h, null);
    }

    void avoidCollision(Sprite p)
    {

    }

    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x",x);
        ob.add("y",y);
        ob.add("w",w);
        ob.add("h",h);
        return ob;
    }

    boolean clickExisting(int mouseX, int mouseY) {
        return false;
    } // fireball doesn't need to avoid collisions

    @Override
    public void setPreviousPosition()
    {

    }

    @Override
    public String toString()
    {
        return "Fireball (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }
}
