package packagee;

class Facade {
    int colCheck(int col) throws DataException {
        if(col < 2 || col > 13) {
            throw new DataException("Niepoprawne dane wejściowe");
        }
        return col;
    }
    int rowCheck(int row) throws DataException {
        if(row < 2 ||  row > 13) {
            throw new DataException("Niepoprawne dane wejściowe");
        }
        return row;
    }
    int bombCheck(int bomb, int row, int col) throws DataException {
        if(bomb <= 0 || bomb >= row * col) {
            throw new DataException("Niepoprawne dane wejściowe");
        }
        return bomb;
    }
}
