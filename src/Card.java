import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private BufferedImage image;
    private Rectangle hitbox;
    private boolean hitOn;
    private boolean highlighted;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.imageFileName = "card_"+suit+"_"+value+".png";
        this.image = readImage();
        this.hitbox = new Rectangle(-10, -10, image.getWidth(), image.getHeight());
        this.hitOn = true;
        this.highlighted = false;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public String getSuit() {
        return suit;
    }


    public String getValue() {
        return value;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String toString() {
        return suit + " " + value;
    }

    public boolean getHitOn(){
        return hitOn;
    }

    public void setHitOn(){
        if (hitOn){
            hitOn = false;
        }
        else {
            hitOn = true;
        }
    }

    public boolean isHighlighted(){
        return highlighted;
    }

    public void setHighlighted(){
        if (highlighted){
            highlighted = false;
        }
        else {
            highlighted = true;
        }
    }



    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage readImage() {
        try {
            BufferedImage image;
            image = ImageIO.read(new File("images/" + imageFileName));
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

}