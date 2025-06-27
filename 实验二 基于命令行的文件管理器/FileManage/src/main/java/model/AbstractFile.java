package model;

public abstract class AbstractFile {
    private String path;
    private String name;
    private long size;
    private long changeTime;
    private String type;

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public String getType() {
        return type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public void setType(String type) {
        this.type = type;
    }
}
