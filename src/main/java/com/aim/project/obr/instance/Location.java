package main.java.com.aim.project.obr.instance;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public record Location (int iLocationId, int x, int y) {

    static int id = 0;

    public Location(int x, int y) {

        this(id++, x, y);
    }
}
