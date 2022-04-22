package com.techzealot.designpatterns.structural.decorator.encodecompress;

public class Client {
    public static void main(String[] args) {
        DataSource dataSource =
                new EncryptDataSourceDecorator(
                        new CompressDataSourceDecorator(
                                new CompressDataSourceDecorator(
                                        new EncryptDataSourceDecorator(
                                                new ByteDataSource()
                                        ))));
        dataSource.write("abcdefghijkABCDEFGHIJK");
        System.out.println(dataSource.read());
    }
}
