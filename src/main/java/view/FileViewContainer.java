package view;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import model.FileItem;

/**
 * Container for file selection menu
 * Used inside main window to represent files
 */
@Getter
public class FileViewContainer {
    private final VBox topContainer;
    private final TreeView<FileItem> tree;
    private final HBox buttonContainer;
    private final Button confirmButton;
    private final Button discardButton;

    public FileViewContainer(TreeView<FileItem> tree, Button confirmButton, Button discardButton) {
        this.topContainer = new VBox();
        this.buttonContainer = new HBox();
        this.tree = tree;
        this.confirmButton = confirmButton;
        this.discardButton = discardButton;
        configContainers();
    }

    /**
     * Configures containers
     */
    public void configContainers() {
        this.buttonContainer.getChildren().addAll(confirmButton, discardButton);
        this.topContainer.getChildren().addAll(tree, buttonContainer);
    }

    /**
     * @return Returns selected item or null if nothing was selected
     */
    public final FileItem getSelectedFileItem() {
        return tree.getSelectionModel().getSelectedItem().getValue();
    }
}
