/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.home.Window;

/**
 *
 * @author �t�p�n Otta
 */
public class WindowClosed implements WindowState{
    private final Window window;

    public WindowClosed (Window window) {
        this.window = window;
    }

    public void close() {
        
    }

    public void open() {
        window.setState(new WindowOpen(window)); 
    }
    
}
