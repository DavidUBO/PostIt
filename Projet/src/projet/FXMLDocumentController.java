/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

/**
 *
 * @author pondavda
 */
public class FXMLDocumentController implements Initializable {
    
    public List<PostIt> postItListe;
    
    @FXML
    public Pane panneau;
    
    @FXML
    public ScrollPane scroll;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Projet.controleur = this;
        postItListe = new LinkedList<>();
    }
    
    @FXML
    private void creerUnPostIt(ActionEvent event) {
        PostIt postIt = new PostIt(0, 0);
        postItListe.add(postIt);
        panneau.getChildren().add(postIt);
        postIt.componentsToFront();
    }
    
    @FXML
    private  void chargerPostIt(ActionEvent event){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisissez un fichier à charger :");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
              //postItListe.addAll(PostItJsonSerializer.importerPostIt(file));
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
    
    @FXML
    private void sauvegarderPostIt(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez un fichier :");
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            //PostItJsonSerializer.exporterPostIt(postItListe, file);
        }
    }
    
    public String getTexteFromDialog(String contenuActuel){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Contenu du post-it");
        dialog.setHeaderText("Veuillez entrer ce que vous souhaitez écrire sur le post-it.");

        ButtonType validerBouton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(validerBouton);

        GridPane grid = new GridPane();        
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));

        TextArea textArea = new TextArea(contenuActuel);

        grid.add(textArea, 0, 0);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> textArea.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == validerBouton) {
                return textArea.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        
        if(result.isPresent()){
            return result.get();
        }
        else{
            return contenuActuel;
        }
    }
}
