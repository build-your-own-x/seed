package com.techzealot.designpatterns.creational.builder;

public class ExcelExportBuilder implements ExportBuilder {

    private Output output;

    @Override
    public void buildHeader(Input input) {

    }

    @Override
    public void buildBody(Input input) {

    }

    @Override
    public void buildFooter(Input input) {

    }

    public Output getOutput() {
        return output;
    }
}
