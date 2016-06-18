package org.dinamizadores.dinaeventos.utiles.log;


import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class LoginProducer {
	@Produces
	private Logger createLogger(InjectionPoint injectionPoint) {
//		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
		return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}
