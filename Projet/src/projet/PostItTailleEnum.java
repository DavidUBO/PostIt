/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

/**
 *
 * @author pondavda
 */
public enum PostItTailleEnum {
    TailleNormale,
    GrandeTaille,
    TresGrandeTaille;
    
    @Override
    public String toString() {
        switch(this){
            case TailleNormale :
                return "Taille normale";
            case GrandeTaille :
                return "Grande taille";
            case TresGrandeTaille :
                return "Très grande taille";
            default :
                return "";
        }
    }
    
    public double getValue(){
        switch(this){
            case TailleNormale :
                return 200;
            case GrandeTaille :
                return 300;
            case TresGrandeTaille :
                return 400;
            default :
                return 200;
        }
    }
    
    public static PostItTailleEnum getEnumValue(String s){
        switch (s) {
            case "Grande taille":
                return PostItTailleEnum.GrandeTaille;
            case "Très grande taille":
                return PostItTailleEnum.TresGrandeTaille;
            default:
                return PostItTailleEnum.TailleNormale;
        }
    }
}
