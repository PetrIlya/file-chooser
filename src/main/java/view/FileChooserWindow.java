package view;

import javafx.scene.control.Dialog;

import java.io.File;


public class FileChooserWindow {
    private final FileViewContainer container;
    private final Dialog<File> window;

    public FileChooserWindow(FileViewContainer container, Dialog<File> window) {
        this.container = container;
        this.window = window;
    }
}
