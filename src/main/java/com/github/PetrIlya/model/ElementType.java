package com.github.PetrIlya.model;

public enum ElementType {
    FOLDER("image\\file.png"), FILE("image\\folder.jpg");
    private final String path;

    ElementType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
