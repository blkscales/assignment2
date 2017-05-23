
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import Warriors.Warrior;
import Warriors.WarriorType;
import World.City;
import World.PlayerHeadquarters;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import systemControl.mainDriver;
import World.World;
import World.WorldProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author blk
 */
public class displayConfig {

    public static Text dialog1 = new Text("Time: ");
    public static Text dialog2 = new Text("000 ");
    public static Text dialog3 = new Text("Life Elements: ");
    public static Text dialog4 = new Text();
    public static Map<String, Image> images = new HashMap<>();
    public static Image redFlag = new Image("file:src/figure/flag_red.png", 100, 100, false, false);
    public static Image blueFlag = new Image("file:src/figure/flag_blue.png", 100, 100, false, false);
    public static Image blankImage = new Image("file:src/figure/blank_space.png", 50, 50, false, false);
    public static ImageView[] imgViewArr;
    public static TabPane tp;
    public static Text textMessage;

    static {
        for (String name : new String[]{"dragon", "iceman", "lion", "ninja", "wolf"}) {
            setImages(name);
        }
    }

    public static void setImages(String name) {
        setImage(name);
        setImage(name + "_blue");
        setImage(name + "_red");
    }

    public static void setImage(String name) {
        images.put(name, new Image("file:src/figure/" + name + ".png", 50, 50, false, false));
    }

    public static void display(GridPane displayPane, String[] data, int task) {
        basicDisplay(displayPane, data, task);
        if (task == 2) {
            playerDisplay(displayPane, data, task);
        }
    }

    private static void playerDisplay(GridPane displayPane, String[] data, int task) {
        textMessage = new Text("Game Started");
        BorderPane bp = new BorderPane();
        GridPane control = new GridPane();
        initPlayerControl(control);
        tp = new TabPane();
        initInfoTab(tp);
        bp.setRight(control);
        bp.setCenter(tp);
        displayPane.add(textMessage, 0, 6, 14, 1);
        displayPane.add(bp, 0, 7, 14, 1);
    }

    private static void initPlayerControl(GridPane control) {
        for (int i = 0; i < 5; i++) {
            ImageView w = new ImageView(images.get(WarriorType.WarriorNames[i]));
            control.add(w, i % 3, i / 3, 1, 1);
            final int x = i;
            w.setOnMouseClicked((e) -> {
                try {
                    if (((PlayerHeadquarters) World.CityList.get(0)).tryToProduceWarrior(x)) {
                        updateBoard();
                    }
                } catch (IllegalStateException ex) {
                    textMessage.setText(ex.getMessage());
                }
            });
        }
        Button none = new Button("X");
        none.setOnMouseClicked((e) -> {
            try {
                if (((PlayerHeadquarters) World.CityList.get(0)).tryToProduceWarrior(-1)) {
                    updateBoard();
                }
            } catch (IllegalStateException ex) {
                textMessage.setText(ex.getMessage());
            }
        });
        control.add(none, 2, 2);
    }

    private static void initInfoTab(TabPane tp) {
        ObservableList<Tab> tabs = tp.getTabs();
        // empty tab for display nothing initially
        Tab empty = new Tab();
        empty.setContent(new Pane());
        tabs.add(empty);
        
        Tab cityInfo = new Tab();
        HBox cityHbox = new HBox();
        //City part
        ObservableList<Node> chc = cityHbox.getChildren();
        chc.add(new ImageView());
        GridPane cGridPane = new GridPane();
         //Setting the Grid alignment 
        cGridPane.setAlignment(Pos.CENTER);
        // set border, padding 
        cGridPane.setVgap(20);
        cGridPane.setHgap(20);
        cGridPane.setPadding(new Insets(10, 10, 10, 10));
   
        cGridPane.add(new Text("ID"), 0, 0, 1, 1);
        cGridPane.add(new Text("LifeElement"), 2, 0, 1, 1);
        cGridPane.add(new Text("RedWarriorStation"), 0, 1, 1, 1);
        cGridPane.add(new Text("BlueWarriorStation"), 2, 1, 1, 1);
        cGridPane.add(new Text("Flag"),0, 2, 1, 1);
        cGridPane.add(new Text("Last Round Winner"), 2, 2, 1, 1);
        cGridPane.add(new Text("Status After Battle"), 0, 3, 1, 1);
        cGridPane.add(new Text("Active Attack Party"), 2, 3, 1, 1);
           
        for(int i = 0; i < 8; i++){
            cGridPane.add(new Text(), i % 2 * 2 + 1, i / 2);
        }
        chc.add(cGridPane);

        cityInfo.setContent(cityHbox);
        tabs.add(cityInfo);
        
        //Warrior part
        Tab warriorInfo = new Tab();
        HBox warriorHbox = new HBox();
        ObservableList<Node> whc = warriorHbox.getChildren();
        whc.add(new ImageView());
        GridPane wGridPane = new GridPane();
         //Setting the Grid alignment 
        wGridPane.setAlignment(Pos.CENTER);
        // set border, padding ,min size
        wGridPane.setVgap(20);
        wGridPane.setHgap(20);
        wGridPane.setPadding(new Insets(10, 10, 10, 10));
        
        wGridPane.add(new Text("Type"), 0, 0, 1, 1);
        wGridPane.add(new Text("HP"), 2, 0, 1, 1);
        wGridPane.add(new Text("Party"), 0, 1, 1, 1);
        wGridPane.add(new Text("Attack"), 2, 1, 1, 1);
        wGridPane.add(new Text("ID"),0, 2, 1, 1);
        wGridPane.add(new Text("Kills"), 2, 2, 1, 1);
        wGridPane.add(new Text("Location"), 0, 3, 1, 1);
        wGridPane.add(new Text("Has moved"), 2, 3, 1, 1);
       
        // ......
        for(int i = 0; i < 8; i++){
            wGridPane.add(new Text(), i % 2 * 2 + 1, i / 2);
        }
        whc.add(wGridPane);
        warriorInfo.setContent(warriorHbox);
        tabs.add(warriorInfo);       
    }

    public static void selectCity(City c) {
        Tab cityInfo = tp.getTabs().get(1);
        HBox content = (HBox) cityInfo.getContent();
        ObservableList<Node> children = content.getChildren();

        GridPane grid = (GridPane) children.get(1);
        ObservableList<Node> gridNodes = grid.getChildren();
        ImageView img = (ImageView) children.get(0);
        Image headQuarter = new Image("file:src/figure/castle_0.png", 100, 100, false, false);
        Image city = new Image("file:src/figure/castle_1.png", 100, 100, false, false);
        if(c.CityID == 0 || c.CityID == WorldProperty.NumberOfCity+1)
            img.setImage(headQuarter);
        else
            img.setImage(city);
        
        for(Node n : gridNodes){
            
            int red = 0;
            int blue = 1;
            
            if(grid.getColumnIndex(n) == 1 && grid.getRowIndex(n) == 0){
                Text text = (Text) n;
                text.setText(String.valueOf(c.CityID));
            }
            if(grid.getColumnIndex(n) == 3 && grid.getRowIndex(n) == 0){
                Text text = (Text) n;
                text.setText(String.valueOf(c.LifeElement));
            }
            if(grid.getColumnIndex(n) == 1 && grid.getRowIndex(n) == 1){
                Text text = (Text) n;
                if(c.RedWarriorStation.isEmpty())
                    text.setText("Nil");
                else
                    text.setText(c.RedWarriorStation.get(0).WarriorNameCard);
            }
            if(grid.getColumnIndex(n) == 3 && grid.getRowIndex(n) == 1){
                Text text = (Text) n;
                if(c.BlueWarriorStation.isEmpty())
                    text.setText("Nil");
                else
                    text.setText(c.BlueWarriorStation.get(0).WarriorNameCard);
            }
            if(grid.getColumnIndex(n) == 1 && grid.getRowIndex(n) == 2){
                Text text = (Text) n;
                if(c.Flag == red)
                    text.setText("red");
                else if(c.Flag == blue)
                    text.setText("blue");
                else
                    text.setText("nil");
            }
            if(grid.getColumnIndex(n) == 3 && grid.getRowIndex(n) == 2){
                Text text = (Text) n;
                if(c.PartyOfLastRoundWinner == red)
                    text.setText("red");
                else if(c.PartyOfLastRoundWinner == blue)
                    text.setText("blue");
                else
                    text.setText("nil");
            }
            if(grid.getColumnIndex(n) == 1 && grid.getRowIndex(n) == 3){
                Text text = (Text) n;
                String status = c.Status_AfterBattle? "true":"false";  
                text.setText(status);
                           
            }
            if(grid.getColumnIndex(n) == 3 && grid.getRowIndex(n) == 3){
                Text text = (Text) n;
                if(c.activeAttackParty == red)
                    text.setText("red");
                else if(c.activeAttackParty == blue)
                    text.setText("blue");
                else
                    text.setText("nil");
            }
          
        }
        tp.getSelectionModel().select(cityInfo);
    }

    public static void selectWarrior(Warrior w) {
        textMessage.setText(WarriorType.WarriorNames[w.Type]);
        Tab warriorInfo = tp.getTabs().get(2);
        HBox content = (HBox) warriorInfo.getContent();
        ObservableList<Node> children = content.getChildren();
        ImageView img = (ImageView) children.get(0);
        img.setImage(images.get(WarriorType.WarriorNames[w.Type] + "_" + WorldProperty.PartyNames[w.Party]));
        GridPane grid = (GridPane) children.get(1);
        ObservableList<Node> gridNodes = grid.getChildren();
        for(Node n : gridNodes){
            if(grid.getColumnIndex(n) == 1 && grid.getRowIndex(n) == 0){
                Text text = (Text) n;
                text.setText(WarriorType.WarriorNames[w.Type]);
            }
            if(grid.getColumnIndex(n) == 3 && grid.getRowIndex(n) == 0){
                Text text = (Text) n;
                text.setText(String.valueOf(w.HP));
            }
            if(grid.getColumnIndex(n) == 1 && grid.getRowIndex(n) == 1){
                Text text = (Text) n;
                text.setText(WorldProperty.PartyNames[w.Party]);
            }
            if(grid.getColumnIndex(n) == 3 && grid.getRowIndex(n) == 1){
                Text text = (Text) n;
                text.setText(String.valueOf(w.AttackValue));
            }
            if(grid.getColumnIndex(n) == 1 && grid.getRowIndex(n) == 2){
                Text text = (Text) n;
                text.setText(String.valueOf(w.ProductionID));
            }
            if(grid.getColumnIndex(n) == 3 && grid.getRowIndex(n) == 2){
                Text text = (Text) n;
                text.setText(String.valueOf(w.NumberOfKilledWarrior));
            }
            if(grid.getColumnIndex(n) == 1 && grid.getRowIndex(n) == 3){
                Text text = (Text) n;
                text.setText(String.valueOf(w.Location));
            }
            if(grid.getColumnIndex(n) == 3 && grid.getRowIndex(n) == 3){
                Text text = (Text) n;
                text.setText(String.valueOf(w.MovedDistance));
            }
            
        }
 
        tp.getSelectionModel().select(warriorInfo);
    }

    private static void basicDisplay(GridPane displayPane, String[] data, int task) {
        Image[] imgArr = new Image[28];
        for (int i = 0; i < 7; i++) {
            imgArr[i] = new Image("file:src/figure/blank_space.png", 100, 100, false, false);
        }

        imgArr[7] = new Image("file:src/figure/castle_0.png", 100, 100, false, false);
        for (int i = 8; i < 13; i++) {
            imgArr[i] = new Image("file:src/figure/castle_1.png", 100, 100, false, false);
        }

        imgArr[13] = new Image("file:src/figure/castle_0.png", 100, 100, false, false);
        for (int i = 14; i < 28; i++) {
            imgArr[i] = blankImage;
        }

        imgViewArr = new ImageView[28];
        for (int i = 0; i < 14; i++) {
            imgViewArr[i] = new ImageView();
            imgViewArr[i].setImage(imgArr[i]);

            displayPane.add(imgViewArr[i], i % 7 * 2, 1 + i / 7 * 2, 2, 2);
            final int x = i;
            if (task == 2 || task == 3) {
                if(x <=6)
                    continue;
                imgViewArr[i].setOnMouseClicked((e) -> {
                    City c = World.CityList.get(x-7);
    
                    selectCity(c);
                });
            }
        }
        for (int i = 14; i < 28; i++) {
            imgViewArr[i] = new ImageView();
            imgViewArr[i].setImage(imgArr[i]);
            //display 14 pic in 1 row
            displayPane.add(imgViewArr[i], i - 14, 5, 1, 1);
            final int x = i;
            if (task == 2 || task == 3) {
                imgViewArr[i].setOnMouseClicked((e) -> {
                    City c = World.CityList.get((x - 14) / 2);
                    boolean red = x % 2 == 0;
                    ArrayList<Warrior> list = red ? c.RedWarriorStation : c.BlueWarriorStation;
                    if (list.isEmpty()) {
                        return;
                    }
                    selectWarrior(list.get(0));
                });
            }
        }

        displayPane.add(dialog1, 3, 0, 2, 1);
        displayPane.add(dialog2, 5, 0, 2, 1);
        displayPane.add(dialog3, 8, 0, 2, 1);
        displayPane.add(dialog4, 10, 0, 2, 1);

        mainDriver.main(data, task);
        World copyWorld = mainDriver.the_world;
        String time = copyWorld.WorldClock.getTime();

        displayPane.setAlignment(Pos.CENTER);
        // set border, padding
        displayPane.setVgap(20);
        displayPane.setHgap(20);
        displayPane.setPadding(new Insets(10, 10, 10, 10));
    }

    public static void setTime(String time) {
        dialog2.setText(time);
    }

    public static void updateBoard() {
        dialog4.setText(String.valueOf(World.CityList.get(0).LifeElement));
        for (int i = 1; i < 6; i++) {
            City c = World.CityList.get(i);
            switch (c.Flag) {
                case -1:
                    imgViewArr[i].setImage(blankImage);
                    break;
                case 0:
                    imgViewArr[i].setImage(redFlag);
                    break;
                case 1:
                    imgViewArr[i].setImage(blueFlag);
                    break;
            }
        }
        for (int i = 14; i < 28; i++) {
            City c = World.CityList.get((i - 14) / 2);
            boolean red = i % 2 == 0;
            ArrayList<Warrior> list = red ? c.RedWarriorStation : c.BlueWarriorStation;
            if (list.isEmpty()) {
                imgViewArr[i].setImage(blankImage);
            } else {
                String warriorName = WarriorType.WarriorNames[list.get(0).Type];
                imgViewArr[i].setImage(images.get(warriorName + "_" + (red ? "red" : "blue")));
            }
        }
    }

}