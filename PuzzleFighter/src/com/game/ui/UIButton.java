package com.game.ui;

import com.game.gfx.AssetManager;
import com.game.puzzlefighter.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A UI Button which can be hovered and fire click events when it is clicked.
 */
public class UIButton {

    private final BufferedImage buttonImage;
    private final Rectangle bounds;
    private BufferedImage hoverImage;
    private BufferedImage image;
    private int x, y;
    private ButtonListener buttonListener;

    /**
     * Constructor 1
     *
     * @param imageKey      key of button image
     * @param hoverImageKey key of button hover image
     * @param scale         scale
     */
    public UIButton(String imageKey, String hoverImageKey, float scale) {
        this(imageKey, hoverImageKey, 0, 0, scale);
    }

    /**
     * Constructor
     *
     * @param imageKey      key of button image
     * @param hoverImageKey key of button hover image
     * @param x             z
     * @param y             y
     * @param scale         scale
     */
    public UIButton(String imageKey, String hoverImageKey, int x, int y, float scale) {
        this(imageKey, x, y, scale);
        hoverImage = AssetManager.getImage(hoverImageKey);
    }

    /**
     * Constructor 3
     *
     * @param imageKey key of button image
     * @param x        x
     * @param y        y
     * @param scale    scale
     */
    public UIButton(String imageKey, int x, int y, float scale) {
        buttonImage = AssetManager.getImage(imageKey);
        this.x = x;
        this.y = y;

        int width = (int) (buttonImage.getWidth() * scale);
        int height = (int) (buttonImage.getHeight() * scale);
        bounds = new Rectangle(x, y, width, height);
    }

    public void update() {

        int mouseX = (int) Game.mouseManager.getX();
        int mouseY = (int) Game.mouseManager.getY();

        // set image
        image = bounds.contains(mouseX, mouseY) ? hoverImage : buttonImage;

        // if clicked, fire event
        if (bounds.contains(mouseX, mouseY) && Game.mouseManager.isLeftPressed())
            buttonListener.buttonPressed();
    }

    public void render(Graphics2D g2) {
        g2.drawImage(image, x, y, bounds.width, bounds.height, null);
    }

    /**
     * Sets the location of the button and its collision bound
     *
     * @param x x
     * @param y y
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }

    /**
     * Updates the coordinates of the collision bounds.
     * Function is called when the window resizes.
     *
     * @param x x
     * @param y y
     */
    public void updateBounds(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }

    //getters

    public int getWidth() {
        return bounds.width;
    }

    public int getHeight() {
        return bounds.height;
    }

    public void setButtonListener(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }
}
