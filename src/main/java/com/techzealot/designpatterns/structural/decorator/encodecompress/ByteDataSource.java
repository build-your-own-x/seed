package com.techzealot.designpatterns.structural.decorator.encodecompress;

public class ByteDataSource implements DataSource {
    private byte[] data;

    @Override
    public void write(String data) {
        this.data = data.getBytes();
    }

    @Override
    public String read() {
        return new String(data);
    }
}
