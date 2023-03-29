import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Pipe extends Sprite
{
    static BufferedImage image;

    boolean isPipe() { return true; }

    boolean isGoomba() {
        return false;
    }

    @Override
    boolean isFireball() {
        return false;
    }

    public Pipe(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.w = 55;
        this.h = 400;
        if (image == null)
        {
            image = View.loadImage("pipe.png");
        }
    }

    public Pipe(Json ob)
    {
        x = (int) ob.getLong("x");
        y = (int) ob.getLong("y");
        w = (int) ob.getLong("w");
        h = (int) ob.getLong("h");
        if (image == null)
        {
            image = View.loadImage("pipe.png");
        }
    }

    @Override
    public String toString()
    {
        return "Pipe (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }

    boolean clickExisting(int mouseX, int mouseY) // check if existing pipe is being clicked on
    {
        if( (mouseX >= x && mouseX <= x + w) && (mouseY >= y && mouseY <= h + w))
        {
            return true;
        }
        return false;
    }

    @Override
    public void setPreviousPosition()
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

    public void draw(Graphics g, int scrollPos)
    {
        g.drawImage(image, x - scrollPos, y, null);
    }

    @Override
    void avoidCollision(Sprite p) {     } // pipes don't move

    void update(){}
}
