public class Card {
    private String suit;
    private String value;
    private String fileName;

    public Card(String s, String v){
        suit = s;
        value = v;
        fileName = "card_" + suit + "_" + value + ".png";
    }

    public String getSuit(){
        return suit;
    }

    public String getValue(){
        return value;
    }

    public String getFileName(){
        return fileName;
    }

    public String toString(){
        return "Suit: " + suit + "\nValue: " + value + "\nFile Name: " + fileName;
    }
}
