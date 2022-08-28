package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import java.io.*;

public class Main extends Application implements EventHandler <ActionEvent> {

    private int velikostXY = 750;
    private int steviloX = 50;
    private int steviloY = 50;
    private int k = 3;
    private Group vsiRec;
    private Button replay;
    private Button save;
    private Button load;
    private Button allNew;
    private Rectangle[][] rec;
    private Slider slider;
    private Slider slider2;
    private Slider slider3;
    private Label win;
    private Label lose;
    private Label poteze;
    private Label poteze2;
    private int[][] barve;
    private int click = 0;
    private int click2 = 0;
    private int sourceC;
    private int targetC;
    private int posX;
    private int posY;
    private int obstaja;
    ArrayRandom a;
    ColorArray col;

    @Override
    public void start(Stage stage){
        vsiRec = new Group();
        rec = new Rectangle[steviloX][steviloY];
        int velikost;
        if(steviloX < steviloY){
            velikost = velikostXY / steviloY;
        } else {
            velikost = velikostXY / steviloX;
        }
        a = new ArrayRandom(steviloX, steviloY, k);
        barve = a.Array();
        //Ustvarimo array od kvadratov in jih postavimo na scene
        for(int i = 0; i < steviloX; i++){
            for(int j = 0; j < steviloY; j++){
                Rectangle r = new Rectangle(velikost, velikost);
                rec[i][j] = r;
                rec[i][j].setX(j * velikost);
                rec[i][j].setY(i * velikost);
                int y = (int)r.getX() / velikost;
                int x = (int)r.getY() / velikost;
                vsiRec.getChildren().add(rec[i][j]);

                //Preverimo ce je bil clikan veljaven kradrat, in potem vzamemo color od sourca in od targeta
                r.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if((x != 0 && y != 0 && x != steviloX - 1 && y != steviloY - 1)){
                            click++;
                            click2++;
                            if(click2 == 1){
                                sourceC = barve[x][y];
                            } else {
                                targetC = barve[x][y];
                                posX = x;
                                posY = y;
                                if(sourceC != targetC){
                                    Game igra = new Game();
                                    igra.game(sourceC, targetC, posX, posY);
                                }
                                click2 = 0;
                            }
                            poteze.setText(Integer.toString(click / 2));
                            col.colorRec();
                        }
                    }
                });
            }
        }
        col = new ColorArray();
        col.colorRec();

        //Ustvarim buttone i slidere ki so pozicionirani v borderPane
        allNew = new Button();
        allNew.setOnAction(event -> {
            start(stage);
        });
        replay = new Button();
        save = new Button();
        load = new Button();
        replay.setOnAction(this);
        save.setOnAction(this);
        load.setOnAction(this);
        win = new Label();
        lose = new Label();
        win.setPrefHeight(385);
        win.setPrefWidth(100);
        lose.setPrefWidth(135);
        lose.setPrefHeight(350);
        win.setStyle("-fx-background-image: url('/img/prazno.jpg')");
        lose.setStyle("-fx-background-image: url('/img/img.jpg')");

        slider3 = new Slider(1, 12, k);
        slider3.setOrientation(Orientation.VERTICAL);
        slider3.setPrefHeight(350);
        slider3.setShowTickLabels(true);
        slider3.setShowTickLabels(true);
        slider3.setMajorTickUnit(2);
        slider3.setBlockIncrement(2);

        slider3.setOnMouseReleased(event -> {
            //System.out.println(slider3.getValue());
            k = (int)slider3.getValue();
            poteze.setText(Integer.toString(0));
            click = 0;
            start(stage);
        });

        VBox menuGor = new VBox();
        VBox options = new VBox();
        HBox gornja = new HBox();
        HBox dolnja = new HBox();
        options.getChildren().addAll(replay, save, load, win);
        gornja.getChildren().addAll(options, win);
        dolnja.getChildren().addAll(lose, slider3);
        menuGor.getChildren().addAll(gornja, dolnja);

        replay.setPrefWidth(100);
        replay.setPrefHeight(100);
        save.setPrefWidth(100);
        save.setPrefHeight(100);
        load.setPrefWidth(100);
        load.setPrefHeight(100);
        replay.setStyle("-fx-background-image: url('/img/replay.jpg')");
        save.setStyle("-fx-background-image: url('/img/save.jpg')");
        load.setStyle("-fx-background-image: url('/img/load.jpg')");

        poteze = new Label();
        poteze.setText(Integer.toString(click));
        slider = new Slider(5, 50, steviloY);
        slider.setPrefWidth(750);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(5);
        slider.setBlockIncrement(5);
        poteze2 = new Label("POTEZE");
        poteze2.setPrefWidth(175);
        HBox dol = new HBox();
        dol.getChildren().addAll(poteze2, poteze, slider);

        slider.setOnMouseReleased(event -> {
            //System.out.println(slider.getValue());
            steviloY = (int)slider.getValue();
            poteze.setText(Integer.toString(0));
            click = 0;
            start(stage);
        });

        slider2 = new Slider(5, 50, steviloX);
        slider2.setOrientation(Orientation.VERTICAL);
        slider2.setShowTickMarks(true);
        slider2.setShowTickLabels(true);
        slider2.setMajorTickUnit(5);
        slider2.setBlockIncrement(5);

        slider2.setOnMouseReleased(event -> {
            //System.out.println(slider2.getValue());
            steviloX = (int)slider2.getValue();
            poteze.setText(Integer.toString(0));
            click = 0;
            start(stage);
        });

        BorderPane game = new BorderPane();
        game.setCenter(vsiRec);
        game.setLeft(menuGor);
        game.setBottom(dol);
        game.setRight(slider2);

        Scene scene = new Scene(game, 1000, 800);
        scene.getStylesheets().add("Game.css");

        stage.setTitle("Sebastijan Perusko");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    //Vzamem polje od barv ki gre od 1 do k, in pobarvam polje od Rectangles
    public class ColorArray {
        public void colorRec() {
            for (int i = 0; i < steviloX; i++) {
                for (int j = 0; j < steviloY; j++) {
                    if (barve[i][j] == 0) {
                        obstaja++;
                        rec[i][j].setFill(Color.web("851f3f", 1.0));
                    } else if (barve[i][j] == 1) {
                        rec[i][j].setFill(Color.web("461f3f", 1.0));
                    } else if (barve[i][j] == 2) {
                        rec[i][j].setFill(Color.web("FBEAEB", 1.0));
                    } else if (barve[i][j] == 3) {
                        rec[i][j].setFill(Color.web("071f3f", 1.0));
                    } else if (barve[i][j] == 4) {
                        rec[i][j].setFill(Color.web("8C1B24", 1.0));
                    } else if (barve[i][j] == 5) {
                        rec[i][j].setFill(Color.web("D9D4D0", 1.0));
                    } else if (barve[i][j] == 6) {
                        rec[i][j].setFill(Color.web("F28D77", 1.0));
                    } else if (barve[i][j] == 7) {
                        rec[i][j].setFill(Color.web("D94343", 1.0));
                    } else if (barve[i][j] == 8) {
                        rec[i][j].setFill(Color.web("260F0F", 1.0));
                    } else if (barve[i][j] == 9) {
                        rec[i][j].setFill(Color.web("055CF2", 1.0));
                    } else if (barve[i][j] == 10) {
                        rec[i][j].setFill(Color.web("A3D9D9", 1.0));
                    } else if (barve[i][j] == 11) {
                        rec[i][j].setFill(Color.web("F2E313", 1.0));
                    } else if (barve[i][j] == -1) {
                        rec[i][j].setFill((Color.web("f4f4f4", 1.0)));
                    }
                }
            }
            //Preverim ce je mozno nadaljevanje in ce smo zmagali
            if (obstaja == (steviloX - 2) * (steviloY - 2)) {
                System.out.println("Win");
                win.setStyle("-fx-background-image: url('/img/win.jpg')");
            }
            if (obstaja == 0) {
                System.out.println("Lose");
                win.setStyle("-fx-background-image: url('/img/lose.jpg')");
            }
            obstaja = 0;
        }
    }

    //Preverim od kateri button je bil clikan
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == replay){
            click = 0;
            poteze.setText(Integer.toString(click));
            win.setStyle("-fx-background-image: url('/img/prazno.jpg')");
            barve = a.Array();
            col.colorRec();
            //Shranim stanje igre
        } else if(event.getSource() == save){
            try{
                FileOutputStream savegame = new FileOutputStream("savegame.dat");
                FileOutputStream saveK = new FileOutputStream("saveK.dat");
                FileOutputStream saveSteviloX = new FileOutputStream("saveSteviloX.dat");
                FileOutputStream saveSteviloY = new FileOutputStream("saveSteviloY.dat");
                FileOutputStream savePoteze = new FileOutputStream("savePoteze.dat");
                ObjectOutputStream game = new ObjectOutputStream(savegame);
                ObjectOutputStream gameK = new ObjectOutputStream(saveK);
                ObjectOutputStream gameSteviloX = new ObjectOutputStream(saveSteviloX);
                ObjectOutputStream gameSteviloY = new ObjectOutputStream(saveSteviloY);
                ObjectOutputStream gamePoteze = new ObjectOutputStream(savePoteze);
                game.writeObject(barve);
                gameK.writeObject(k);
                gameSteviloX.writeObject(steviloX);
                gameSteviloY.writeObject(steviloY);
                gamePoteze.writeObject(click);
            } catch (Exception e){

            }
            //Load presnji save
        } else if(event.getSource() == load){
            try {
                FileInputStream loadgame = new FileInputStream("savegame.dat");
                FileInputStream loadK = new FileInputStream("saveK.dat");
                FileInputStream loadSteviloX = new FileInputStream("saveSteviloX.dat");
                FileInputStream loadSteviloY = new FileInputStream("saveSteviloY.dat");
                FileInputStream loadPoteze = new FileInputStream("savePoteze.dat");
                ObjectInputStream gameload = new ObjectInputStream(loadgame);
                ObjectInputStream gameloadK = new ObjectInputStream(loadK);
                ObjectInputStream gameloadSteviloX = new ObjectInputStream(loadSteviloX);
                ObjectInputStream gameloadSteviloY = new ObjectInputStream(loadSteviloY);
                ObjectInputStream gameloadPoteze = new ObjectInputStream(loadPoteze);
                steviloX = (int) gameloadSteviloX.readObject();
                steviloY = (int) gameloadSteviloY.readObject();
                k = (int) gameloadK.readObject();
                allNew.fire();
                click = (int) gameloadPoteze.readObject();
                poteze.setText(Integer.toString(click / 2));
                barve = (int[][]) gameload.readObject();
                a.Array();
                col.colorRec();
            } catch (Exception e){

            }
        }
    }

    //Pobarvam sosedna polja v source barvo ce je mozno.
    public class Game {
        public void game(int sourceC, int targetC, int posX, int posY) {
            if (posX < 0 || posX > steviloX - 1 || posY < 0 || posY > steviloY - 1) {
                return;
            }
            if (barve[posX][posY] != targetC) {
                return;
            } else {
                barve[posX][posY] = sourceC;
                game(sourceC, targetC, posX + 1, posY);
                game(sourceC, targetC, posX, posY - 1);
                game(sourceC, targetC, posX - 1, posY);
                game(sourceC, targetC, posX, posY + 1);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
