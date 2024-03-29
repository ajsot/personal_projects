import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	Model model = new Model();
	Controller controller = new Controller(model);
	View view = new View(controller, model);
	public Game()
	{

		this.setTitle("A5"); // Title
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}

	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 63 milliseconds = roughly 25 frames per second
			try
			{
				Thread.sleep(63);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args)
 	{
		Game g = new Game();
		g.run();
	}
}
