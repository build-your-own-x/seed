package com.techzealot.designpatterns.structural.decorator.encodecompress;

import java.util.Base64;

public class CompressDataSourceDecorator extends DataSourceDecorator {
    public CompressDataSourceDecorator(DataSource wrappee) {
        super(wrappee);
    }

    @Override
    public void write(String data) {
        String compressed = Base64.getEncoder().encodeToString(data.getBytes());
        System.out.println("compressed:" + compressed);
        wrappee.write(compressed);
    }

    @Override
    public String read() {
        String decompressed = new String(Base64.getDecoder().decode(wrappee.read()));
        System.out.println("decompressed:" + decompressed);
        return decompressed;
    }
}
