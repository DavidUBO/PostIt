/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 *
 * @author pondavda
 */
public class PostIt extends Canvas {
    double x, y;
    double taille = 200;    
    String contenu = "";
    Color couleur;
    
    Label labelContenu;
    ButtonBar buttonBar;
    ColorPicker choixCouleur;
    
    GraphicsContext gc ;
    
    double decalageX, decalageY;   
    
    static final int LABEL_MARGE_VERTICALE = 40;
    static final int LABEL_MARGE_HORIZONTALE = 10;
    static final int BOUTON_MARGE_GAUCHE = 10;
    static final int BOUTON_MARGE_HAUT = 10;
    static final int COULEUR_BOX_MARGE_BAS = 10;
    
    public PostIt(double x, double y) {
        this.x = x;
        this.y = y;
        this.couleur = new Color(1.0, 0.949, 0.8, 1.0);
        init();
        //editerTexte();
    }
    
    public final void init(){
        gc = getGraphicsContext2D();
        PostItInitializer.init(this);
    }
	
    public void draw() {
        gc.clearRect(0, 0, taille, taille);
        gc.setFill(couleur);		
        gc.fillRoundRect(1, 1, taille, taille, 10, 10);
    }
    
    public void componentsToFront(){
        labelContenu.toFront();
        buttonBar.toFront();
        choixCouleur.toFront();
    }
    
    public void editerTexte(){
        String texte = Projet.controleur.getTexteFromDialog(contenu);
        contenu = texte;
        this.labelContenu.setText(texte);
    }
    
    public void supprimerPostIt(){
        Projet.controleur.panneau.getChildren().remove(labelContenu);
        Projet.controleur.panneau.getChildren().remove(buttonBar);
        Projet.controleur.panneau.getChildren().remove(choixCouleur);
        Projet.controleur.panneau.getChildren().remove(this);
    }
    
    public void changerCouleur(){
        this.couleur = this.choixCouleur.getValue();
        this.couleur = new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), 1);
        this.choixCouleur.setValue(this.couleur);
        this.changeCouleurTexteLabelContenu();
        this.draw();
    }
    
    public void changeCouleurTexteLabelContenu(){
        double rouge = this.couleur.getRed();
        double vert = this.couleur.getGreen();
        double bleu = this.couleur.getBlue();
        
        if (rouge <= 0.03928)
            rouge = rouge / 12.92;
        else
            rouge =  Math.pow((rouge + 0.055) / 1.055, 2.4);
        if (vert <= 0.03928)
            vert = vert / 12.92;
        else
            vert =  Math.pow((vert + 0.055) / 1.055, 2.4);
        if (bleu <= 0.03928)
            bleu = bleu / 12.92;
        else
            bleu =  Math.pow((bleu + 0.055) / 1.055, 2.4);
        
        double L = 0.2126 * rouge + 0.7152 * vert + 0.0722 * bleu;
        if(L > Math.sqrt(1.05 * 0.05) - 0.05)
            this.labelContenu.setTextFill(Color.BLACK);
        else
            this.labelContenu.setTextFill(Color.WHITE);
    }
    
    public void afficheParDessus(){
        this.toFront();
        this.componentsToFront();
    }
}
