package com.program.centerapi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import microservice.api.IpAccessor;
import microservice.api.ServiceApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.util.NestedServletException;

import com.caucho.hessian.io.Hessian2Output;

public class ApiHessianHandler extends HessianServiceExporter {
	private static Logger logger = LoggerFactory.getLogger(ApiHessianHandler.class);

	public ApiHessianHandler() {
		setServiceInterface(ServiceApi.class);
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!"POST".equals(request.getMethod())) {
			response.getWriter().write("");
			response.getWriter().flush();
			return;
		}
		if (!allowable(request, response)) {
			return;
		}
		response.setContentType(CONTENT_TYPE_HESSIAN);
		try {
			invoke(request.getInputStream(), response.getOutputStream());
		} catch (Throwable ex) {
			throw new NestedServletException("Channel skeleton invocation failed", ex);
		}
	}

	protected boolean allowable(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String local = request.getLocalAddr();
			String remote = request.getRemoteHost();
			String client = request.getHeader("X-Real-IP");
			logger.info("local:" + local + ",remote:" + remote + ",client:" + client);
			if (this.getService() instanceof IpAccessor) {
				IpAccessor a = (IpAccessor) this.getService();
				if (!a.allowable(remote) && !a.allowable(client)) {
					Hessian2Output out = new Hessian2Output(response.getOutputStream());
					out.writeFault("IOException", "AccessDenied", null);
					out.close();
					return false;
				}
			}
		} catch (Exception e) {
			Hessian2Output out = new Hessian2Output(response.getOutputStream());
			out.writeFault("IOException", "AccessDeniedException", null);
			out.close();
			return false;
		}
		return true;
	}

}
