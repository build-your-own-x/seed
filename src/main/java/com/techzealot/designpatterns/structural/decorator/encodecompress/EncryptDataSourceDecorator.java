package com.techzealot.designpatterns.structural.decorator.encodecompress;

public class EncryptDataSourceDecorator extends DataSourceDecorator {
    public EncryptDataSourceDecorator(DataSource wrappee) {
        super(wrappee);
    }

    @Override
    public void write(String data) {
        byte[] bytes = data.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] += 1;
        }
        String encrypt = new String(bytes);
        System.out.println("encrypt:" + encrypt);
        wrappee.write(encrypt);
    }

    @Override
    public String read() {
        byte[] bytes = wrappee.read().getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] -= 1;
        }
        String s = new String(bytes);
        System.out.println("decrypt:" + s);
        return s;
    }
}
