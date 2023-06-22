package project.home.Window;

import project.home.Window.WindowBlind.WindowBlind;

/**
 *
 * @author �t�p�n Otta
 */
public class Window {
    private WindowState state;
    private WindowBlind windowBlind;

    public Window() {
        state = new WindowClosed(this);
    }
    
    public void open(){
        state.open();
    }
    
    public void close(){
        state.close();
    }

    public WindowState getState() {
        return state;
    }

    public WindowBlind getWindowBlind() {
        return windowBlind;
    }

    public void setWindowBlind(WindowBlind windowBlind) {
        this.windowBlind = windowBlind;
    }

    public void setState(WindowState state) {
        this.state = state;
    }
}
