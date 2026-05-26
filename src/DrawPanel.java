import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;

class DrawPanel extends JPanel implements MouseListener {

    private Deck d;
    private Card[][] currentCards;
    private ArrayList<Card> unusedDeck;
    private Rectangle replaceButton;
    private boolean valid;

    public DrawPanel() {
        d = new Deck();
        unusedDeck = d.getCards();
        currentCards = new Card[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                currentCards[r][c] = unusedDeck.get((int) (Math.random() * unusedDeck.size()));
                for (int i = 0; i < unusedDeck.size(); i++) {
                    if (unusedDeck.get(i).getImageFileName().equals(currentCards[r][c].getImageFileName())) {
                        unusedDeck.remove(i);
                    }
                }
            }
        }
        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        g.drawRect(350, 100, 50, 30);
        replaceButton = new Rectangle(350, 100, 50, 30);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                g.drawImage(currentCards[r][c].getImage(), x * r * 2, y * c * 10, null);
                Rectangle hit = new Rectangle(x * r * 2, y * c * 10, currentCards[r][c].getImage().getWidth(), currentCards[r][c].getImage().getHeight());
                currentCards[r][c].setHitbox(hit);
                if (currentCards[r][c].getHitOn()) {
                    g.setColor(Color.black);
                    g.drawRect(x * r * 2 , y * c * 10, currentCards[r][c].getImage().getWidth(), currentCards[r][c].getImage().getHeight());
                }
                if (currentCards[r][c].isHighlighted()){
                    g.setColor(Color.PINK);
                    g.drawRect(x * r * 2 , y * c * 10, currentCards[r][c].getImage().getWidth(), currentCards[r][c].getImage().getHeight());
                }
            }
        }
        g.setColor(Color.black);
        g.drawString("Number of cards left: " + unusedDeck.size(), x + 100, y + 300);
        if (!valid){
            g.drawString("Invalid combination.", x + 150, y + 350);
        }
    }

    public void mousePressed(MouseEvent e) {

        Point p = e.getPoint();
        int button = e.getButton();
        Point rep = e.getPoint();
        int replace = e.getButton();
        if (button == 1 && replaceButton.contains(rep)){
            valid = true;
            ArrayList<Card> highlightedCards = new ArrayList<>();
            for (int r = 0; r < 3; r++){
                for (int c = 0; c < 3; c++){
                    if (currentCards[r][c].isHighlighted()){
                        highlightedCards.add(currentCards[r][c]);
                    }
                }
            }
            if (highlightedCards.size() > 3 || highlightedCards.size() < 2){
                valid = false;
                for (Card c : highlightedCards){
                    c.setHighlighted();
                }
            }
            else if (highlightedCards.size() == 2){
                if ((Integer.parseInt(highlightedCards.get(0).getValue()) + Integer.parseInt(highlightedCards.get(1).getValue()) == 11) || highlightedCards.get(0).getValue() == "10"){
                    for (int r = 0; r < 3; r++){
                        for (int c = 0; c < 3; c++){
                            if (currentCards[r][c].isHighlighted()){
                                int newInd = (int)(Math.random() * unusedDeck.size());
                                currentCards[r][c] = unusedDeck.get(newInd);
                                unusedDeck.remove(newInd);
                            }
                        }
                    }
                }
            }
            else {
                int jCount = 0;
                int qCount = 0;
                int kCount = 0;
                for (Card c : highlightedCards){
                    if (c.getSuit().equals("J")){
                        jCount++;
                    }
                    if (c.getSuit().equals("Q")){
                        qCount++;
                    }
                    if (c.getSuit().equals("K")){
                        kCount++;
                    }
            }
                if (jCount == 1 && qCount == 1 && kCount == 1) {
                    for (int r = 0; r < 3; r++){
                        for (int c = 0; c < 3; c++){
                            if (currentCards[r][c].isHighlighted()){
                                int newInd = (int)(Math.random() * unusedDeck.size());
                                currentCards[r][c] = unusedDeck.get(newInd);
                                unusedDeck.remove(newInd);
                            }
                        }
                    }
                }
                else {
                    valid = false;
                }
            }
        }
        ArrayList<Card> hc = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (unusedDeck.size() != 0 && button == 1) {
                    if (currentCards[r][c].getHitbox().contains(p)) {
                        boolean currentHit = currentCards[r][c].getHitOn();
                        if (currentCards[r][c].getHitOn() != currentHit){
                            currentCards[r][c].setHitOn();
                        }
                        currentCards[r][c].setHighlighted();
                        for (int i = 0; i < unusedDeck.size(); i++) {
                            if (unusedDeck.get(i).getImageFileName().equals(currentCards[r][c].getImageFileName())) {
                                unusedDeck.remove(i);
                            }
                        }
                    }
                }
                if (button == 3) {
                    if (currentCards[r][c].getHitbox().contains(p)) {
                        currentCards[r][c].setHitOn();
                    }
                }
            }
        }
    }

        public void mouseReleased (MouseEvent e){
        }
        public void mouseEntered (MouseEvent e){
        }
        public void mouseExited (MouseEvent e){
        }
        public void mouseClicked (MouseEvent e){
        }
    }
