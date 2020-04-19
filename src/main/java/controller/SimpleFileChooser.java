package controller;

import view.FileChooserWindow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class SimpleFileChooser extends FileChooser {

    public SimpleFileChooser() {
        this.setWindow(new FileChooserWindow());
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
}
