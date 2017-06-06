package com.autentia.tutos.springboot.services;

public class FaultExceptionService extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private FaultService faultService;

	public FaultExceptionService(String message, FaultService faultService) {
		super(message);
		this.faultService = faultService;
	}

	public FaultExceptionService(String message, Throwable e, FaultService faultService) {
		super(message, e);
		this.faultService = faultService;
	}

	public FaultService getFaultService() {
		return faultService;
	}

	public void setServiceFault(FaultService faultService) {
		this.faultService = faultService;
	}

}
