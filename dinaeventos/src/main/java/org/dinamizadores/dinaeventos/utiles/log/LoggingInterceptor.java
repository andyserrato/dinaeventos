package org.dinamizadores.dinaeventos.utiles.log;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.Logger;

@Interceptor
@Loggable
public class LoggingInterceptor implements Serializable {

	private static final long serialVersionUID = 742813988565528026L;

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object logMethod(InvocationContext ic) throws Exception {
		logger.debug(ic.getTarget().toString() + " Inicio " + ic.getMethod().getName());
		try {
			return ic.proceed();
		} finally {
			logger.debug(ic.getTarget().toString() + " Fin " + ic.getMethod().getName());
		}
	}
}
