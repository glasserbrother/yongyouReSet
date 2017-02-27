package com.zgcfo.ezg.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zgcfo.ezg.entity.yongyou.YongYouDataEntity;
import com.zgcfo.ezg.util.AppMySQLConn;
import com.zgcfo.ezg.util.MyFormat;

public class YongYouDoData {
	
	public List<YongYouDataEntity> getYongYouDataList(){
		return getYongYouDataList(null, null,null);
	}
	
	public List<YongYouDataEntity> getYongYouDataList(String bookid){
		return getYongYouDataList(null, bookid,null);
	}
	
	public List<YongYouDataEntity> getYongYouDataList(String accountcode, String bookid, String yongyouid){
		List<YongYouDataEntity> datas = new ArrayList<YongYouDataEntity>();
		AppMySQLConn con = new AppMySQLConn();
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.accountcode, a.accountpwd,b.bookid, b.yongyouid from yzg_accountbook b, yzg_accounter a where b.status = '2' and b.accounter = a.accounterid  ");
		if (!MyFormat.isStrNull(accountcode) && !"noneed".equals(accountcode)){
			sql.append(" and a.accountcode = ? ");
		}
		if (!MyFormat.isStrNull(bookid) && !"noneed".equals(bookid)){
			sql.append(" and b.bookid = ? ");
		}
		if (!MyFormat.isStrNull(yongyouid) && !"noneed".equals(yongyouid)){
			sql.append(" and b.yongyouid = ? ");
		}
		
		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		
		ResultSet rs;
		try {
			if (!MyFormat.isStrNull(accountcode) && !"noneed".equals(accountcode)){
				ps.setString(index++, accountcode);
			}
			if (!MyFormat.isStrNull(bookid) && !"noneed".equals(bookid)){
				ps.setString(index++, bookid);
			}
			if (!MyFormat.isStrNull(yongyouid) && !"noneed".equals(yongyouid)){
				ps.setString(index++, yongyouid);
			}
			rs = ps.executeQuery();
			String loginName;
			String pwd;
			String bookId;
			int yongyouId;
			String currMonth;
			
			YongYouDataEntity yyData;
			while(rs.next()){
				loginName = rs.getString("accountcode");
				pwd = rs.getString("accountpwd");
				bookId = rs.getString("bookid");
				yongyouId = rs.getInt("yongyouId");
				yyData = new YongYouDataEntity();
				yyData.setLoginName(loginName);
				yyData.setPwd(pwd);
				yyData.setBookId(bookId);
				yyData.setYongyouId(yongyouId);
				
				datas.add(yyData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return datas;
	}
	
	public boolean getYongYouData(YongYouDataEntity yyData){
		
		
		
		return false;
	}
	
	
	public boolean doYongYouData(YongYouDataEntity yyData, AppMySQLConn conn){
		boolean flag = false;
		if (null != yyData){
			String loginName = yyData.getLoginName();
			String pwd = yyData.getPwd();
			String bookId = yyData.getBookId();
			int yongyouId = yyData.getYongyouId();
			String currMonth = yyData.getCurrMonth();
			
			try{
				YongyouDataUtil yongyouDataUtil = new YongyouDataUtil(loginName, pwd, conn);
				yongyouDataUtil.beginInsert(bookId, yongyouId, currMonth);
				flag= true;
			}catch(Exception e){
				e.printStackTrace();
				flag = false;
			}
			
		}

		
		return flag;
	}
}
