import greenfoot.*;
import java.util.List;

public class MyWorld extends World {
    private Avatar avatar;
    private final int spacing = 100;
    private int prevX;

    // Constants for readability
    private static final int WORLD_WIDTH = 400;
    private static final int WORLD_HEIGHT = 1000;
    private static final int AVATAR_START_X = 200;
    private static final int AVATAR_START_Y = 900;
    private static final int DELETE_THRESHOLD_Y = 999;
    private static final int SCROLL_THRESHOLD = 400;
    private static final int INITIAL_PLATFORM_BOTTOM = 200;
    private static final int MAX_X_DISTANCE = 120;

    public MyWorld() {    
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);

        avatar = new Avatar();
        addObject(avatar, AVATAR_START_X, AVATAR_START_Y);

        Ground ground = new Ground();
        prevX = 0;

        // Fill ground along bottom
        for (int i = 0; i <= getWidth() / ground.getImage().getWidth(); i++) {
            ground = new Ground();
            int groundX = i * ground.getImage().getWidth() + ground.getImage().getWidth() / 2;
            int groundY = getHeight() - ground.getImage().getHeight() / 2;
            addObject(ground, groundX, groundY);
        }

        // Place initial platforms
        for (int y = getHeight() - INITIAL_PLATFORM_BOTTOM; y > 0; y -= spacing) {
            placeGround(y);
        }
    }

    public void addScore(Coin coin) {
        removeObject(coin);
        avatar.addScore(coin.getValue());
    }

    public void act() {
        showText("Score: " + avatar.getScore(), 50, 100);
        scrollWorld();
    }

    private void scrollWorld() {
        if (avatar.getY() < SCROLL_THRESHOLD) {
            int dy = SCROLL_THRESHOLD - avatar.getY();
            avatar.setLocation(avatar.getX(), SCROLL_THRESHOLD);

            List<Actor> objects = getObjects(null);
            for (Actor obj : objects) {
                if (obj != avatar && obj.getClass() != Coin.class) {
                    obj.setLocation(obj.getX(), obj.getY() + dy);
                }
            }

            Ground highestGround = getHighestGround();
            if (highestGround == null || highestGround.getY() > spacing + 50) {
                placeGround((highestGround == null ? SCROLL_THRESHOLD : highestGround.getY()) - spacing);
            }
        }

        List<Actor> objects = getObjects(null);
        for (Actor obj : objects) {
            if (obj != avatar && obj.getY() >= DELETE_THRESHOLD_Y) {
                removeObject(obj);
            }
        }
    }

    private Ground getHighestGround() {
        List<Ground> grounds = getObjects(Ground.class);
        Ground highest = null;
        for (Ground g : grounds) {
            if (highest == null || g.getY() < highest.getY()) {
                highest = g;
            }
        }
        return highest;
    }

    public void placeGround(int y) {
        if (y < 80) return;

        Ground ground = new Ground();

        int x;
        if (prevX == 0) {
            // First placement: full range
            x = getRandomInRange(ground.getImage().getWidth(), getWidth() - ground.getImage().getWidth());
        } else {
            // Limit horizontal step to avoid unreachable jumps
            int MAX_X_DISTANCE = 120; // Customize based on avatar's jump strength
            int minX = Math.max(ground.getImage().getWidth(), prevX - MAX_X_DISTANCE);
            int maxX = Math.min(getWidth() - ground.getImage().getWidth(), prevX + MAX_X_DISTANCE);
            x = getRandomInRange(minX, maxX);
        }

        addObject(ground, x, y);

        int groundHeight = ground.getImage().getHeight();

        if (getRandomInRange(1, 5) == 4) {
            Apple coin = new Apple();
            int coinHeight = coin.getImage().getHeight();
            int coinY = y - (groundHeight / 2) - 10 - (coinHeight / 2);

            if (coinY > 0) {
                addObject(coin, x, coinY);
            }
        }
        
        prevX = x;
    }

    private int getCoinYAbove(Ground ground, Apple coin, int y) {
        return y - (ground.getImage().getHeight() / 2) - 10 - (coin.getImage().getHeight() / 2);
    }

    public int getRandomInRange(int min, int max) {
        return Greenfoot.getRandomNumber(max - min + 1) + min;
    }
}
