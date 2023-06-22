package project.home.Window;

/**
 *
 * @author �t�p�n Otta
 */
public class WindowOpen implements WindowState {
    private final Window window;

    public WindowOpen(Window window) {
        this.window = window;
    }

    public void close() {
        window.setState(new WindowClosed(window));
    }

    public void open() {
        
    }
    
}
