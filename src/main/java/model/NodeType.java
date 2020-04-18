package model;

public enum NodeType {
    DIRECTORY("image\\folder.jpg"), FILE("image\\file.png");
    private final String value;

    public String getValue() {
        return value;
    }

    NodeType(String value) {
        this.value = value;
    }
}