package com.hotelbooking.model;

import net.sf.json.JSONObject;

public class Result {
	private int resultCode;
	private String resultMessage;
	private JsonObject dataObject;
	
	public Result(int resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public int getResultCode() {
		return resultCode;
	}
	
	public String toJsonString()
	{
		JSONObject obj = new JSONObject();
		obj.put("result_code", resultCode);
		obj.put("result_message", resultMessage);
		if (dataObject != null)
			obj.put(dataObject.getJsonName(), dataObject.getJsonValue());
		return obj.toString();
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public JsonObject getDataObject() {
		return dataObject;
	}

	public void setDataObject(JsonObject dataObject) {
		this.dataObject = dataObject;
	}
	
}
