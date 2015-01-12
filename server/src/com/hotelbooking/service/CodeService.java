package com.hotelbooking.service;

import net.sf.json.JSONObject;

import com.hotelbooking.dao.CodeDAO;
import com.hotelbooking.model.Code;

public class CodeService {

	public static String checkCodeResult(String code)
	{
		CodeDAO codeDAO = new CodeDAO();
		Code c = codeDAO.checkCode(code);
		JSONObject resObj = new JSONObject();
		if (c != null && c.getIsUsed() == 0)
		{
			resObj.put("result", "0");
			resObj.put("cut_fee", c.getCutFee());
		}
		else
			resObj.put("result", "1");
		return resObj.toString();
	}
	
	public static void useCode(String code)
	{
		CodeDAO codeDAO = new CodeDAO();
		codeDAO.useCode(code);
	}
}
