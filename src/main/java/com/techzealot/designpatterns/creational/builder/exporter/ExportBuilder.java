package com.techzealot.designpatterns.creational.builder.exporter;

public interface ExportBuilder {

    void buildHeader(Input input);

    void buildBody(Input input);

    void buildFooter(Input input);

    Output getOutput();
}
