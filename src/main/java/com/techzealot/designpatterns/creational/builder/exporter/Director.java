package com.techzealot.designpatterns.creational.builder.exporter;

public class Director {

    private ExportBuilder builder;

    public Director(ExportBuilder builder) {
        this.builder = builder;
    }

    public Output build(Input input) {
        builder.buildHeader(input);
        builder.buildBody(input);
        builder.buildFooter(input);
        return builder.getOutput();
    }
}
