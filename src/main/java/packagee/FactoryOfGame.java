package packagee;

class FactoryOfGame {
    static Game letsplay(int numberOfGame, boolean gModOn){
        if(numberOfGame == 0 && !gModOn){
            return new Game.Builder().bombs(3).col(5).row(5).build();
        }
        else if(numberOfGame == 1 && !gModOn){
            return new Game.Builder().bombs(20).col(8).row(8).build();
        }
        else if(numberOfGame == 2 && !gModOn){
            return new Game.Builder().bombs(124).col(12).row(12).build();
        }
        else if(numberOfGame == 0){
            return new GMode.Builder().bombs(3).col(5).row(5).build();
        }
        else if(numberOfGame == 1){
            return new GMode.Builder().bombs(30).col(8).row(8).build();
        }
        else if(numberOfGame == 2){
            return new GMode.Builder().bombs(124).col(12).row(12).build();
        }

        return new Game.Builder().bombs(3).col(5).row(5).build();
    }
}
