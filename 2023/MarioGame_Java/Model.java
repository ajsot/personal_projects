import java.util.ArrayList;
import java.util.Iterator;

class Model
{
    ArrayList<Sprite> sprites;
    Mario mario;
    Model()
    {
        sprites = new ArrayList<Sprite>();
        mario = new Mario (50,400);
        sprites.add(mario);
    }
    Json marshal()
    {
        Json ob = Json.newObject();

        Json tmpListPipes = Json.newList();
        ob.add("pipes", tmpListPipes);

        Json tmpListGoombas = Json.newList();
        ob.add("goombas",tmpListGoombas);

        for (int i = 0; i < sprites.size(); i++)
        {
            if (sprites.get(i).isPipe())
            {
                tmpListPipes.add(((Pipe)sprites.get(i)).marshal());
            }
            if (sprites.get(i).isGoomba())
            {
                tmpListGoombas.add(((Goomba)sprites.get(i)).marshal());
            }
        }
        return ob;
    }


    void unmarshal(Json ob)
    {
        sprites = new ArrayList<Sprite>();
        sprites.add(mario);

        Json tmpListPipes = ob.get("pipes");
        Json tmpListGoombas = ob.get("goombas");

        for (int i = 0; i < tmpListPipes.size(); i++)
        {
            sprites.add(new Pipe(tmpListPipes.get(i)));
        }

        for (int i = 0; i < tmpListGoombas.size(); i++)
        {
            sprites.add(new Goomba(tmpListGoombas.get(i)));
        }
    }

    // If not clicking on existing pipe, then create new pipe and add it to array
    public void addPipe(int x, int y)
    {
        boolean foundPipe = false;
        for (int i = 0; i < sprites.size(); i++)
        {
            if (sprites.get(i).isPipe() == true)
            {
                if (sprites.get(i).clickExisting(x,y) == true)
                {
                    foundPipe = true;
                    sprites.remove(i);
                }
            }
        }
        if (!foundPipe)
        {
            sprites.add(new Pipe(x,y));
        }
    }

    public void addGoomba(int x, int y)
    {
        sprites.add(new Goomba(x,y));
    }

    public void addFireball()
    {
        sprites.add(new Fireball(mario.x + mario.w, mario.y + mario.h));
    }

    boolean isThereACollision(Sprite sprite1, Sprite sprite2)
    {
        int sprite1Left = sprite1.x;
        int sprite1Right = sprite1.x + sprite1.w;
        int sprite1Top = sprite1.y;
        int sprite1Bottom = sprite1.y + sprite1.h;
        int sprite2Left = sprite2.x;
        int sprite2Right = sprite2.x + sprite2.w;
        int sprite2Top = sprite2.y;
        int sprite2Bottom = sprite2.y + sprite2.h;

        // if he is not colliding
        //return false;
        if(sprite1Right < sprite2Left)
            return false;
        if(sprite1Left > sprite2Right)
            return false;
        if(sprite1Bottom < sprite2Top) // assumes bigger is downward
            return false;
        if(sprite1Top > sprite2Bottom) // assumes bigger is downward
            return false;

        // not NOT colliding
        return true;
    }

    public void update()
    {
        //mario.update();
        // check if there is a collision and get out of pipe
        Iterator<Sprite> it = sprites.iterator();  // Iterator
        while (it.hasNext())
        {
            Sprite s = it.next();
            s.update();
            if (s.isPipe()) // pipe
            {
                boolean check1 = isThereACollision(mario,s); // check if colliding with mario
                if (check1 == true)
                {
                    mario.avoidCollision(s);
                }

                for (int i = 0; i < sprites.size(); i++)
                {
                    if (sprites.get(i).isGoomba() == true)
                    {
                        boolean check2 = isThereACollision(s, (Goomba)sprites.get(i)); // check if colliding with goomba
                        if (check2 == true)
                        {
                            ((Goomba)sprites.get(i)).avoidCollision(s); // goomba avoids collision
                        }
                    }
                }
            }
            if (s.isGoomba()) // goomba
            {
                for (int i = 0; i < sprites.size(); i++)
                {
                    Sprite tempSprite = sprites.get(i);
                    if (tempSprite.isFireball())
                    {
                        boolean check = isThereACollision(s, tempSprite);
                        if (check == true && tempSprite.x <= mario.x + 400 - tempSprite.w) // if fireball goes just beyond view it doesnt burn goombas
                        {
                            s.onFire = true;
                        }
                    }
                }
                if (s.onFire == true)
                {
                    s.timeOnFire ++;
                }
                if (s.timeOnFire >= 20)
                {
                    it.remove(); // remove goomba if it has been on fire for 20
                }
            }
            if (s.isFireball()) // fireball
            {
                if (s.x > mario.x + 400) // fireball has moved out of view
                {
                    it.remove();
                }
            }
         }
    }
}