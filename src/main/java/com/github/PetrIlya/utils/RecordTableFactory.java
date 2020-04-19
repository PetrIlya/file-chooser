package com.github.PetrIlya.utils;

import com.github.PetrIlya.model.Record;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class RecordTableFactory {
    public static TableView<Record> getFullTable() {
        TableView<Record> table = new TableView<>();
        TableColumn<Record, ImageView> icon = new TableColumn<>();
        icon.setCellValueFactory(cell -> new SimpleObjectProperty<>(new ImageView(cell.getValue().getType().getPath())));
        TableColumn<Record, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        TableColumn<Record, String> dateColumn = new TableColumn<>("Date");
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getModified().toString()));
        TableColumn<Record, String> extensionColumn = new TableColumn<>("Extension");
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getExtension()));
        TableColumn<Record, String> sizeColumn = new TableColumn<>("Size");
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(Long.toString(cell.getValue().getSize())));
        table.getColumns().addAll(icon, nameColumn, dateColumn, extensionColumn, sizeColumn);
        return table;
    }

    public static TableView<Record> getListTable() {
        TableView<Record> table = new TableView<>();
        TableColumn<Record, ImageView> icon = new TableColumn<>();
        icon.setCellValueFactory(cell -> new SimpleObjectProperty<>(new ImageView(cell.getValue().getType().getPath())));
        TableColumn<Record, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        table.getColumns().addAll(icon, nameColumn);
        return table;
    }
}
