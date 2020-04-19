package com.github.PetrIlya.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Container for file selection menu
 * Used inside main window to represent files
 */
public class FileViewContainer {
    private final VBox topContainer;
    private final TreeView<String> tree;
    private final HBox textContainer;
    private final TextField fileName;
    private final VBox filterContainer;
    private final TextField filterRegExpr;
    private final Button filterButton;

    public FileViewContainer(TreeView<String> tree) {
        this.topContainer = new VBox();
        this.tree = tree;
        this.textContainer = new HBox();
        this.fileName = new TextField();
        this.filterContainer = new VBox();
        this.filterRegExpr = new TextField();
        this.filterButton = new Button("Filter files by RegExpr");
        configContainers();
    }

    /**
     * Configures containers
     */
    public void configContainers() {
        this.filterContainer.
                getChildren().
                addAll(
                        filterRegExpr,
                        filterButton);
        this.textContainer.getChildren().addAll(fileName, filterContainer);
        this.topContainer.getChildren().addAll(tree, textContainer);
    }

    /**
     * @return Returns selected item or null if nothing was selected
     */
    public final TreeItem<String> getSelectedFileItem() {
        return tree.getSelectionModel().getSelectedItem();
    }

    /**
     * @return Current selected path or null if nothing was selected
     */
    public final List<String> getSelectedPath() {
        TreeItem<String> treeItem = this.tree.getSelectionModel().getSelectedItem();
        if (treeItem == null) {
            return null;
        }
        List<String> paths = new ArrayList<>();
        do {
            paths.add(treeItem.getValue());
            treeItem = treeItem.getParent();
        } while (treeItem != null);
        Collections.reverse(paths);
        return paths;
    }

    public VBox getTopContainer() {
        return this.topContainer;
    }

    public TreeView<String> getTree() {
        return this.tree;
    }
}
