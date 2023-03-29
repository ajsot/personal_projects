import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

class View extends JPanel
{
	Model model;
	int scrollPos = 0;

	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
	}

	static BufferedImage loadImage(String filename)
	{
		BufferedImage im = null;
		try
		{
			im = ImageIO.read(new File(filename));
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		System.out.println("Successfully loaded " + filename + " image.");
		return im;
	}

	// Json Marshal()
	// {
	// 	Json ob = Json.newObject();
	// 	ob.add("scroll", scrollPos);
	// 	return ob;
	// }

	public void paintComponent(Graphics g)
	{
		scrollPos = model.mario.x - 100; // mario.x - 100
		g.setColor(new Color(128, 255, 255)); // background
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			s.draw(g, scrollPos);
		}

		g.setColor(new Color(136, 107, 34)); // ground
		g.fillRect(0,400,2000,100);
	}
}
