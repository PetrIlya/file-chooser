package com.github.PetrIlya.view;

import com.github.PetrIlya.model.Record;
import com.github.PetrIlya.utils.RecordTableFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TableContainer {
    private final List<TableColumn<Record, ?>> deletedColumns;

    private final VBox topContainer;
    private final TableView<Record> table;
    private final HBox buttonContainer;
    private final Button asTable;
    private final Button asList;

    public TableContainer(List<Record> records) {
        this.deletedColumns = new ArrayList<>();
        this.topContainer = new VBox();
        this.buttonContainer = new HBox();

        this.table = RecordTableFactory.getFullTable();
        this.table.getItems().addAll(records);

        this.asTable = new Button("Table view");
        this.asList = new Button("List view");
        configureContainers();
    }

    private void configureContainers() {
        configureButtons();
        this.buttonContainer.getChildren().addAll(asTable, asList);
        this.topContainer.getChildren().addAll(table, buttonContainer);
    }

    private void configureButtons() {
        this.asList.setOnAction(e -> {
            this.deletedColumns.clear();
            this.deletedColumns.addAll(RecordTableFactory.removeUnnecessaryColumns(table));
        });
        this.asTable.setOnAction(e -> {
            this.table.getColumns().addAll(deletedColumns);
            this.deletedColumns.clear();
        });
    }

    public VBox getTopContainer() {
        return topContainer;
    }
}
