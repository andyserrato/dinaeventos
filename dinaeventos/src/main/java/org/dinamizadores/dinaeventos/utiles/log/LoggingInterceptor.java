package org.dinamizadores.dinaeventos.utiles.log;


import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.Logger;

@Interceptor
@Loggable
public class LoggingInterceptor {
	
	@Inject
	private Logger logger;
	
	@AroundInvoke
	public Object logMethod(InvocationContext ic) throws Exception {
		logger.info(ic.getTarget().toString() + " " + ic.getMethod().getName());
		try {
			return ic.proceed();
		} finally {
			logger.info(ic.getTarget().toString() + " " + ic.getMethod().getName());
		}
	}
}
