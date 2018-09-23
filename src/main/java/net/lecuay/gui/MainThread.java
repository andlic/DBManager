
package net.lecuay.gui;

/**
 *
 * @author Javier Trancoso Lara
 */
public class MainThread {

    static Window window;

    public static void initGUI() {
        window = new Window();
    }

    public static Window getWindow() {
        return window;
    }
}
