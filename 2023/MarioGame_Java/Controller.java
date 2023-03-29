import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	boolean keyCtrl = true;
	boolean editMode = false;
	boolean goombaMode = false;

	Controller(Model m)
	{
		model = m;
		Json j = Json.load("map.json"); // load map as soon as program starts
		model.unmarshal(j);
		System.out.println("File is loaded.");
	}

	void setView(View v)
	{
		view = v;
	}

	public void actionPerformed(ActionEvent e) {	}

	public void mousePressed(MouseEvent e)
	{
		if (editMode == true)
		{
			if (goombaMode == true)
			{
				model.addGoomba(e.getX()+view.scrollPos, e.getY());
			}
			else
			{
				model.addPipe(e.getX()+view.scrollPos, e.getY());
			}
		}
	}

	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SPACE: keySpace = true; break;
			case KeyEvent.VK_CONTROL: keyCtrl = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
			case KeyEvent.VK_CONTROL: keyCtrl = false; break;
			case KeyEvent.VK_ESCAPE: System.exit(0);
		}
		char c = Character.toLowerCase(e.getKeyChar());
		if (c == 'q')	// exit program
		{
			System.exit(0);
		}
		if (c == 's')	// save to json file
		{
			Json saveObject = model.marshal();
			saveObject.save("map.json");
			System.out.println("File is saved.");
		}
		if (c== 'l')	// load json file
		{
			Json j = Json.load("map.json");
			model.unmarshal(j);
			System.out.println("File is loaded.");
		}
		if (c == 'e')
		{
			editMode = !editMode;
			System.out.println("Edit mode: " + editMode);
		}
		if (c == 'g')
		{
			if (editMode == true)
			{
				goombaMode = !goombaMode;
				System.out.println("Goomba mode: " + goombaMode);
			}
		}
	}

	public void keyTyped(KeyEvent e)
	{

	}
	void update()
	{
		// for (int i = 0; i < model.sprites.size(); i++)
		// {
		// 	model.sprites.get(i).setPreviousPosition();
		// }
		model.mario.setPreviousPosition();	//changed
		if (keyRight)
		{
			model.mario.rightFacing = true;
			model.mario.x += 5;
			model.mario.changeImageState();
		}
		if (keyLeft)
		{
			model.mario.rightFacing = false;
			model.mario.x -= 5;
			model.mario.changeImageState();
		}
		if (keySpace)
		{
			model.mario.checkJump();
		}
		if (!keyCtrl)
		{
			model.addFireball();
			keyCtrl = true;
		}
	}
}
