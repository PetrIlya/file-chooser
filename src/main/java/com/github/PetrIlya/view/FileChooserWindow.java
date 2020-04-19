package com.github.PetrIlya.view;

import com.github.PetrIlya.model.Record;
import com.github.PetrIlya.utils.TreeHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Optional;

/**
 * Dialog for file choosing
 */
public class FileChooserWindow {
    private final HBox topContainer;
    private final FileViewContainer treeContainer;
    private final TableContainer tableContainer;
    private final Dialog<String> window;

    public FileChooserWindow() {
        this.topContainer = new HBox();
        this.treeContainer = new FileViewContainer(TreeHelper.createTree());
        this.tableContainer = null;
        this.window = new Dialog<>();
        configWindow();
    }

    public FileChooserWindow(EventHandler<ActionEvent> eventHandler, List<Record> records) {
        this.topContainer = new HBox();
        this.treeContainer = new FileViewContainer(TreeHelper.createTree(), eventHandler);
        this.tableContainer = new TableContainer(records);
        this.window = new Dialog<>();
        configWindow();
    }

    /**
     * Configures dialog pane window
     */
    public void configWindow() {
        this.topContainer.getChildren().
                addAll(this.treeContainer.getTopContainer(),
                        this.tableContainer.getTopContainer());
        this.window.getDialogPane().setExpandableContent(this.topContainer);
        this.window.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.window.setResultConverter(value -> {
            if (value.equals(ButtonType.OK)) {
                List<String> paths = this.treeContainer.getSelectedPath();
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
        TreeHelper.populateTree(this.treeContainer.getTree());
        Optional<String> path = this.window.showAndWait();
        TreeHelper.clearTree(this.treeContainer.getTree());
        return path;
    }

    public String getExtensionToFilter() {
        return this.treeContainer.getFilterRegExpr().getText();
    }

    public String getFileName() {
        return this.treeContainer.getFileName().getText();
    }
}
