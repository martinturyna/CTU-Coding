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
public class WindowBlind {
    private WindowBlindState state;

    public WindowBlind() {
        state = new WindowBlindOpen(this);
    }
    
    public void open(){
        state.open();
    }
    
    public void close(){
        state.close();
    }

    public WindowBlindState getState() {
        return state;
    }

    public void setState(WindowBlindState state) {
        this.state = state;
    }
}
