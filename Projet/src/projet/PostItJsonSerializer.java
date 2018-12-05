/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Fab LC
 */
public final class PostItJsonSerializer {
   
   public static List<PostIt> importerPostIt(File fichier){
      
       List<PostIt> postIts = new LinkedList<>();
           
        try {		
            
            BufferedReader w = new BufferedReader(new InputStreamReader(new FileInputStream(fichier))); 
            JSONObject json = new JSONObject(new String(w.toString()));

            System.out.println("\nLecture du fichier JSON " + fichier + "\n");

            JSONArray tableauPostIt = json.getJSONArray("postits");

		for (int i = 0; i < tableauPostIt.length(); i++) {
                    
                    double x = tableauPostIt.getJSONObject(i).getDouble("x");
                    double y = tableauPostIt.getJSONObject(i).getDouble("y");
                    double taille = tableauPostIt.getJSONObject(i).getDouble("taille");
                    
                    String contenu = tableauPostIt.getJSONObject(i).getString("contenu");     
                    
                    JSONArray tableauCouleur = tableauPostIt.getJSONObject(i).getJSONArray("couleur");
                    Color couleur = new Color(tableauCouleur.getDouble(0), tableauCouleur.getDouble(1), tableauCouleur.getDouble(2), 1.0);
                    
                    PostIt monPostIt = new PostIt(x,y,taille,couleur,contenu);
                    postIts.add(monPostIt);                    
                    
                    System.out.println("- Post It" + contenu + " (Importé)");
                }

		return postIts;

	} catch (JSONException e) {
            e.printStackTrace();
	} catch (IOException e){
            
        }
        
        return null;
   }
   
   public static void exporterPostIt(List<PostIt> postIts, File fichier) throws FileNotFoundException, IOException{
           
        try {		
           
            JSONArray tableauPostIt = new JSONArray();

            System.out.println("\n Création de l'objet JSON" + fichier + "\n");
            
            for (PostIt monPostIt : postIts) {
                
                JSONObject postIt = new JSONObject();
            
                postIt.put("x", monPostIt.x);
                postIt.put("y", monPostIt.y);
                postIt.put("taille", monPostIt.taille);
                postIt.put("contenu", monPostIt.contenu);
                
                JSONObject couleur = new JSONObject();
                
                Double rouge = monPostIt.couleur.getRed();
                Double vert = monPostIt.couleur.getGreen();
                Double bleu = monPostIt.couleur.getBlue();
                
                couleur.put("rouge", rouge);
                couleur.put("vert", vert);
                couleur.put("bleu", bleu);
                 
                postIt.put("couleur", couleur);
                
                tableauPostIt.put(postIt);
                System.out.println("\nAjout du Post It : " + monPostIt.contenu + "\n");
            }   
            
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichier)));   
            w.write(tableauPostIt.toString());
            
	} catch (JSONException e) {
            e.printStackTrace();
	}
        
   }
    
}
