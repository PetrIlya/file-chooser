package com.github.PetrIlya.model;

public enum ElementType {
    FOLDER("image\\folder.jpg"), FILE("image\\file.png");
    private final String path;

    ElementType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
