package ctr;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter
{
    public static double x;
    public static double y;
    public static boolean pressed;
    public static boolean pressedConsumed;
    
    @Override
    public void mouseMoved(MouseEvent e) 
    {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        pressed = true;
        pressedConsumed = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        pressed = false;
    }
        
}
