package it.interno.anpr.config;

public class ParamHandler {

	private String dataRequest;
	
	public String getDataRequest() {
		return dataRequest;
	}
	public void setDataRequest(String dataRequest) {
		this.dataRequest = dataRequest;
	}
	private String fileRequest;
	private WSTypeHandler wsType;

	public String getFileRequest() {
		return fileRequest;
	}
	public void setFileRequest(String fileRequest) {
		this.fileRequest = fileRequest;
	}
	public void setEnvironment(String environment) {
		EnvironmentHandler.setEnv(environment);
	}
	public WSTypeHandler getWsType() {
		return wsType;
	}
	public void setWsType(WSTypeHandler wsType) {
		this.wsType = wsType;
	}

}
