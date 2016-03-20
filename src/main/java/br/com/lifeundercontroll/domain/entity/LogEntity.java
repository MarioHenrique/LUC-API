package br.com.lifeundercontroll.domain.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import br.com.lifeundercontroll.config.LocalDateTimeConverter;

@Entity
@Table(name="Log")
public class LogEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Token")
	private String token;
	
	@Column(name = "callId")
	private String callId;
	
	@Column(name = "endpointPattern")
	private String endpointPattern;
	
	@Column(name = "method")
	private String method;
	
	@Column(name = "responseCode")
	private Integer statusCode;
	
	@Column(name = "errorMessage")
	private String errorMessage;

	@Convert(converter=LocalDateTimeConverter.class)
	@Column(name = "startDate")
	private LocalDateTime startDate;
	
	@Convert(converter=LocalDateTimeConverter.class)
	@Column(name = "endDate")
	private LocalDateTime endDate;
	
	@Column(name = "elapsedTime")
	private Long elapsedTime;
	
	@Column(name = "ipAddress")
	private String ipAddress;
	
	@Column(name = "messageBody", columnDefinition = "TEXT")
	private String messageBody;
	
	@ElementCollection(fetch=FetchType.EAGER)
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="LogQueryParam", joinColumns=@JoinColumn(name="log_id"))
	private Map<String, String> queryParams = new HashMap<String, String>();
	
	@ElementCollection(fetch=FetchType.EAGER)
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="LogPathParam", joinColumns=@JoinColumn(name="log_id"))
	private Map<String, String> pathParams = new HashMap<String, String>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCallId() {
		return callId;
	}
	
	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getEndpointPattern() {
		return endpointPattern;
	}

	public void setEndpointPattern(String endpointPattern) {
		this.endpointPattern = endpointPattern;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public LocalDateTime getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String key, String value) {
		this.queryParams.put(key, value);
	}
	
	public Map<String, String> getPathParams() {
		return pathParams;
	}
	
	public void setPathParams(String key, String value) {
		this.pathParams.put(key, value);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
