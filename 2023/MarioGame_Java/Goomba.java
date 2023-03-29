import java.awt.*;
import java.awt.image.BufferedImage;

public class Goomba extends Sprite
{
    int previous_x, previous_y;
    static BufferedImage image;
    static BufferedImage image2;
    int direction = 1;

    double vertVelocity;
    boolean moveRight = true;

    boolean isPipe() {
        return false;
    }

    boolean isGoomba()
    {
        return true;
    }

    @Override
    boolean isFireball() {
        return false;
    }

    public Goomba(int x, int y)
    {
        this.x = x;
        this.y = y;
        if (image == null)
        {
            image = View.loadImage("goomba.png");
        }
        this.w = image.getWidth();
        this.h = image.getHeight();
    }

    public Goomba(Json ob)
    {
        x = (int) ob.getLong("x");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        h = (int) ob.getLong("h");
        if (image == null)
        {
            image = View.loadImage("goomba.png");
        }
    }

    void update()
    {
        setPreviousPosition();
        if (!onFire)
        {
          x += direction * 4.0;
        }
        
        vertVelocity += 9.8;
        y += vertVelocity;

        if(y > 400 - h) // if below the ground
        {
            vertVelocity = 0;
            y = 400 - h; // snap back to the ground
        }
    }

    void draw(Graphics g, int scrollPos)
    {
        if (!onFire)
        {
            g.drawImage(image, x- scrollPos, y, w, h, null);
        }
        else
        {
            if (image2 == null)
            {
                image2 = View.loadImage("goomba_fire.png");
            }
            g.drawImage(image2, x - scrollPos, y, w, h, null);
        }
    }

    void avoidCollision(Sprite p)
    {
        if (x + w >= p.x && previous_x + w <= p.x) // coming from left
        {
            x = p.x - w; // set left of pipe
            //moveRight = !moveRight;
            direction *= -1;        //added, to change direction of goomba
        }
        if (x <= p.x + p.w && previous_x >= p.x + p.w) // coming from right
        {
            x = p.x + p.w; // set right of pipe
            //moveRight = !moveRight;
            direction *= -1;        //added, to change direction of goomba
        }
        if (y + h >= p.y && previous_y + h <= p.y) // coming from up
        {
            y = p.y - h; // set above pipe
            vertVelocity = 0;
        }
        if (y <= p.y + p.h && previous_y >= p.y + p.h) // coming from down
        {
            y = p.y + p.h; // set below pipe
            vertVelocity = 0;
        }
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

    boolean clickExisting(int mouseX, int mouseY)
    {
        return false;
    }

    public void setPreviousPosition()
    {
        previous_x = x;
        previous_y = y;
    }

    @Override
    public String toString()
    {
        return "Goomba (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }
}
