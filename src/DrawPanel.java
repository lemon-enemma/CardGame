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
    private ArrayList<Card> highlightedCards;
    private Card[] currentCards1D = new Card[9];
    private Rectangle resetButton;

    public DrawPanel() {
        d = new Deck();
        unusedDeck = d.getCards();
        currentCards = new Card[3][3];
        valid = true;
        highlightedCards = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                currentCards[r][c] = unusedDeck.get((int) (Math.random() * unusedDeck.size()));
                if (unusedDeck.size()-1 >= 0) {
                    for (int i = 0; i < unusedDeck.size(); i++) {
                        if (unusedDeck.get(i).getImageFileName().equals(currentCards[r][c].getImageFileName())) {
                            unusedDeck.remove(i);
                        }
                    }
                }
                else {
                    currentCards[r][c] = null;
                }
            }
        }
        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        int counter = -1;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++){
                counter++;
                currentCards1D[counter] = currentCards[r][c];
            }
        }
        g.drawRect(350, 100, 75, 30);
        replaceButton = new Rectangle(350, 100, 75, 30);
        g.drawString("Check combo", 350, 120);
        g.drawRect(350, 150, 75, 30);
        resetButton = new Rectangle(350, 150, 75, 30);
        g.drawString("Reset cards", 355, 170);
        highlightedCards.clear();
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
                    highlightedCards.add(currentCards[r][c]);
                }
            }
        }
        g.setColor(Color.black);
        g.drawString("Number of cards left: " + unusedDeck.size(), x + 100, y + 300);
        if (!valid){
            g.drawString("Invalid combination.", x + 150, y + 350);
        }
        if (hasNoMatches()){
            g.drawString("No more matches possible. You lost!", x + 200, y + 400);
        }
        boolean allNull = true;
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                if (currentCards[r][c] != null){
                    allNull = false;
                }
            }
        }
        if (allNull){
            g.drawString("You win!", x + 200, y + 400);
        }


    }
    public boolean hasNoMatches() {
        boolean noMatches = true;
        for (int i = 0; i < currentCards1D.length; i++){
            if (currentCards1D[i].getValue().equals("J") || currentCards1D[i].getValue().equals("Q") || currentCards1D[i].getValue().equals("K")){
                boolean ja = false;
                boolean q = false;
                boolean k = false;
                for (int j = 0; j < currentCards1D.length; j++){
                    if (currentCards1D[j].getValue().equals("J")){
                        ja = true;
                    }
                    if (currentCards1D[j].getValue().equals("Q")){
                        q = true;
                    }
                    if (currentCards1D[j].getValue().equals("K")){
                        k = true;
                    }
                }
                if (ja && q && k){
                    noMatches = false;
                }
            }
            else if (currentCards1D[i].getValue().equals("A")){
                for (int j = 0; j < currentCards1D.length; j++){
                    if (currentCards1D[j].getValue().equals("10")){
                        noMatches = false;
                    }
                }
            }
            else {
                int currentNum = Integer.parseInt(currentCards1D[i].getValue());
                for (int j = 0; j < currentCards1D.length; j++){
                    if (!currentCards1D[j].getValue().equals("J") && !currentCards1D[j].getValue().equals("Q")&&!currentCards1D[j].getValue().equals("K")&&!currentCards1D[j].getValue().equals("A")) {
                        if (currentNum + Integer.parseInt(currentCards1D[j].getValue()) == 11 && i != j) {
                            noMatches = false;
                        }
                    }
                }
            }
        }
        return noMatches;
    }



    public void mousePressed(MouseEvent e) {

        Point p = e.getPoint();
        int button = e.getButton();
        if (button == 1 && replaceButton.contains(p)){
            valid = true;
            if (highlightedCards.size() > 3 || highlightedCards.size() < 2){
                for (Card c : highlightedCards){
                    c.setHighlighted();
                }
                valid = false;
            }
            else if (highlightedCards.size() == 2){
                if ((highlightedCards.get(0).getValue().equals("10") && highlightedCards.get(1).getValue().equals("A")) || (highlightedCards.get(1).getValue().equals("10") && highlightedCards.get(0).getValue().equals("A"))){
                    for (int r = 0; r < 3; r++){
                        for (int c = 0; c < 3; c++){
                            if (currentCards[r][c].isHighlighted()){
                                if (unusedDeck.size()-1 >= 0) {
                                    int newInd = (int)(Math.random() * unusedDeck.size());
                                    currentCards[r][c] = unusedDeck.get(newInd);
                                    unusedDeck.remove(newInd);
                                    for (int i = 0; i < unusedDeck.size(); i++) {
                                        if (unusedDeck.get(i).getImageFileName().equals(currentCards[r][c].getImageFileName())) {
                                            unusedDeck.remove(i);
                                        }
                                    }
                                }
                                else {
                                    currentCards[r][c] = null;
                                }
                                int newInd = (int)(Math.random() * unusedDeck.size());
                                currentCards[r][c] = unusedDeck.get(newInd);
                                unusedDeck.remove(newInd);
                            }
                        }
                    }
                }
                else {
                    boolean allNums = true;
                    for (Card c : highlightedCards) {
                        if (c.getValue().equals("A") || c.getValue().equals("J") || c.getValue().equals("Q") || c.getValue().equals("K")) {
                            allNums = false;
                            valid = false;
                        }
                    }
                    if (allNums) {
                        if ((Integer.parseInt(highlightedCards.get(0).getValue()) + Integer.parseInt(highlightedCards.get(1).getValue())) == 11) {
                            for (int r = 0; r < 3; r++) {
                                for (int c = 0; c < 3; c++) {
                                    if (currentCards[r][c].isHighlighted()) {
                                        if (unusedDeck.size()-1 >= 0) {
                                            int newInd = (int)(Math.random() * unusedDeck.size());
                                            currentCards[r][c] = unusedDeck.get(newInd);
                                            unusedDeck.remove(newInd);
                                            for (int i = 0; i < unusedDeck.size(); i++) {
                                                if (unusedDeck.get(i).getImageFileName().equals(currentCards[r][c].getImageFileName())) {
                                                    unusedDeck.remove(i);
                                                }
                                            }
                                        }
                                        else {
                                            currentCards[r][c] = null;
                                        }
                                    }
                                }
                            }
                        } else {
                            valid = false;
                        }
                    }
                }
            }
            else {
                int jCount = 0;
                int qCount = 0;
                int kCount = 0;
                for (Card c : highlightedCards){
                    if (c.getValue().equals("J")){
                        jCount++;

                    }
                    if (c.getValue().equals("Q")){
                        qCount++;
                    }
                    if (c.getValue().equals("K")){
                        kCount++;
                    }
                }
                if (jCount == 1 && qCount == 1 && kCount == 1) {
                    for (int r = 0; r < 3; r++){
                        for (int c = 0; c < 3; c++){
                            if (currentCards[r][c].isHighlighted()){
                                if (unusedDeck.size()-1 >= 0) {
                                    int newInd = (int)(Math.random() * unusedDeck.size());
                                    currentCards[r][c] = unusedDeck.get(newInd);
                                    unusedDeck.remove(newInd);
                                    for (int i = 0; i < unusedDeck.size(); i++) {
                                        if (unusedDeck.get(i).getImageFileName().equals(currentCards[r][c].getImageFileName())) {
                                            unusedDeck.remove(i);
                                        }
                                    }
                                }
                                else {
                                    currentCards[r][c] = null;
                                }
                            }
                        }
                    }
                }
                else {
                    valid = false;
                }
            }
        }
        else if (resetButton.contains(p)){
            Deck f = new Deck();
            unusedDeck = f.getCards();
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
                valid = true;
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
