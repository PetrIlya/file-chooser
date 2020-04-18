package model;

import lombok.Data;

@Data
public class FileItem {
    private NodeType type;
    private String absolutePath;
    private String name;
}
