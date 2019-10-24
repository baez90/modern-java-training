package com.github.baez90.training.server.jokes;

public class ICNDBAPIResponse<T> {
    private String type;
    private T value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return this.type != null && this.type.equals("success");
    }
}
