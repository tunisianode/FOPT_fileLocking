package pp.filelocking;

import java.util.ArrayList;
import java.util.List;


//todo: muss versuchen mit Likedlist dieser aufgabe zu lösen
public class File extends Thread
{
    //Attribute:
    public int length;
    List<Intervall> IntervallList = null;

    //Falls dem Konstruktor der Klasse File eine negative Länge übergeben wird, muss eine
    //Ausnahme vom Typ IllegalArgumentException ausgelöst werden.
    public File(int length)
    {
        if (length < 0)
        {
            throw new IllegalArgumentException();
        }
        this.length = length;
        IntervallList = new ArrayList<Intervall>();
    }

    public static void main(String[] args)
    {
        File file1 = new File(100);
        file1.start();
        file1.lock(50, 60);
        file1.lock(61, 75);
        file1.lock(75, 80);
        file1.unlock(50, 60);
    }

    public synchronized void lock(int begin, int end)
    {
        if (begin < 0 || end < 0 || begin > end || end > length)
        {
            throw new IllegalArgumentException("Error bei Lock methode:  begin < 0 || end < 0 || begin > end || end " + ">" + " length");
        }

        Intervall intervall = new Intervall(begin, end);

        for (Intervall item : IntervallList)
        {
            if ((intervall.getStart() >= item.getStart() && intervall.getEnd() <= item.getEnd()) || (intervall.getStart() >= item.getStart() && intervall.getStart() <= item.getEnd()) || (intervall.getEnd() >= item.getStart() && intervall.getEnd() <= item.getEnd()))
            {
                //throw new IllegalArgumentException("Uberlappung bei das Intervall [" + begin + "," + end + "]");
                //                          Uberlappung
                try
                {
                    wait();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }


        this.IntervallList.add(new Intervall(begin, end));
        notify();
    }

    public synchronized void unlock(int begin, int end)
    {
        //auserhalb der bereich
        if (begin < 0 || end < 0 || begin > end || end > length)
        {
            throw new IllegalArgumentException("Error bei Lock methode:  begin < 0 " + "|| end < 0 || begin > end || " +
                    "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + ""
                    + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" +
                    "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + ""
                    + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" +
                    "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "end "
                    + "" + "" + ">" + " " + "" + "" + "length");
        }


        //wenn der Intervall ist nicht in der Sperre Liste
        if (IntervallList.contains(new Intervall(begin, end)))
        {
            throw new IllegalArgumentException("The interval [" + begin + "," + end + "] ist in the list");
        }
        this.IntervallList.remove(new Intervall(begin, end));
        notifyAll();
    }

    //prüft die Gültichkeit von ein eingegebene Intervall
    //todo: der check muss ausgelagert werden
    private Boolean IsValid()
    {
        return true;
    }
}

class Intervall
{

    private int start, end;
    private Boolean isLocked;

    public Intervall(int start, int end)
    {
        this.start = start;
        this.end = end;
        this.isLocked = true;
    }

    public void setLocked(Boolean locked)
    {
        isLocked = locked;
    }

    public int getStart()
    {
        return start;
    }

    public int getEnd()
    {
        return end;
    }


}