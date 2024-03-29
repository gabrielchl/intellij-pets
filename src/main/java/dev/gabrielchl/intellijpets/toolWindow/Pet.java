package dev.gabrielchl.intellijpets.toolWindow;

import com.intellij.util.ui.JBUI;
import dev.gabrielchl.intellijpets.settings.AppSettingsState;
import dev.gabrielchl.intellijpets.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Pet {
    private enum State {
        ATTACK,
        IDLE,
        JUMP,
        LIEDOWN,
        RUN,
        SIT,
        WALK,
    }

    private final JPanel container;
    private final JPanel petInnerPanel;
    private final JLabel pet;
    private int xIndex;
    private State state;
    private int currentX;
    private int targetX;
    private final HashMap<State, ArrayList<Image>> sprites = new HashMap<>();
    private final int SPRITE_WIDTH;
    private final int SPRITE_HEIGHT;
    private final int SPEED;

    public Pet(JPanel container, JPanel petInnerPanel, JLabel pet) {
        this.container = container;
        this.petInnerPanel = petInnerPanel;
        this.pet = pet;

        xIndex = 0;
        state = State.SIT;
        targetX = 0;

        AppSettingsState settings = AppSettingsState.getInstance().getState();
        String petVariant = settings == null ? "cat-1" : settings.getPetVariant();

        int spriteSize = Utils.petToSpriteSize.get(petVariant);
        double display_scale = 1.6;
        double spriteWidthBase = Math.round(spriteSize * display_scale);
        double spriteHeightBase = Math.round(spriteSize * display_scale);
        double speed_base = 7d / 40 * spriteSize;

        double petScale = settings == null ? 1d : settings.getPetScale();
        SPRITE_WIDTH = (int)Math.round(spriteWidthBase * petScale);
        SPRITE_HEIGHT = (int)Math.round(spriteHeightBase * petScale);
        SPEED = (int)Math.round(speed_base * petScale);

        for (State state : State.values()) {
            Image spriteImg = Toolkit.getDefaultToolkit().createImage(getClass().getResource(String.format("/spritesheets/%s/%s.png", petVariant, state.toString().toLowerCase())));
            Utils.waitForImageToLoad(spriteImg);
            ImageProducer catSource = spriteImg.getSource();

            ArrayList<Image> spriteRow = new ArrayList<>();
            for (int x = 0; x < spriteImg.getWidth(null) / spriteSize; x += 1) {
                Image img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(
                                catSource,
                                new CropImageFilter(spriteSize * x, 0, spriteSize, spriteSize)
                        )
                ).getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_DEFAULT);
                Utils.waitForImageToLoad(img);
                spriteRow.add(img);
            }
            sprites.put(state, spriteRow);
        }

        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (state == State.SIT) {
                    xIndex = 0;
                    state = Math.round((float)Math.random()) == 0 ? State.JUMP : State.ATTACK;
                }
            }
        });
        container.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (Math.abs(e.getX() - (currentX + (SPRITE_WIDTH / 2))) > 30) {
                    targetX = Math.max(0, e.getX() - (SPRITE_WIDTH / 2));
                }
            }
        });
        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                targetX = currentX;
            }
        });
    }

    public void tick() {
        if (currentX != targetX && state != State.WALK) {
            state = State.WALK;
            xIndex = 0;
        }
        if (currentX == targetX && state == State.WALK) {
            state = State.SIT;
            xIndex = 0;
        }
        if (xIndex >= sprites.get(state).size()) {
            xIndex = 0;
            if (state == State.JUMP || state == State.ATTACK) {
                state = State.SIT;
            } else if (state == State.SIT) {
                int rand = new Random().nextInt(10);
                if (rand == 0) {
                    state = State.IDLE;
                }
                if (rand == 1) {
                    state = State.LIEDOWN;
                }
                if (rand == 2) {
                    if (container.getWidth() > 0) {
                        targetX = new Random().nextInt(container.getWidth() - SPRITE_WIDTH);
                    }
                }
            } else if (state == State.IDLE) {
                int rand = new Random().nextInt(10);
                if (rand == 0) {
                    state = State.SIT;
                }
                if (rand == 1) {
                    if (container.getWidth() > 0) {
                        targetX = new Random().nextInt(container.getWidth() - SPRITE_WIDTH);
                    }
                }
            } else if (state == State.LIEDOWN) {
                if (new Random().nextInt(15) == 0) {
                    state = State.SIT;
                }
            }
        }
        boolean toRight = true;
        if (currentX != targetX) {
            int change = Math.abs(targetX - currentX);
            if (change > SPEED) {
                change = SPEED;
            }
            if (targetX < currentX) {
                change *= -1;
                toRight = false;
            }
            currentX = currentX + change;
            petInnerPanel.setBorder(JBUI.Borders.emptyLeft(currentX));
        }
        Image catImg = sprites.get(state).get(xIndex);
        if (!toRight) {
            BufferedImage originalImage = Utils.toBufferedImage(catImg);
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-originalImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage flippedImage = op.filter(originalImage, null);
            ImageIcon catIcon = new ImageIcon(flippedImage.getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_DEFAULT));
            pet.setIcon(catIcon);
        } else {
            ImageIcon catIcon = new ImageIcon(catImg);
            pet.setIcon(catIcon);
        }
        xIndex = xIndex + 1;
    }
}
