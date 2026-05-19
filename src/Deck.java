import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
    public Deck(){
        String[] suits = {"clubs", "hearts", "spades", "diamonds"};
        String[] values = {"A", "J", "Q", "K", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
        for (int s = 0; s < suits.length; s++){
            for (int v = 0; v < values.length; v++){
                cards.add(new Card(suits[s], values[v]));
            }
        };
    }
    public Card getRandomCard(){
        int index = (int) (Math.random()*53);
        return cards.get(index);
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

}
