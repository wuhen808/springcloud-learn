package com.wuhen.filter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class BasicAuthFilter  extends ZuulFilter implements InitializingBean {
	private String encodedAuth;
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, encodedAuth);
		return null;
	}

	
	@Override
	public void afterPropertiesSet() throws Exception {
		encodedAuth = "Basic YWRtaW46YWRtaW4=";
	}
}

