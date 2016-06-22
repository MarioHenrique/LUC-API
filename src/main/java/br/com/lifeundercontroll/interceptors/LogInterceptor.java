package br.com.lifeundercontroll.interceptors;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.lifeundercontroll.entity.LogEntity;
import br.com.lifeundercontroll.repository.LogRepository;

@Component
@Order(value = 2)
public class LogInterceptor extends HandlerInterceptorAdapter {

	Logger logger = Logger.getLogger(LogInterceptor.class);

	@Autowired
	private LogRepository logRepository;
	
	private static final String ERROR_ATTRIB_SPRING = "org.springframework.boot.autoconfigure.web.DefaultErrorAttributes.ERROR";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		LogEntity logEntity = new LogEntity();
		
		String method = request.getMethod();
		String callId = UUID.randomUUID().toString();

		logEntity.setCallId(callId);
		logEntity.setMethod(method);
		logEntity.setToken(request.getHeader("token"));

		logEntity.setCallId(callId);
		logEntity.setIpAddress(request.getRemoteAddr());
		logEntity.setEndpointPattern((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		logEntity.setMethod(request.getMethod());
		logEntity.setStartDate(LocalDateTime.now());

		Map<String, String[]> queryParams = request.getParameterMap();

		queryParams.forEach((k, v) -> logEntity.setQueryParams(k, v[0]));

		saveBody(request, logEntity);

		request.setAttribute("REQUEST_LOG", logEntity);

		response.addHeader("CALL_ID", callId);

		return true;

	}

	private void saveBody(HttpServletRequest request, LogEntity logEntity) throws IOException {
		if (HttpMethod.POST.name().equals(request.getMethod()) || HttpMethod.PUT.name().equals(request.getMethod())) {

			Optional<BufferedReader> bufferedReader = Optional.ofNullable(request.getReader());

			if (bufferedReader.isPresent()) {
				StringBuilder messageBody = new StringBuilder();
				int value = 0;
				while ((value = bufferedReader.get().read()) != -1) {
					messageBody.append((char) value);
				}

				logEntity.setMessageBody(messageBody.toString());
			}
		}
	}


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)    throws Exception {

        LogEntity logEntity = (LogEntity) request.getAttribute("REQUEST_LOG");
        
        if (logEntity != null) {
        	logEntity.setEndDate(LocalDateTime.now());
        	logEntity.setStatusCode(response.getStatus());
        	logEntity.setElapsedTime(logEntity.getStartDate().until(logEntity.getEndDate(), ChronoUnit.MILLIS));
	        
	        Optional<Object> errorMessage = Optional.ofNullable(request.getAttribute(ERROR_ATTRIB_SPRING));
	        
	        errorMessage.ifPresent(e -> logEntity.setErrorMessage(e.toString()));

	        logger.debug("CallId: "+logEntity.getCallId()+" Request: "+logEntity.getEndpointPattern()+" method: "+logEntity.getMethod());
	        
	        logRepository.save(logEntity);
	        	        
        }
    }
	
}
