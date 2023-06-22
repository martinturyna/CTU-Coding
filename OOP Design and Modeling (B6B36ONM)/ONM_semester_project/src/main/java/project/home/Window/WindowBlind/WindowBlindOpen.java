/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.home.Window.WindowBlind;

/**
 *
 * @author �t�p�n Otta
 */
public class WindowBlindOpen implements WindowBlindState {
    private final WindowBlind windowBlind;

    public WindowBlindOpen(WindowBlind windowBlind) {
        this.windowBlind = windowBlind;
    }

    public void close() {
        windowBlind.setState(new WindowBlindClose(windowBlind));
    }

    public void open() {
    
    }
    
}
