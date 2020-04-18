package view;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Container for file selection menu
 * Used inside main window to represent files
 */
@Getter
public class FileViewContainer {
    private final VBox topContainer;
    private final TreeView<String> tree;

    public FileViewContainer(TreeView<String> tree) {
        this.topContainer = new VBox();
        this.tree = tree;
        configContainer();
    }

    /**
     * Configures containers
     */
    public void configContainer() {
        this.topContainer.getChildren().add(tree);
    }

    /**
     * @return Returns selected item or null if nothing was selected
     */
    public final String getSelectedFileItem() {
        return tree.getSelectionModel().getSelectedItem().getValue();
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
}
