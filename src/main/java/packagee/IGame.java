package packagee;
import java.awt.*;
import java.util.ArrayList;
public interface IGame {

    int getCounter();

    int getRow();

    int getCol();

    int getBombs();

    boolean isEnd();

    int returnValue(int r, int c);

    boolean check_neighbours(int r, int c, ArrayList<Point> retval);

    boolean won();

    ArrayList<Point> allBombs();

    void addMarked(int r, int c);

    int getMarked(int r, int c);

    void addCounter();

    void subCounter();
}
