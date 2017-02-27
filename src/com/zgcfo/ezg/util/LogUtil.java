package com.zgcfo.ezg.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class LogUtil {
	
	private static AppMySQLConn conn;
	private static PreparedStatement ps;
	private static PreparedStatement psDetail;
	private static PreparedStatement psDelete;
	private static PreparedStatement psLogStart;
	private static PreparedStatement psLogEnd;
	
	static {
		init();
	}
	
	
	public static void init(){
		System.out.println("init LogUtil.......");
		conn = new AppMySQLConn();
		conn.getConnection();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into yzg_yy_datereset(id,tableType,accountantLoginName,accountantPwd,accountBookId,yongyouBookId,period,errMsg,status) values(uuid(),?,?,?,?,?,?,?,?) ");
		ps = conn.prepareStatement(sql);
		
		sql = new StringBuilder();
		sql.append(" insert into yzg_yy_datereset(id,tableType,accountantLoginName,accountantPwd,accountBookId,yongyouBookId,period,subjectId,errMsg,status) values(uuid(),?,?,?,?,?,?,?,?,?) ");
		psDetail = conn.prepareStatement(sql);
		
		sql = new StringBuilder();
		sql.append(" delete from yzg_yy_datereset where status = ? and period = ? and  accountBookId = ? ");
		psDelete = conn.prepareStatement(sql);
		
		sql = new StringBuilder();
		sql.append(" insert into  yzg_yy_datereset(id,tableType,period, accountBookId, status) values(?,?,?,?,?) ");
		psLogStart = conn.prepareStatement(sql);

		sql = new StringBuilder();
		sql.append(" update yzg_yy_datereset set status = '1' where id = ? ");
		psLogEnd = conn.prepareStatement(sql);
		
		
	}
	
	public static String logStart(String tableType, String period, String accountBookId){
		String id = UUID.randomUUID().toString();
		try {
			psLogStart.setString(1, id);
			psLogStart.setString(2, tableType);
			psLogStart.setString(3, period);
			psLogStart.setString(4, accountBookId);
			psLogStart.setString(5, "2");//1-正常的日志;2-数据校验用个日志
			psLogStart.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static void logEnd(String id){
		if (MyFormat.isStrNull(id)){
			return;
		}
		try {
			
			psLogEnd.setString(1, id);
			psLogEnd.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void logDelete(String accountBookId,  String period){
		try {
			psDelete.setString(1, "2");//1-正常的日志;2-数据校验用个日志
			psDelete.setString(2, period);
			psDelete.setString(3, accountBookId);
			psDelete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void logErr(String tableType, String accountantLoginName, String accountantPwd, String accountBookId, int yongyouBookId, String period, String errMsg){
		try {
			ps.setString(1, tableType);
			ps.setString(2, accountantLoginName);
			ps.setString(3, accountantPwd);
			ps.setString(4, accountBookId);
			ps.setInt(5, yongyouBookId);
			ps.setString(6, period);
			ps.setString(7, errMsg);
			ps.setString(8, "3");//1-正常的日志;2-数据校验用个日志;3-没有或异常数据
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void logErr(String tableType, String accountantLoginName, String accountantPwd, String accountBookId, int yongyouBookId, String period, String subjectId, String errMsg){
		try {
			psDetail.setString(1, tableType);
			psDetail.setString(2, accountantLoginName);
			psDetail.setString(3, accountantPwd);
			psDetail.setString(4, accountBookId);
			psDetail.setInt(5, yongyouBookId);
			psDetail.setString(6, period);
			psDetail.setString(7, subjectId);
			psDetail.setString(8, errMsg);
			psDetail.setString(9, "3");//1-正常的日志;2-数据校验用个日志;3-没有或异常数据
			psDetail.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		//LogUtil.logErr("CashReport", 100015, "201611", "balabala");
	}
	
	

}
