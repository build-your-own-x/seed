package com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationFilterChain implements FilterChain {

    /**
     * The int which is used to maintain the current position
     * in the filter chain.
     */
    private int pos = 0;

    private List<Filter> filters = new ArrayList<>();


    @Override
    public void doFilter(Request request, Response response) throws Exception {
        if (pos < filters.size()) {
            Filter filter = filters.get(pos);
            pos++;
            //通过在filter中调用该方法实现递归,从而巧妙的使各Filter.doFilter种chain.doFilter分割的两段代码前半部分顺序执行，后半部分倒序执行
            //并且后半部分执行时已经获取到了Response
            filter.doFilter(request, response, this);
            return;
        }
        // We fell off the end of the chain -- call the servlet instance
        doService();
    }

    private void doService() {
        System.out.println("here we handle get/post request and return response");
    }

    public void addFilters(Filter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }
}
