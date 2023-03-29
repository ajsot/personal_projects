import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Mario extends Sprite
{
    int previous_x, previous_y;
    double vertVelocity;
    int numFramesInAir = 0;
    boolean rightFacing = true;
    int currentImage;
    static BufferedImage[] images;

    boolean isPipe() { return false; }

    boolean isGoomba() {
        return false;
    }

    @Override
    boolean isFireball() {
        return false;
    }

    public Mario(int x, int y)
    {
        this.x = x;
        this.y = y;
        currentImage = 0;
        images = new BufferedImage[5];
        for (int i = 0; i< images.length; i++)
        {
            if (images[i] == null) // lazy loading
            {
                images[i] = View.loadImage("mario" + (i+1) + ".png");
            }

        }
        this.w = images[0].getWidth();
        this.h = images[0].getHeight();
    }

    @Override
    public String toString()
    {
        return "Mario (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }

    public void setPreviousPosition()
    {
        previous_x = x;
        previous_y = y;
    }

    public void checkJump()
    {
       if (numFramesInAir <= 5)
       {
           vertVelocity = -25;
       }
    }

    void changeImageState()
    {
        currentImage++;
        if (currentImage > 4)
        {
            currentImage = 0;
        }
        //System.out.println("Current image = " + currentImage );
    }

    public void draw(Graphics g, int scrollPos)
    {
        if (rightFacing)
        {
            g.drawImage(images[currentImage], x - scrollPos, y, w, h, null);  // draw facing right
        }
        else
        {
            g.drawImage(images[currentImage], x - scrollPos + w, y, -w, h, null); // draw facing left
        }
    }

    void avoidCollision(Sprite p)
    {
        if (x + w >= p.x && previous_x + w <= p.x) // coming from left
        {
            x = p.x - w; // set left of pipe
        }
        if (x <= p.x + p.w && previous_x >= p.x + p.w) // coming from right
        {
            x = p.x + p.w; // set right of pipe
        }
        if (y + h >= p.y && previous_y + h <= p.y) // coming from up
        {
            y = p.y - h; // set above pipe
            vertVelocity = 0;
            numFramesInAir = 0;
        }
        if (y <= p.y + p.h && previous_y >= p.y + p.h) // coming from down
        {
            y = p.y + p.h; // set below pipe
            vertVelocity = 0;
        }
    }

    @Override
    boolean clickExisting(int mouseX, int mouseY) {
        return false;
    }

    void update()
    {
        vertVelocity += 9.8;   // gravity
        y += vertVelocity;  // update position
        numFramesInAir ++;

        if(y > 400 - h) // if below the ground
        {
            vertVelocity = 0;
            y = 400 - h; // snap back to the ground
            numFramesInAir = 0;
        }
    }
}