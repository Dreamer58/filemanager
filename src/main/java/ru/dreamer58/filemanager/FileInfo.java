package ru.dreamer58.filemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo {

    public enum FileType {
        FILE("F"),
        DIRECTORY("D");

        private String name;

        FileType(String f) {
            this.name = f;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private String filename;
    private FileType type;
    private long size;
    private LocalDateTime lastModified;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public FileInfo(Path path) {
        this.filename = path.getFileName().toString();
        this.type = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;
        if (this.type == FileType.DIRECTORY) {
            this.size = -1L;
        } else {
            try {
                this.size = Files.size(path);
            } catch (IOException e) {
                throw new RuntimeException("Problem file size. Unable to create file info from path: " + path.toAbsolutePath().toString());
            }
        }
        try {
            this.lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(0));
        } catch (IOException e) {
            throw new RuntimeException("Problem file last modified date. Unable to create file info from path: " + path.toAbsolutePath().toString());
        }
    }
}
