package com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.Filter;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.ProtocolFilterWrapper;

import java.util.ArrayList;
import java.util.List;

public class TestProtocolFilterWrapper extends ProtocolFilterWrapper {
    @Override
    public List<Filter> getFilters() {
        List<Filter> filters = new ArrayList<>();
        filters.add(new FilterA());
        filters.add(new FilterB());
        filters.add(new FilterC());
        return filters;
    }
}
