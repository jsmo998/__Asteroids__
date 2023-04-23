package java_module.__asteroids__;

public enum ScreenUse {
    /** used to keep track of items which loop through the edges of the screen
     *  and those that only cross the screen once
     */
    INFINITE, // player and asteroids
    SINGULAR // alien and bullets
}
