/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import static projet.PostIt.BOUTON_MARGE_GAUCHE;
import static projet.PostIt.BOUTON_MARGE_HAUT;
import static projet.PostIt.LABEL_MARGE_VERTICALE;
import static projet.PostIt.LABEL_MARGE_HORIZONTALE;
import static projet.PostIt.COULEUR_BOX_MARGE_BAS;

/**
 *
 * @author pondavda
 */
public final class PostItInitializer {
    
    private static final int COLOR_PICKER_DEFAULT_HEIGHT = 25;
    
    /**
     * Permet d'initialiser un post-it pour qu'il soit dans un état stable. Son comportement est également initialisé.
     * @param p Le post-it à initialiser
     */
    public static void init(PostIt p) {
        p.setLayoutX(p.x - p.taille / 2);
        p.setLayoutY(p.y - p.taille / 2);
        p.setWidth(p.taille);
        p.setHeight(p.taille);        
        
        //Ajout du label
        PostItInitializer.initLabelContenu(p, new Label(p.contenu));
        
        //Ajout des boutons
        p.buttonBar = new ButtonBar();
        p.buttonBar.setLayoutX(p.x - p.taille / 2 + BOUTON_MARGE_GAUCHE);
        p.buttonBar.setLayoutY(p.y - p.taille / 2 + BOUTON_MARGE_HAUT);
        p.buttonBar.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 0), null, null)));
        Projet.controleur.panneau.getChildren().add(p.buttonBar);
        Color boutonsColor = new Color(1, 1, 1, 0.5);
        //Ajout du bouton "Éditer"   
        PostItInitializer.initBoutonEditer(p, new Button("Éditer"), boutonsColor);
        //Ajout du bouton "Supprimer"
        PostItInitializer.initBoutonSupprimer(p, new Button("Supprimer"), boutonsColor);
        
        //Ajout de la liste déroulante pour choisir les couleur
        PostItInitializer.initColorPicker(p, new ColorPicker(p.couleur), boutonsColor);
        
        //Autres initialisations
        p.draw();
        PostItInitializer.initMouseListeners(p);
    }
    
    public static void initLabelContenu(PostIt p, Label label){
        p.labelContenu = label;
        label.setLayoutX(p.x - p.taille / 2 + LABEL_MARGE_HORIZONTALE);
        label.setLayoutY(p.y - p.taille / 2 + LABEL_MARGE_VERTICALE);
        label.setMaxSize(p.taille - 2 * LABEL_MARGE_HORIZONTALE, p.taille - (LABEL_MARGE_VERTICALE * 2) - COLOR_PICKER_DEFAULT_HEIGHT);
        label.setTextFill(Color.BLACK);
        label.setWrapText(true);
        label.getStyleClass().add("postItContenu");
        Projet.controleur.panneau.getChildren().add(label);
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            if(t.getClickCount() == 2){
                Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
                p.editerTexte();
            }
        });
    }
    
    public static void initBoutonEditer(PostIt p, Button bouton, Color couleur){
        //Dessin
        bouton.setBackground(new Background(new BackgroundFill(couleur, CornerRadii.EMPTY, Insets.EMPTY)));
        ButtonBar.setButtonData(bouton, ButtonData.LEFT);
        p.buttonBar.getButtons().add(bouton);
        //Handlers
        bouton.setOnAction((ActionEvent event) -> {
            Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
            p.editerTexte();
        });
        bouton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent t) -> {
            Projet.controleur.panneau.setCursor(Cursor.HAND);
        });
        bouton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent t) -> {
            Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
        });
    }
    
    public static void initBoutonSupprimer(PostIt p, Button bouton, Color couleur){
        //Dessin
        bouton.setBackground(new Background(new BackgroundFill(couleur, CornerRadii.EMPTY, Insets.EMPTY)));
        ButtonBar.setButtonData(bouton, ButtonData.LEFT);
        p.buttonBar.getButtons().add(bouton);
        //Handlers
        bouton.setOnAction((ActionEvent event) -> {
            Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
            p.supprimerPostIt();
        });
        bouton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent t) -> {
            Projet.controleur.panneau.setCursor(Cursor.HAND);
        });
        bouton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent t) -> {
            Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
        });
    }
    
    public static void initColorPicker(PostIt p, ColorPicker picker, Color couleur){
        //Dessin
        p.choixCouleur = picker;
        picker.setLayoutX(p.x - p.taille / 2 + LABEL_MARGE_HORIZONTALE);
        picker.setLayoutY(p.y + p.taille / 2 - COULEUR_BOX_MARGE_BAS - COLOR_PICKER_DEFAULT_HEIGHT);
        picker.setBackground(new Background(new BackgroundFill(couleur, CornerRadii.EMPTY, Insets.EMPTY)));
        Projet.controleur.panneau.getChildren().add(picker);
        //Handlers
        picker.setOnAction((ActionEvent event) -> {
           p.changerCouleur(); 
        });
        picker.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent t) -> {
            Projet.controleur.panneau.setCursor(Cursor.HAND);
        });
        picker.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent t) -> {
            Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
        });
    }
    
    public static void initMouseListeners(PostIt p) {
        p.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent t) -> {
            Projet.controleur.panneau.setCursor(Cursor.OPEN_HAND);
            p.afficheParDessus();
        });

        p.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent t) -> {
            // Activer le scroll du panneau
            Projet.controleur.scroll.setPannable(true);
            Projet.controleur.panneau.setCursor(Cursor.OPEN_HAND);
        });

        p.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent t) -> {
            Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
        });

        p.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent t) -> {
            // Mettre à jour le décalage du dessin par rapport à la souris
            p.decalageX = t.getSceneX() - p.x;
            p.decalageY = t.getSceneY() - p.y;
        });

        p.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent t) -> {
            // Désactiver le scroll du panneau
            Projet.controleur.scroll.setPannable(false);
            Projet.controleur.panneau.setCursor(Cursor.CLOSED_HAND);
            // Mettre à jour la position du post-it
            p.x = t.getSceneX() - p.decalageX;
            p.y = t.getSceneY() - p.decalageY;
            if(p.x - p.taille / 2 < 0)
                p.x = 0 + p.taille/2;
            if(p.y - p.taille / 2 < 0)
                p.y = 0 + p.taille/2;
            if(p.x + p.taille > Projet.controleur.panneau.getWidth())
                p.x = Projet.controleur.panneau.getWidth() - p.taille / 2;
            if(p.y + p.taille > Projet.controleur.panneau.getHeight())
                p.y = Projet.controleur.panneau.getHeight() - p.taille / 2;
            p.setLayoutX(p.x - p.taille / 2);
            p.setLayoutY(p.y - p.taille / 2);
            //Mettre à jour ses composants
            p.labelContenu.setLayoutX(p.x - p.taille / 2 + LABEL_MARGE_HORIZONTALE);
            p.labelContenu.setLayoutY(p.y - p.taille / 2 + LABEL_MARGE_VERTICALE);
            p.buttonBar.setLayoutX(p.x - p.taille / 2 + BOUTON_MARGE_GAUCHE);
            p.buttonBar.setLayoutY(p.y - p.taille / 2 + BOUTON_MARGE_HAUT);
            p.choixCouleur.setLayoutX(p.x - p.taille / 2 + LABEL_MARGE_HORIZONTALE);
            p.choixCouleur.setLayoutY(p.y + p.taille / 2 - COULEUR_BOX_MARGE_BAS - COLOR_PICKER_DEFAULT_HEIGHT);
            //Redessiner le post-it
            p.draw();
        });
        
        p.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            if(t.getClickCount() == 2){
                Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
                p.editerTexte();
            }
        });
    }
}
