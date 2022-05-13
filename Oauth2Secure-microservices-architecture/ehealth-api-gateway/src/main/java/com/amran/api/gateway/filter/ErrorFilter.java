package com.amran.api.gateway.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * @Author : Amran Hosssain on 6/27/2020
 */
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("Inside Route Filter");

        return null;
    }
}