/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
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
    
    @FXML
    public ToggleButton changerArchivageBouton;
    
    @FXML
    public ChoiceBox postItChoixTaille;
    
    Image editImage;
    Image deleteImage;
    Image archiveImage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Projet.controleur = this;
        postItListe = new LinkedList<>();
        
        List<String> tailles = new ArrayList<>();
        for(PostItTailleEnum taille : PostItTailleEnum.values()){
            tailles.add(taille.toString());
        }
        
        postItChoixTaille.getItems().addAll(tailles);
        postItChoixTaille.setValue(PostItTailleEnum.TailleNormale.toString());
        
        postItChoixTaille.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            PostIt.changeTaillePostIt(postItListe, PostItTailleEnum.getEnumValue((String)postItChoixTaille.getValue()).getValue());
        });
        
        editImage = new Image(getClass().getResourceAsStream("/ressources/edit.png"));
        deleteImage = new Image(getClass().getResourceAsStream("/ressources/trash.png"));
        archiveImage = new Image(getClass().getResourceAsStream("/ressources/archive.png"));
    }
    
    @FXML
    private void creerUnPostIt(ActionEvent event) {
        PostIt postIt = new PostIt(0, 0, PostItTailleEnum.getEnumValue((String)postItChoixTaille.getValue()).getValue());
//        PostIt postIt = new PostIt(0, 0, PostItTailleEnum.TailleNormale.getValue());
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
              postItListe.addAll(PostItJsonSerializer.importerPostIt(file));
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
    
    @FXML
    private void sauvegarderPostIt(ActionEvent event){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisissez un fichier :");
            File file = fileChooser.showSaveDialog(null);
            if(file != null){
                PostItJsonSerializer.exporterPostIt(postItListe, file);
            }
        } catch (Exception e) {
            
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
    
    @FXML
    public void changerAffichageArchive() {
        if(changerArchivageBouton.isSelected()){
            for(PostIt courant : postItListe){
                if(courant.estArchive && !courant.estAffiche)
                    courant.redessiner();
            }
        }
        else{
            for(PostIt courant : postItListe){
                if(courant.estArchive)
                    courant.effacer();
            }
        }
    }
}
