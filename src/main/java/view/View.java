package view;

import com.sun.javafx.sg.prism.NGShape;
import controller.Controller;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import model.GameCompetitiveSolo;
import model.Mode;
import org.fxmisc.richtext.StyleClassedTextArea;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.*;

import java.util.List;
import java.util.Objects;


public class View extends Application {

    private final Controller controller;

    private StyleClassedTextArea text = null;

    private StyleClassedTextArea additionnalInfo = null;





    public View(Controller c) {
        this.controller = c;
    }




    @Override
    public void start(Stage primaryStage){
        try {

            BorderPane root = new BorderPane();
            MenuBar menuBar = new MenuBar();
            Button exit = new Button("exit");
            exit.setOnAction(e -> System.exit(0));
            Menu newGame = new Menu("New game");

            MenuItem soloMode = new MenuItem("Single-player");
            soloMode.setOnAction(e -> this.controller.changeMode(0)); // appel controller

            MenuItem multiMode = new MenuItem("Multiplayer");
            multiMode.setOnAction(e -> this.controller.changeMode(2)); // appel controller

            MenuItem competitiveMode = new MenuItem("Normal Competitive");
            competitiveMode.setOnAction(e -> this.controller.changeMode(1)); // appel controller

            newGame.getItems().addAll(soloMode, multiMode, competitiveMode);
            menuBar.getMenus().addAll(newGame);

            HBox topBar = new HBox(menuBar, exit);
            HBox.setHgrow(menuBar, Priority.ALWAYS);
            HBox.setHgrow(exit, Priority.NEVER);
            root.setTop(topBar);


            this.text = new StyleClassedTextArea();
            text.setEditable(false);
            text.setWrapText(true);

            this.additionnalInfo = new StyleClassedTextArea();
            additionnalInfo.setEditable(false);
            additionnalInfo.setWrapText(false);


            VBox vbox = new VBox(text,additionnalInfo);
            root.setCenter(vbox);


            Scene scene = new Scene(root, 1080, 180);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            primaryStage.setTitle("Dactylo-Game");
            primaryStage.setScene(scene);



            scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if(controller.isGameRunning()) {
                        controller.keyPressed(event);
                        controller.update();
                        if(!controller.isGameRunning()){
                            if(controller.getGame().getMode().equals(Mode.COMPETITIVE)) ((GameCompetitiveSolo) controller.getGame()).cancelTimer();

                            System.out.println("game no more running\n");
                            System.out.println(this);
                            //on affiche les statistiques
                            controller.getStats();
                        }
                    }

                }


            });

            root.requestFocus();
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void printText(String s) {
        this.text.appendText(s + " ");
    }


    public void resetText() {
        System.out.println(this);
        this.text.replaceText("");
    }

    public void colorWord(int pos){
        this.text.setStyleClass(0, this.text.getLength(), "black");
        this.text.setStyleClass(0, pos, "green");

        if(this.controller.getGame().getMode().equals( Mode.COMPETITIVE)) {
            this.text.setStyleClass(pos, this.text.getLength(), "black");
            List<Integer> blueWordList = ((GameCompetitiveSolo) this.controller.getGame()).getBlueWordsPos();
            List<String> currentList = this.controller.getGame().getList();
            if (blueWordList != null) {
                for (Integer i : blueWordList) {
                    if (i < 0) continue;
                    int position = 0;
                    for (int j = 0; j < i; j++) {
                        position += currentList.get(j).length();
                        position++;
                    }
                    this.text.setStyleClass(position, position + currentList.get(i).length(), "blue");
                }
            }
            this.text.setStyleClass(0, pos, "green");
        }
    }



    public static void main(String[] args) {
        Platform.setImplicitExit(false);
        Controller controller = new Controller();
        controller.init();
        controller.getGame().init(controller);
        // on commence par start la GUI
        Platform.runLater(() -> {
            try {
                View v1 = new View(controller);
                controller.setView(v1);
                Stage stage = new Stage();
                v1.start(stage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        controller.update();
    }

    public void setEndScreen(String stats) {
        this.text.replaceText(stats);
        this.text.setStyleClass(0, this.text.getLength(), "black");
    }


    public void printLives(int lives) {
        this.additionnalInfo.replaceText("lives : " + lives);
;
    }

}


