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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 *
 * @author pondavda
 */
public final class PostItInitializer {

  public static final int TAILLE_IMAGE_BOUTON = 27;

  /**
   * Permet d'initialiser un post-it pour qu'il soit dans un état stable. Son comportement est
   * également initialisé.
   * 
   * @param p Le post-it à initialiser
   */
  public static void init(PostIt p) {
    p.setLayoutX(p.x);
    p.setLayoutY(p.y);
    p.setWidth(p.taille);
    p.setHeight(p.taille);

    p.estAffiche = true;

    // Ajout du label
    PostItInitializer.initLabelContenu(p, new Label(p.contenu));

    // Ajout des boutons
    p.buttonBar = new ButtonBar();
    p.setButtonBarLayout();
    p.buttonBar
        .setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 0), null, null)));
    p.buttonBar.setButtonMinWidth(1 + TAILLE_IMAGE_BOUTON + 1);
    Projet.controleur.panneau.getChildren().add(p.buttonBar);
    Color boutonsColor = new Color(1, 1, 1, 0.5);
    // Ajout du bouton "Éditer"
    PostItInitializer.initBoutonEditer(p, new Button(), boutonsColor);
    // Ajout du bouton "Supprimer"
    PostItInitializer.initBoutonSupprimer(p, new Button(), boutonsColor);
    // Ajout du bouton "Archiver"
    PostItInitializer.initBoutonArchiver(p, new Button(), boutonsColor);

    // Ajout de la liste déroulante pour choisir les couleur
    PostItInitializer.initColorPicker(p, new ColorPicker(p.couleur), boutonsColor);

    // Autres initialisations
    p.draw();
    PostItInitializer.initMouseListeners(p);
  }

  public static void initLabelContenu(PostIt p, Label label) {
    p.labelContenu = label;
    p.setLayoutLabelContenu();
    label.setTextFill(Color.BLACK);
    label.setWrapText(true);
    label.getStyleClass().add("postItContenu");
    Projet.controleur.panneau.getChildren().add(label);
    label.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
      if (t.getClickCount() == 2) {
        Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
        p.editerTexte();
      }
    });
    label.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent t) -> {
      p.afficheParDessus();
    });
  }

  public static void initialiserBouton(PostIt p, Button bouton, Color couleur, String texte,
      Image icone) {
    // Dessin
    ImageView image = new ImageView(icone);
    image.setFitWidth(TAILLE_IMAGE_BOUTON);
    image.setFitHeight(TAILLE_IMAGE_BOUTON);
    bouton.setGraphic(image);
    bouton.setTooltip(new Tooltip(texte));
    bouton.setBackground(
        new Background(new BackgroundFill(couleur, CornerRadii.EMPTY, Insets.EMPTY)));
    ButtonBar.setButtonData(bouton, ButtonData.LEFT);
    p.buttonBar.getButtons().add(bouton);
    // Handlers
    bouton.setOnAction((ActionEvent event) -> {
      Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
    });
    bouton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent t) -> {
      Projet.controleur.panneau.setCursor(Cursor.HAND);
    });
    bouton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent t) -> {
      Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
    });
  }

  public static void initBoutonEditer(PostIt p, Button bouton, Color couleur) {
    initialiserBouton(p, bouton, couleur, "Éditer", Projet.controleur.editImage);
    // Handlers
    bouton.setOnAction((ActionEvent event) -> {
      p.editerTexte();
    });
  }

  public static void initBoutonSupprimer(PostIt p, Button bouton, Color couleur) {
    initialiserBouton(p, bouton, couleur, "Supprimer", Projet.controleur.deleteImage);
    // Handlers
    bouton.setOnAction((ActionEvent event) -> {
      p.supprimerPostIt();
    });
  }

  public static void initBoutonArchiver(PostIt p, Button bouton, Color couleur) {
    p.boutonArchiver = bouton;
    initialiserBouton(p, bouton, couleur, "Archiver", Projet.controleur.archiveImage);
    // Handlers
    bouton.setOnAction((ActionEvent event) -> {
      p.archiverPostIt();
    });
  }

  public static void initColorPicker(PostIt p, ColorPicker picker, Color couleur) {
    // Dessin
    p.choixCouleur = picker;
    p.setColorPickerLayout();
    p.choixCouleur.setTooltip(new Tooltip("Modifier la couleur"));
    picker.setBackground(
        new Background(new BackgroundFill(couleur, CornerRadii.EMPTY, Insets.EMPTY)));
    Projet.controleur.panneau.getChildren().add(picker);
    // Handlers
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
      if (p.x < 0) {
        p.x = 0;
      }
      if (p.y < 0) {
        p.y = 0;
      }
      if (p.x + p.taille > Projet.controleur.panneau.getWidth()) {
        p.x = Projet.controleur.panneau.getWidth() - p.taille;
      }
      if (p.y + p.taille > Projet.controleur.panneau.getHeight()) {
        p.y = Projet.controleur.panneau.getHeight() - p.taille;
      }
      p.setLayoutX(p.x);
      p.setLayoutY(p.y);
      // Mettre à jour ses composants
      p.setLayoutLabelContenu();
      p.setButtonBarLayout();
      p.setColorPickerLayout();
      // Redessiner le post-it
      p.draw();
    });

    p.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
      if (t.getClickCount() == 2) {
        Projet.controleur.panneau.setCursor(Cursor.DEFAULT);
        p.editerTexte();
      }
    });
  }
}
