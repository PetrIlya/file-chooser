package com.github.PetrIlya.controller;

import com.github.PetrIlya.model.ElementType;
import com.github.PetrIlya.model.Record;
import com.github.PetrIlya.view.FileChooserWindow;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleFileChooser extends FileChooser {
    private final List<Record> tableRecords;

    public SimpleFileChooser() {
        this.tableRecords = new ArrayList<>();
        this.setWindow(new FileChooserWindow(this::filter, tableRecords));
    }

    @Override
    public Optional<File> load() {
        Optional<String> path = this.getWindow().
                getPath();
        return path.map(File::new);
    }

    @Override
    public Optional<File> save(String fileName) throws IOException {
        Optional<String> pathToSave = this.getWindow().getPath();
        if (pathToSave.isPresent()) {
            File fileToSave = new File(String.valueOf(Path.of(pathToSave.get(), fileName)));
            if (fileToSave.createNewFile()) {
                return Optional.of(fileToSave);
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<File> save() throws IOException {
        Optional<String> pathToSave = this.getWindow().getPath();
        if (pathToSave.isPresent()) {
            File fileToSave = new File(String.valueOf(Path.of(pathToSave.get(), this.getWindow().getFileName())));
            if (fileToSave.createNewFile()) {
                return Optional.of(fileToSave);
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private void filter(ActionEvent e) {
        this.tableRecords.removeIf(record -> {
            if (ElementType.FILE.equals(record.getType())) {
                return !record.getExtension().
                        equals(this.getWindow().getExtensionToFilter());
            }
            return false;
        });
    }
}
