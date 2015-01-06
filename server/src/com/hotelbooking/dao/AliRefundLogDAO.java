package com.hotelbooking.dao;

import java.sql.Date;
import java.util.List;

import javassist.expr.NewArray;

import com.hotelbooking.model.AliRefundLog;
import com.hotelbooking.util.DateFormater;

public class AliRefundLogDAO extends BaseDAO{
	
	public int getTodayNewBatchNo()
	{
		Date today = new Date(System.currentTimeMillis());
		String todayString = DateFormater.format_batch_no_part(today);
		List<AliRefundLog> logs = query("from AliRefundLog where refund_date = ?", todayString);
		int max = 1;
		for (AliRefundLog log: logs)
			if (log.getRefund_batch_no() > max)
				max = log.getRefund_batch_no();
		return max + 1;
		
	}

}
