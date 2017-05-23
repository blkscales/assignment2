
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//basic java stuff or user define class
import testing.dialogConfig;

public class Testing extends Application {

    @Override
    public void start(Stage stage) {

        //Creating a Grid Panes 
        GridPane mainPane = new GridPane();
        GridPane displayPane = new GridPane();
        GridPane singlePlayPane = new GridPane();
        GridPane multiPlayPane = new GridPane();

        Scene mainScene = new Scene(mainPane);
        Scene displayPage = new Scene(displayPane);
        Scene singlePlayPage = new Scene(singlePlayPane);
        Scene multiPlayPage = new Scene(multiPlayPane);
        singlePlayPage.getStylesheets().add("file:src/css/tab.css");

        Text title = new Text("World of Warcraft");
        //Creating Buttons 
        Button button1 = new Button("Display Assignment1");
        Button button2 = new Button("Single Player");
        Button button3 = new Button("Multi Player");
        Button back1 = new Button("back to Main Page");
        Button back2 = new Button("back to Main Page");
        Button back3 = new Button("back to Main Page");

        //data for storing input
        String[] data = new String[12];

        // load the image
        Image img = new Image("file:src/image/ninja.png");

        // simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(img);
        mainPane.add(iv1, 3, 0);

        //Arranging all the nodes in the grid 
        dialogConfig.initialize(displayPane, 1); //task 1
        dialogConfig.initialize(singlePlayPane, 2);//task 2

        displayPane.add(back1, 2, 4);
        singlePlayPane.add(back2, 2, 4);
        multiPlayPane.add(back3, 2, 4);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Display");
                stage.setScene(displayPage);
                stage.setTitle("Display Scene");
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Single play");
                stage.setScene(singlePlayPage);
                stage.setTitle("Main Scene");
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Multi play");
                stage.setScene(multiPlayPage);
                stage.setTitle("Multi Play");
            }
        });

        back1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(mainScene);
                stage.setTitle("Main Page");
            }
        });

        back2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(mainScene);
                stage.setTitle("Main Page");
            }
        });

        back3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(mainScene);
                stage.setTitle("Main Page");
            }
        });

        //Setting size for the panes
        mainPane.setMinSize(600, 600);

        //Setting the padding  
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns 
        mainPane.setVgap(20);
        mainPane.setHgap(20);

        //Setting the Grid alignment 
        mainPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid 
        mainPane.add(title, 0, 0);
        mainPane.add(button1, 0, 1);
        mainPane.add(button2, 0, 2);
        mainPane.add(button3, 0, 3);

        //Styling nodes     
        title.setStyle("-fx-font: normal bold 40px 'serif' ");

        stage.setTitle("Main Page");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
