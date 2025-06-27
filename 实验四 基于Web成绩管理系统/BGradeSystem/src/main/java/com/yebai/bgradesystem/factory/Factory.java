package com.yebai.bgradesystem.factory;

public interface Factory {
    public Object produce(int id, Object... args);
}
