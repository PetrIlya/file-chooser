package com.github.PetrIlya.view;

import com.github.PetrIlya.utils.TreeHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.List;
import java.util.Optional;

/**
 * Dialog for file choosing
 */
public class FileChooserWindow {
    private final FileViewContainer container;
    private final Dialog<String> window;

    public FileChooserWindow() {
        this.container = new FileViewContainer(TreeHelper.createTree());
        this.window = new Dialog<>();
        configWindow();
    }

    public FileChooserWindow(EventHandler<ActionEvent> eventHandler) {
        this.container = new FileViewContainer(TreeHelper.createTree(), eventHandler);
        this.window = new Dialog<>();
        configWindow();
    }

    /**
     * Configures dialog pane window
     */
    public void configWindow() {
        this.window.getDialogPane().setExpandableContent(container.getTopContainer());
        this.window.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.window.setResultConverter(value -> {
            if (value.equals(ButtonType.OK)) {
                List<String> paths = this.container.getSelectedPath();
                if (paths == null) {
                    return null;
                }
                paths.remove(0);
                return String.join("\\", paths);
            } else {
                return null;
            }
        });
    }

    /**
     * Get current selected path
     *
     * @return Paths or null if nothing was selected
     */
    public Optional<String> getPath() {
        TreeHelper.populateTree(this.container.getTree());
        Optional<String> path = this.window.showAndWait();
        TreeHelper.clearTree(this.container.getTree());
        return path;
    }

    public String getExtensionToFilter() {
        return this.container.getFilterRegExpr().getText();
    }

    public String getFileName() {
        return this.container.getFileName().getText();
    }
}
