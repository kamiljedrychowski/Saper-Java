package packagee;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    private static final int BUTTON_PADDING = 4;
    private Game gra;
    private Button[][] buttonBoard;
    private TextField rowInp;
    private TextField colInp;
    private TextField bombInp;
    private Label bombAmount;
    private Label markedAmount;

    private Map<String, Image> icons = new HashMap<>();

    private void newGame(GridPane grid, Game game) {
        grid.getChildren().clear();
        bombAmount.setText(Integer.toString(game.getBombs()));

        markedAmount.setText(Integer.toString(gra.getCounter()));
        buttonBoard = new Button[game.getRow()][game.getCol()];
        for (int r = 0; r < game.getRow(); r++) {
            for (int c = 0; c < game.getCol(); c++) {
                Button button = new Button();
                if (gra.getMarked(r, c) == 5) {
                    button.setGraphic(new ImageView(icons.get("buttonx")));
                } else {
                    button.setGraphic(new ImageView(icons.get("button")));
                }

                button.setStyle("-fx-background-color: transparent;");
                final int rr = r;
                final int cc = c;

                button.setOnMouseClicked(event -> {
                    MouseButton b = event.getButton();

                    if (b == MouseButton.PRIMARY) {
                        if (gra.returnValue(rr, cc) == 9) {
                            button.setGraphic(new ImageView(icons.get("bomb")));
                        } else {
                            button.setGraphic(new ImageView(icons.get(Integer.toString(gra.returnValue(rr, cc)))));
                        }
                        button.setDisable(true);
                        if (gra.won()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Koniec gry");
                            alert.setHeaderText("WYGRANA!!!");
                            alert.setContentText(null);
                            alert.showAndWait();
                            endOfGame();
                        }
                        show_nummber(rr, cc);
                    }
                    else if (b == MouseButton.SECONDARY) {
                        if (gra.getMarked(rr, cc) == 0 || gra.getMarked(rr, cc) == 5) {
                            gra.addCounter();
                        }
                        else if (gra.getMarked(rr, cc) == 1 || gra.getMarked(rr, cc) == 6) {
                            gra.subCounter();
                        }
                        gra.addMarked(rr, cc);

                        if (gra.getMarked(rr, cc) == 0) {
                            button.setGraphic(new ImageView(icons.get("button")));
                        } else if (gra.getMarked(rr, cc) == 1) {
                            button.setGraphic(new ImageView(icons.get("d")));
                        } else if (gra.getMarked(rr, cc) == 2) {
                            button.setGraphic(new ImageView(icons.get("q")));
                        }
                        if (gra.getMarked(rr, cc) == 5) {
                            button.setGraphic(new ImageView(icons.get("buttonx")));
                        } else if (gra.getMarked(rr, cc) == 6) {
                            button.setGraphic(new ImageView(icons.get("dx")));
                        } else if (gra.getMarked(rr, cc) == 7) {
                            button.setGraphic(new ImageView(icons.get("qx")));
                        }


                        markedAmount.setText(Integer.toString(gra.getCounter()));
                        System.out.println(gra.getCounter());

                        if (gra.won()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Koniec gry");
                            alert.setHeaderText("WYGRANA!!!");
                            alert.setContentText(null);
                            alert.showAndWait();
                            endOfGame();
                        }
                    }
                });

                buttonBoard[r][c] = button;
                grid.add(button, c, r);
            }
        }
    }

    private void show_nummber(int r, int c) {        // funkcja do wyświetlania sąsiednich pól po otwarciu pola pustego
        if (!this.gra.isEnd()) {
            ArrayList<Point> retval = new ArrayList<>();
            if (this.gra.check_neighbours(r, c, retval)) {
                for (Point p : retval) {
                    this.buttonBoard[p.x][p.y].setGraphic(new ImageView(icons.get(Integer.toString(gra.returnValue(p.x, p.y)))));
                    this.buttonBoard[p.x][p.y].setDisable(true);
                }
                if (this.gra.won()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);       // można wrzucić to do funkcji bo się powtarza
                    alert.setTitle("Koniec gry");
                    alert.setHeaderText("WYGRANA!!!");
                    alert.setContentText(null);
                    alert.showAndWait();
                    endOfGame();
                }
            } else {
                this.buttonBoard[r][c].setGraphic(new ImageView(icons.get("bomb")));
                this.buttonBoard[r][c].setDisable(true);
                show();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);       // można wrzucić to do funkcji bo się powtarza
                alert.setTitle("Koniec gry");
                alert.setHeaderText("PRZEGRANA :( !!!");
                alert.setContentText(null);
                alert.showAndWait();
                endOfGame();
            }
        }
    }

    private void show() {        // funkcja do wyświetlenia wszystkich bomb po wciśnięciu bomby
        ArrayList<Point> temp = this.gra.allBombs();
        for (Point p : temp) {
            this.buttonBoard[p.x][p.y].setGraphic(new ImageView(icons.get("bomb")));
        }

    }

    private void endOfGame() {
        if (gra.isEnd()) {
            for (Button[] buttons : buttonBoard) {
                for (Button button : buttons) {
                    button.setDisable(true);
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        icons.put("0", new Image(getClass().getResourceAsStream("/icons/zero.png")));
        icons.put("1", new Image(getClass().getResourceAsStream("/icons/one.png")));
        icons.put("2", new Image(getClass().getResourceAsStream("/icons/two.png")));
        icons.put("3", new Image(getClass().getResourceAsStream("/icons/three.png")));
        icons.put("4", new Image(getClass().getResourceAsStream("/icons/four.png")));
        icons.put("5", new Image(getClass().getResourceAsStream("/icons/five.png")));
        icons.put("6", new Image(getClass().getResourceAsStream("/icons/six.png")));
        icons.put("7", new Image(getClass().getResourceAsStream("/icons/seven.png")));
        icons.put("8", new Image(getClass().getResourceAsStream("/icons/eight.png")));
        icons.put("bomb", new Image(getClass().getResourceAsStream("/icons/bomb.png")));
        icons.put("q", new Image(getClass().getResourceAsStream("/icons/question.png")));
        icons.put("d", new Image(getClass().getResourceAsStream("/icons/triangle.png")));
        icons.put("button", new Image(getClass().getResourceAsStream("/icons/button.png")));
        icons.put("buttonx", new Image(getClass().getResourceAsStream("/icons/buttonx.png")));
        icons.put("qx", new Image(getClass().getResourceAsStream("/icons/questionx.png")));
        icons.put("dx", new Image(getClass().getResourceAsStream("/icons/trianglex.png")));

        CheckBox godMode = new CheckBox("God mode ;)");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(BUTTON_PADDING));
        grid.setHgap(BUTTON_PADDING);
        grid.setVgap(BUTTON_PADDING);

        rowInp = new TextField();
        colInp = new TextField();
        bombInp = new TextField();
        Button newGame = new Button("Nowa gra");
        newGame.setOnAction(actionEvent -> {
            Facade facade = new Facade();
            try {
                if (godMode.isSelected()) {
                    gra = new GMode.Builder()
                            .col(facade.colCheck(Integer.parseInt(colInp.getText())))
                            .row(facade.rowCheck(Integer.parseInt(rowInp.getText())))
                            .bombs(facade.bombCheck(Integer.parseInt(bombInp.getText()), Integer.parseInt(rowInp.getText()), Integer.parseInt(colInp.getText())))
                            .build();
                } else {
                    gra = new Game.Builder()
                            .col(facade.colCheck(Integer.parseInt(colInp.getText())))
                            .row(facade.rowCheck(Integer.parseInt(rowInp.getText())))
                            .bombs(facade.bombCheck(Integer.parseInt(bombInp.getText()), Integer.parseInt(rowInp.getText()), Integer.parseInt(colInp.getText())))
                            .build();
                }
                newGame(grid, gra);
            } catch (Exception | DataException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText(e.getMessage());
                alert.setContentText(null);
                alert.showAndWait();
            }


        });
        bombAmount = new Label();
        markedAmount = new Label();
        bombAmount.setPadding(new Insets(10, 10, 10, 10));
        bombAmount.setFont(new Font("Arial", 32));
        markedAmount.setPadding(new Insets(10, 10, 10, 10));
        markedAmount.setFont(new Font("Arial", 32));
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(10, 10, 10, 10));


        Label col = new Label("Kolumny");
        Label row = new Label("Rzędy");
        Label bom = new Label("Bomby");
        Label tempp = new Label(null);
        Label bomAmount = new Label("Liczba Bomb");
        Label marAmount = new Label("Oznaczone pola");
        bomAmount.setPadding(new Insets(10, 10, 10, 10));
        marAmount.setPadding(new Insets(10, 10, 10, 10));


        VBox row1 = new VBox(3);
        row1.getChildren().addAll(col, colInp, row, rowInp);

        VBox row2 = new VBox(3);
        row2.getChildren().addAll(bom, bombInp, tempp, newGame);

        Button buttonEasy = new Button("Łatwa gra");
        buttonEasy.setOnAction(actionEvent -> {
            gra = FactoryOfGame.letsplay(0, godMode.isSelected());
            newGame(grid, gra);
        });
        Button buttonMedium = new Button("Średnia gra");
        buttonMedium.setOnAction(actionEvent -> {
            gra = FactoryOfGame.letsplay(1, godMode.isSelected());
            newGame(grid, gra);
        });
        Button buttonHard = new Button("Trudna gra");
        buttonHard.setOnAction(actionEvent -> {
            gra = FactoryOfGame.letsplay(2, godMode.isSelected());
            newGame(grid, gra);
        });

        VBox row3 = new VBox(3);
        row3.getChildren().addAll(buttonEasy, buttonMedium, buttonHard, godMode);

        VBox row4 = new VBox(3);
        row4.getChildren().addAll(bomAmount, bombAmount);

        VBox row5 = new VBox(3);
        row5.getChildren().addAll(marAmount, markedAmount);


        topMenu.getChildren().addAll(row1, row2, row3, row4, row5);

        BorderPane screen = new BorderPane();
        screen.setTop(topMenu);
        screen.setCenter(grid);

        primaryStage.setScene(new Scene(screen, 700, 700));
        primaryStage.setTitle("Saper");
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
