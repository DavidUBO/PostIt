/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author pondavda
 */
public class Projet extends Application {

  public static FXMLDocumentController controleur;

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/projet/FXMLDocument.fxml"));

    stage.setTitle("Gestion de post-it");
    stage.getIcons()
        .add(new Image(getClass().getResourceAsStream("/ressources/logo_application.png")));

    Font.loadFont(Projet.class.getResource("/ressources/BRADHITC.TTF").toExternalForm(), 20);

    Scene scene = new Scene(root);

    scene.getStylesheets().add(getClass().getResource("/ressources/styles.css").toExternalForm());

    stage.setScene(scene);
    stage.show();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
