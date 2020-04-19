package com.github.PetrIlya.utils;

import com.github.PetrIlya.model.Record;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.stream.Collectors;

public class RecordTableFactory {

    public static TableView<Record> getFullTable() {
        TableView<Record> table = new TableView<>();
        TableColumn<Record, ImageView> iconColumn = new TableColumn<>(TableColumnNames.ICON.getName());
        iconColumn.setResizable(false);
        iconColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(new ImageView(cell.getValue().getType().getPath())));
        TableColumn<Record, String> nameColumn = new TableColumn<>(TableColumnNames.NAME.getName());
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        TableColumn<Record, String> dateColumn = new TableColumn<>(TableColumnNames.DATE.getName());
        dateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getModified().toString()));
        TableColumn<Record, String> extensionColumn = new TableColumn<>(TableColumnNames.EXTENSION.getName());
        extensionColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getExtension()));
        TableColumn<Record, String> sizeColumn = new TableColumn<>(TableColumnNames.SIZE.getName());
        sizeColumn.setCellValueFactory(cell -> new SimpleStringProperty(Long.toString(cell.getValue().getSize())));
        table.getColumns().addAll(iconColumn, nameColumn, dateColumn, extensionColumn, sizeColumn);
        table.getColumns().forEach(column -> {
            column.setMinWidth(50D);
            column.setMaxWidth(50D);
        });
        table.setMinWidth(50D * table.getColumns().size());
        table.setMaxWidth(50D * table.getColumns().size());
        return table;
    }

    public static TableView<Record> getListTable() {
        TableView<Record> table = new TableView<>();
        TableColumn<Record, ImageView> icon = new TableColumn<>(TableColumnNames.ICON.getName());
        icon.setCellValueFactory(cell -> new SimpleObjectProperty<>(new ImageView(cell.getValue().getType().getPath())));
        TableColumn<Record, String> nameColumn = new TableColumn<>(TableColumnNames.NAME.getName());
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        table.getColumns().addAll(icon, nameColumn);
        return table;
    }

    public static List<TableColumn<Record, ?>> removeUnnecessaryColumns(TableView<Record> table) {
        List<TableColumn<Record, ?>> toRemove = table.getColumns().
                stream().
                filter(column -> !TableColumnNames.
                        isNecessaryNameName(column.getText())).
                collect(Collectors.toList());
        table.getColumns().removeAll(toRemove);
        return toRemove;
    }
}