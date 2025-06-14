package org.ms.library.catalog.dto.errors;

import java.time.Instant;

public class ResourceNotFoundMessage {

    private String error;
    private String path;
    private Integer status;
    private Instant timestamp;


    public ResourceNotFoundMessage(String error, String path, Integer status, Instant timestamp) {
        this.error = error;
        this.path = path;
        this.status = status;
        this.timestamp = timestamp;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
