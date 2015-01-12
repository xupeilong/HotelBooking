package com.hotelbooking.dao;

import com.hotelbooking.model.Code;

public class CodeDAO extends BaseDAO{

	public void saveOrUpdateCode(Code code)
	{
		save(code);
	}
	
	public Code checkCode(String code)
	{
		return (Code) loadObject("from Code where code = ?", code);
	}
	
	public void useCode(String code)
	{
		Code c = (Code) loadObject("from Code where code = ?", code);
		if (c != null)
		{
			c.setIsUsed(1);
			update(c);
		}
	}
}
