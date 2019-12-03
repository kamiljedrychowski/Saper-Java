package packagee;

public class GMode extends Game {

    static public class Builder extends Game.Builder {
        public GMode build() {
            GMode newgame = new GMode();
            this.marked = new int[this.row][this.col];
            this.board = new int[this.row][this.col];
            newgame.row = this.row;
            newgame.col = this.col;
            newgame.bombs = this.bombs;
            newgame.fields = this.row * this.col;
            newgame.counter = 0;
            newgame.end = false;

            newgame.marked = new int[this.row][this.col];
            newgame.board = new int[this.row][this.col];
            newgame.board = this.build_board(this.bombs);

            for (int i = 0; i < this.row; i++) {
                for (int ii = 0; ii < this.col; ii++) {
                    if (newgame.board[i][ii] == 9) {
                        newgame.marked[i][ii] = 5;
                    }
                    System.out.print(newgame.board[i][ii]);
                }
                System.out.println();
            }
            return newgame;
        }
    }


    public void addMarked(int r, int c) {
        if (this.board[r][c] == 9) {
            this.marked[r][c]++;
            if (this.marked[r][c] == 8)
                this.marked[r][c] = 5;
        } else {
            this.marked[r][c]++;
            this.marked[r][c] %= 3;
        }
    }

    public boolean won() {                   // funkcja sprawdzająca czy gra została już wygrana
        if (this.fields == this.bombs) {
            this.end = true;
            return true;
        } else if (this.counter == this.bombs) {
            boolean a = false;
            for (int r = 0; r < this.row; r++) {
                for (int c = 0; c < this.col; c++) {
                    if (this.board[r][c] == 9 && this.marked[r][c] != 6) {
                        a = true;
                        break;
                    }
                }
                if (a) break;
            }
            if (!a) {
                this.end = true;
                return true;
            }
        }
        return false;
    }


}