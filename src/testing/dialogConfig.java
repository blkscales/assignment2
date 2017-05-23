/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

/**
 *
 * @author blk
 */
public class dialogConfig {

    public static void initialize(GridPane displayPane, int task) {
        //text create
        Text dialog1 = new Text("initial elements: ");
        Text dialog2 = new Text("end game time: ");
        Text dialog3 = new Text("warriors' HP: ");
        Text dialog4 = new Text("warriors' ATK: ");
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        TextField[] textFieldArr = new TextField[10];
        for (int i = 0; i < 10; i++) {
            textFieldArr[i] = new TextField();
        }

        //Creating Buttons 
        Button submit = new Button("Submit");
        Button reset = new Button("Reset");

        displayPane.add(dialog1, 0, 0);
        displayPane.add(textField1, 1, 0);
        displayPane.add(dialog2, 0, 1);
        displayPane.add(textField2, 1, 1);
        displayPane.add(dialog3, 0, 2);
        displayPane.add(dialog4, 0, 3);
        displayPane.add(submit, 0, 4);
        displayPane.add(reset, 1, 4);

        // warriors' HP input 
        for (int i = 0; i < 5; i++) {
            displayPane.add(textFieldArr[i], 1 + i, 2);
        }
        // warriors' ATK input 
        for (int i = 5; i < 10; i++) {
            displayPane.add(textFieldArr[i], i - 4, 3);
        }

        submit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String[] data = new String[13];
                data[0] = textField1.getText();
                data[1] = "5";
                data[2] = textField2.getText();
                for (int i = 0; i < 10; i++) {
                    data[3 + i] = textFieldArr[i].getText();
                }

                System.out.print("Data are: ");
                for (int i = 0; i < 13; i++) {
                    System.out.print(data[i] + " ");
                }

                System.out.println();
                System.out.println("Number of cities is fixed to 5.");

                displayPane.getChildren().clear();
                displayConfig.display(displayPane, data, task);

            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                textField1.clear();
                textField2.clear();
                for (int i = 0; i < 10; i++) {
                    textFieldArr[i].clear();
                }
            }
        });

        //Setting the Grid alignment 
        displayPane.setAlignment(Pos.CENTER);
        // set border, padding ,min size
        displayPane.setVgap(20);
        displayPane.setHgap(20);
        displayPane.setPadding(new Insets(10, 10, 10, 10));
        displayPane.setMinSize(1200, 600);
    }

}
