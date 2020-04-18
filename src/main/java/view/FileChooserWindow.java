package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.List;


public class FileChooserWindow {
    private final FileViewContainer container;
    private final Dialog<String> window;

    public FileChooserWindow(FileViewContainer container) {
        this.container = container;
        this.window = new Dialog<>();
        configWindow();
    }

    public void configWindow() {
        this.window.getDialogPane().setContent(container.getTopContainer());
        this.window.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.window.setResultConverter(value -> {
            if (value.equals(ButtonType.OK)) {
                List<String> paths = this.container.getSelectedPath();
                if (paths == null) {
                    return null;
                }
                return String.join("\\", paths);
            } else {
                return null;
            }
        });
    }
}
