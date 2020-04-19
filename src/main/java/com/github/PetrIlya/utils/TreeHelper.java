package com.github.PetrIlya.utils;

import com.github.PetrIlya.model.ElementType;
import com.github.PetrIlya.model.Record;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TreeHelper {
    /**
     * Returns simple tree
     *
     * @param fictionalRoot fiction root to start tree
     * @return tree structure
     */
    public static TreeView<String> createTree(TreeItem<String> fictionalRoot) {
        TreeView<String> tree = new TreeView<>();
        tree.setRoot(fictionalRoot);
        return tree;
    }

    /**
     * Produces a tree with empty string as a root
     *
     * @return tree with root contains empty string
     */
    public static TreeView<String> createTree() {
        TreeView<String> tree = new TreeView<>();
        tree.setRoot(new TreeItem<>(""));
        return tree;
    }

    /**
     * Populates tree with basic data and lazily populates all subdirectories
     *
     * @param tree tree to populate
     */
    public static void populateTree(TreeView<String> tree) {
        Arrays.stream(File.listRoots()).
                sorted().
                forEach(rootDirectory -> {
                    TreeItem<String> subRoot = new TreeItem<>(rootDirectory.getAbsolutePath());
                    tree.getRoot().getChildren().add(subRoot);
                    populateNodeWithData(subRoot, rootDirectory);
                });
    }

    /**
     * Populates tree with basic directories only and lazily populates all subdirectories
     *
     * @param tree tree to populate
     */
    public static void populateTreeWithDirectories(TreeView<String> tree) {
        Arrays.stream(File.listRoots()).
                sorted().
                forEach(rootDirectory -> {
                    TreeItem<String> subRoot = new TreeItem<>(rootDirectory.getAbsolutePath());
                    tree.getRoot().getChildren().add(subRoot);
                    populateNodeWithData(subRoot, rootDirectory);
                });
    }

    /**
     * Lazily populates nodes with data when they are expanded
     *
     * @param root node to add childs
     * @param file file to get data
     */
    public static void populateNodeWithData(TreeItem<String> root, File file) {
        if (file.listFiles() != null) {
            Arrays.stream(Objects.requireNonNull(file.listFiles())).
                    filter(element -> !element.isHidden()).
                    filter(File::canRead).
                    sorted((first, second) -> {
                        if (first.isDirectory() == second.isDirectory()) {
                            return 0;
                        } else {
                            if (first.isDirectory()) {
                                return -1;
                            }
                        }
                        return 1;
                    }).
                    forEach(correctElement -> {
                                TreeItem<String> subRoot = new TreeItem<>(correctElement.getName());
                                if (correctElement.isDirectory()) {
                                    subRoot.setGraphic(new ImageView("image/folder.jpg"));
                                    if (correctElement.listFiles() != null &&
                                            Objects.requireNonNull(correctElement.listFiles()).length > 0) {
                                        TreeItem<String> mockItem = new TreeItem<>("");
                                        subRoot.getChildren().add(mockItem);
                                        subRoot.expandedProperty().addListener(
                                                ((observable, oldValue, newValue) -> {
                                                    if (!oldValue && newValue) {
                                                        subRoot.getChildren().remove(mockItem);
                                                        populateNodeWithData(subRoot, correctElement);
                                                    } else {
                                                        subRoot.getChildren().clear();
                                                        subRoot.getChildren().add(mockItem);
                                                    }
                                                }
                                                )
                                        );
                                    }
                                } else {
                                    subRoot.setGraphic(new ImageView("image/file.png"));
                                }
                                root.getChildren().add(subRoot);
                            }
                    );
        }
    }

    /**
     * Clears root of the tree
     *
     * @param tree tree to clear
     */
    public static void clearTree(TreeView<String> tree) {
        tree.getRoot().getChildren().clear();
    }

    /**
     * Populates tree with basic data and lazily populates all subdirectories
     * and binds it with table
     *
     * @param tree tree to populate
     */
    public static void populateTree(TreeView<String> tree, List<Record> records) {
        Arrays.stream(File.listRoots()).
                sorted().
                forEach(rootDirectory -> {
                    TreeItem<String> subRoot = new TreeItem<>(rootDirectory.getAbsolutePath());
                    tree.getRoot().getChildren().add(subRoot);
                    populateNodeWithDirectories(subRoot, rootDirectory, records);
                });
    }

    /**
     * Lazily populates node with subdirs only
     *
     * @param root node to populate
     * @param file file to start from
     */
    public static void populateNodeWithDirectories(TreeItem<String> root, File file, List<Record> records) {
        if (file.listFiles() != null) {
            Arrays.
                    stream(Objects.requireNonNull(file.listFiles())).
                    filter(element -> !element.isHidden()).
                    filter(File::canRead).
                    filter(File::canWrite).
                    filter(File::isDirectory).
                    forEach(
                            correctElement -> {
                                TreeItem<String> subRoot = new TreeItem<>(correctElement.getName());
                                subRoot.setGraphic(new ImageView("image/folder.jpg"));
                                if (correctElement.listFiles() != null
                                        && Objects.requireNonNull(correctElement.listFiles()).length > 0) {
                                    TreeItem<String> mockItem = new TreeItem<>("");
                                    subRoot.getChildren().add(mockItem);
                                    subRoot.expandedProperty().addListener(
                                            ((observable, oldValue, newValue) -> {
                                                if (!oldValue && newValue) {
                                                    subRoot.getChildren().remove(mockItem);
                                                    populateNodeWithDirectories(subRoot, correctElement, records);
                                                } else {
                                                    subRoot.getChildren().clear();
                                                    subRoot.getChildren().add(mockItem);
                                                }
                                            }
                                            )
                                    );
                                }
                                root.getChildren().add(subRoot);
                            }
                    );
        }
    }

    /**
     * Populates list with data from file content (subdirectories, files)
     *
     * @param records list to populate
     * @param file    file to get data from
     */
    public static void populateListWithData(File file, List<Record> records) {
        if (file.listFiles() != null) {
            Arrays.
                    stream(Objects.requireNonNull(file.listFiles())).
                    filter(element -> !element.isHidden()).
                    filter(File::canRead).
                    filter(File::canWrite).
                    sorted((first, second) -> {
                        if (first.isDirectory() == second.isDirectory()) {
                            return 0;
                        } else {
                            if (first.isDirectory()) {
                                return -1;
                            }
                        }
                        return 1;
                    }).
                    forEach(
                            correctElement -> {
                                ElementType type = ElementType.FOLDER;
                                String name = correctElement.getName();
                                Date date = new Date(correctElement.lastModified());
                                long size = Long.MIN_VALUE;
                                String extension = "";
                                if (!correctElement.isDirectory()) {
                                    type = ElementType.FILE;
                                    if (name.lastIndexOf(".") > 0) {
                                        extension = name.substring(name.lastIndexOf(".") + 1);
                                    }
                                    try {
                                        size = Files.size(Path.of(file.getAbsolutePath()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                records.add(new Record(type, name, date, extension, size));
                            }
                    );
        }
    }
}
