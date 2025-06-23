package TicTacToe;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, BLACK, WHITE, or NO_SEED.
 *
 * We also attach a display image icon (text or image) for the items.
 * To draw the image:
 *   g.drawImage(content.getImage(), x, y, width, height, null);
 */
public enum Seed {
    CROSS("X", "Miya.png"),
    NOUGHT("O", "Zilong.png"),
    NO_SEED(" ", null),
    BLACK("B", "Black.png"),
    WHITE("W", "White.png");

    private final String displayName;
    private final Image img;

    private Seed(String name, String imageFilename) {
        this.displayName = name;

        // Load image if filename is provided
        if (imageFilename != null) {
            URL imgURL = getClass().getClassLoader().getResource(imageFilename);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                this.img = icon.getImage();
            } else {
                System.err.println("Couldn't find file " + imageFilename);
                // Fallback image in case file not found
                this.img = null;
            }
        } else {
            this.img = null;
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public Image getImage() {
        // If image is null (not found), you can create a default visual or use a fallback.
        if (img == null) {
            return createFallbackImage(); // Implement a fallback drawing method
        }
        return img;
    }

    // A fallback method to create a simple image if not found
    private Image createFallbackImage() {
        // Here you could return a simple image, for example, a blank or a text image.
        // Alternatively, draw simple X or O using Java graphics (basic drawing code).
        // This is just an example of a method you can use to handle missing images.
        // If you use a default image, return it here.
        return new ImageIcon("default_image.png").getImage();
    }
}
