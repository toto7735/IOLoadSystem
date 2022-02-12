package me.toto7735.data;

import java.util.UUID;

public class Data {

    final private UUID uuid;
    final private int width;
    final private int height;

    public Data(UUID uuid, int width, int height) {
        this.uuid = uuid;
        this.width = width;
        this.height = height;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    
}
