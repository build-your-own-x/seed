package com.techzealot.designpatterns.creational.simplefactory;

import java.text.MessageFormat;

public class ApiFactory {
    public static Api createApi(String id) {
        if ("A".equals(id)) {
            return new ApiImplA();
        } else if ("B".equals(id)) {
            return new ApiImplB();
        }
        throw new UnsupportedOperationException(MessageFormat.format("{0}", id));
    }
}
