package packagee;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Game implements IGame {

    int row;        // rzędy
    int col;        // kolumny
    int bombs;      // ilość bomb
    int fields;     // liczba pól nieodkrytych
    int counter;    // liczba zaznaczonych pól
    boolean end;    // boolean czy gra jest zakończona

    int [][]board;  // tablica pól z ich wartościami 0-8 9 - bomba
    int [][]marked; // tablica pól z ich aktualnym stanem 0-normalny 1-f. jest b 2-f. może być b 3-odkryte

    static public class Builder{
        int row;
        int col;
        int bombs;
        int[][] board;
        int[][] marked;

        Builder row(int row){
            this.row = row;
            return this;
        }
        Builder col(int col){
            this.col = col;
            return this;
        }
        Builder bombs(int bombs){
            this.bombs = bombs;
            return this;
        }

        int[][] build_board(int bombs){         // funkcja ustawiająca wszystkie wartości w tablicy
            int temp = 0;
            Random generator = new Random();
            while(temp < bombs){                        // najpierw losujemy dowolną ilość bomb
                int bombsRow = generator.nextInt(this.row);
                int bombsCol = generator.nextInt(this.col);
                if(this.board[bombsRow][bombsCol] != 9){
                    this.board[bombsRow][bombsCol] = 9;
                    temp++;
                }
            }
            for(int rr=0; rr<this.row; rr++){           // następnie ustalamy pozostałe wartości licząc bomby w sąsiedztwie
                for(int cc=0; cc<this.col; cc++){
                    if(this.board[rr][cc] == 9){
                        for(int ccc=cc-1; ccc<cc+2; ccc++){
                            for(int rrr=rr-1;rrr<rr+2; rrr++){
                                if(this.col > ccc && ccc>=0 && this.row > rrr && rrr>=0 && this.board[rrr][ccc] != 9){
                                    this.board[rrr][ccc]++;
                                }
                            }
                        }
                    }
                }
            }
            return this.board;
        }

        public Game build(){
            Game newgame = new Game();
            this.marked = new int[this.row][this.col];
            this.board = new int[this.row][this.col];
            newgame.row = this.row;
            newgame.col = this.col;
            newgame.bombs = this.bombs;
            newgame.fields = this.row * this.col;
            newgame.counter = 0;
            newgame.end = false;

            newgame.marked = new int[this.row][this.col];      // domyślną wartością dla int jest 0
            newgame.board = new int[this.row][this.col];
            newgame.board = this.build_board(this.bombs);

            for(int i = 0 ; i< this.row ; i++){
                for(int ii = 0 ; ii< this.col ; ii++)
                    System.out.print(newgame.board[i][ii]);
                System.out.println();
            }
            return newgame;
        }
    }

    public boolean check_neighbours(int r, int c, ArrayList<Point> retval){         // funkcja sprawdzająca do odsłonięcia sąsiadów gdzy odkryło się puste pole 0
        if(this.board[r][c] == 9){
            this.end = true;
            return false;
        }
        else{
            this.fields -= 1;
            this.marked[r][c] = 3;
            if(this.board[r][c] == 0){
                for(int rr = r-1; rr<r+2; rr++){
                    for(int cc = c-1; cc<c+2; cc++){
                        if(this.col > cc && cc>=0 && this.row > rr && rr>=0){
                            if(this.marked[rr][cc] != 3){
                                this.check_neighbours(rr,cc,retval);
                            }
                        }
                    }
                }
            }
            retval.add(new Point(r,c));
        }
        return true;
    }

    public boolean won(){                   // funkcja sprawdzająca czy gra została już wygrana
        if(this.fields == this.bombs){
            this.end = true;
            return true;
        }
        else if(this.counter == this.bombs){
            boolean a=false;
            for(int r=0; r<this.row; r++){
                for(int c=0; c<this.col; c++){
                    if(this.board[r][c] == 9 && this.marked[r][c] != 1){
                        a=true;
                        break;
                    }
                }
                if(a) break;
            }
            if(!a){
                this.end = true;
                return true;
            }
        }
        return false;
    }

    public ArrayList<Point> allBombs(){                   // funkcja zwracająca Arrayliste z współrzędnymi wszystkich bomb
        ArrayList<Point> bombs = new ArrayList<>();
        for(int r = 0; r < this.row; r++){
            for(int c = 0; c < this.col; c++){
                if(this.board[r][c]==9)
                    bombs.add(new Point(r,c));
            }
        }
        return bombs;
    }

    public void addMarked(int r, int c){
        this.marked[r][c] ++;
        this.marked[r][c] %= 3;
    }
    public int getMarked(int r, int c){
        return this.marked[r][c];
    }
    public void addCounter(){
        this.counter++;
    }
    public void subCounter(){
        this.counter--;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getBombs() {
        return bombs;
    }
    public int getCounter() {
        return counter;
    }
    public boolean isEnd() {
        return end;
    }
    public int returnValue(int r, int c){
        return this.board[r][c];
    }

}