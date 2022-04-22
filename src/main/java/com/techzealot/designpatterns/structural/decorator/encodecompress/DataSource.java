package com.techzealot.designpatterns.structural.decorator.encodecompress;

public interface DataSource {
    void write(String data);

    String read();
}
