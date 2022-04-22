package com.techzealot.designpatterns.structural.decorator.encodecompress;

public class DataSourceDecorator implements DataSource {
    protected DataSource wrappee;

    public DataSourceDecorator(DataSource wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void write(String data) {
        wrappee.write(data);
    }

    @Override
    public String read() {
        return wrappee.read();
    }
}
