package com.techzealot.designpatterns.creational.builder;

public class Client {

    public static void main(String[] args) {
        ExportBuilder xmlExportBuilder = new XmlExportBuilder();
        Director director = new Director(xmlExportBuilder);
        Output output = director.build(new Input() {
        });
    }
}
