package com.zgcfo.ezg.app.commond;

import java.util.List;

import com.zgcfo.ezg.app.constant.YongYouConstants;
import com.zgcfo.ezg.app.data.YongYouDo;
import com.zgcfo.ezg.entity.yongyou.Balance;
import com.zgcfo.ezg.entity.yongyou.BalanceReport;
import com.zgcfo.ezg.entity.yongyou.CashReport;
import com.zgcfo.ezg.entity.yongyou.DetailAccountReport;
import com.zgcfo.ezg.entity.yongyou.IncomeQuarterReport;
import com.zgcfo.ezg.entity.yongyou.IncomeReport;
import com.zgcfo.ezg.entity.yongyou.Subject;
import com.zgcfo.ezg.entity.yongyou.TaxReport;
import com.zgcfo.ezg.util.LogUtil;
import com.zgcfo.ezg.util.RedisUtil;

public class YongYouCommondRPOP {
	
	private YongYouDo yyDo;
	private int commond;
	
	
	
	public YongYouDo getYyDo() {
		return yyDo;
	}

	public void setYyDo(YongYouDo yyDo) {
		this.yyDo = yyDo;
	}

	public int getCommond() {
		return commond;
	}

	public void setCommond(int commond) {
		this.commond = commond;
	}

	public YongYouCommondRPOP(YongYouDo yyDo, int commond) {
		super();
		this.yyDo = yyDo;
		this.commond = commond;
	}
	
	public void delCommand(Object obj, int paraCommond) throws Exception {
		switch(paraCommond){
		case YongYouConstants.CASH_REPORT_INT : 
			//开始删除现金流量表
			List<CashReport> cashObjs = (List<CashReport>) obj;
			if (cashObjs!=null && cashObjs.size()>0){
				CashReport cashReport;
				//先删除在插入
				cashReport = cashObjs.get(0);
				yyDo.deleteCashReport(cashReport); 
			}
			
			break;
		case YongYouConstants.INCOME_REPORT_INT : 
			//开始删除利润表
			List<IncomeReport> incomeReports = (List<IncomeReport>) obj;
			if (incomeReports!=null && incomeReports.size() >0 ){
				IncomeReport incomeReport;
			    incomeReport = incomeReports.get(0);
			    yyDo.deleteIncomeReport(incomeReport);
			}
			
			break;
		case YongYouConstants.BALANCE_REPORT_INT : 
			//开始删除资产负债表
			List<BalanceReport> balanceReports = (List<BalanceReport>) obj;
			if (balanceReports!=null && balanceReports.size() >0){
				BalanceReport balancReport;
				balancReport =  balanceReports.get(0);
				yyDo.deleteBalanceReport(balancReport);
			}
			
			break;
		case YongYouConstants.SUBJECT_INT : 
			//开始删除该账户下的所有科目
			List<Subject> subjects = (List<Subject>) obj;
			if (subjects !=null && subjects.size() >0 ){
				Subject sbj;
				sbj =  subjects.get(0);
				yyDo.deleteAllSubject(sbj);
			}
			
			break;
		case YongYouConstants.BALANCE_INT : 
			//开始删除余额
			List<Balance> balances = (List<Balance>) obj;
			if (balances !=null && balances.size() >0){
				Balance balance;
				balance =  balances.get(0);
				yyDo.deleteBalance(balance);
			}
			
			break;
		case YongYouConstants.INCOME_QUARTER_REPORT_INT :
			//开始删除利润季报表
			List<IncomeQuarterReport> incomeQuarters = (List<IncomeQuarterReport>) obj;
			if (incomeQuarters != null &&incomeQuarters.size()> 0){
				IncomeQuarterReport incomeQuarterReport;
				incomeQuarterReport = incomeQuarters.get(0);
				yyDo.deleteIncomeQuarterReport(incomeQuarterReport);
			}
			
			break;
		case YongYouConstants.TAX_REPORT_INT : 
			//开始删除纳税统计表
			List<TaxReport> taxReports = (List<TaxReport>) obj;
			if (taxReports!=null && taxReports.size() > 0){
				TaxReport taxReport;
				taxReport = taxReports.get(0);
				yyDo.deleteTaxReport(taxReport);
			}
			break;
		case YongYouConstants.DETAIL_ACCOUNT_REPORT_INT : 
			//开始删除全部明细账
			List<DetailAccountReport> detailAccounts = (List<DetailAccountReport>) obj;
			if (detailAccounts !=null && detailAccounts.size() > 0){
				DetailAccountReport detailAccount;
				detailAccount = detailAccounts.get(0);
				yyDo.deleteAllDetailAccountReport(detailAccount);
			}
			
			
			break;
		}
	}
	
	public void executeCommond(Object obj, int paraCommond) throws Exception {
		
		if (obj != null) {
			switch(paraCommond){
			case YongYouConstants.CASH_REPORT_INT : 
				//开始导入现金流量表
				List<CashReport> cashObjs = (List<CashReport>) obj;
				if (cashObjs!=null && cashObjs.size()>0){
					yyDo.doCashReport(cashObjs);
				}
				
				break;
			case YongYouConstants.INCOME_REPORT_INT : 
				//开始导入利润表
				List<IncomeReport> incomeReports = (List<IncomeReport>) obj;
				if (incomeReports!=null && incomeReports.size() >0 ){
					yyDo.doIncomeReport(incomeReports);
				}
				
				break;
			case YongYouConstants.BALANCE_REPORT_INT : 
				//开始导入资产负债表
				List<BalanceReport> balanceReports = (List<BalanceReport>) obj;
				if (balanceReports!=null && balanceReports.size() >0){
					yyDo.doBalanceReport(balanceReports);
				}
				
				break;
			case YongYouConstants.SUBJECT_INT : 
				//开始导入科目
				List<Subject> subjects = (List<Subject>) obj;
				if (subjects !=null && subjects.size() >0 ){
					yyDo.doSubject(subjects);
				}
				
				break;
			case YongYouConstants.BALANCE_INT : 
				//开始导入余额
				List<Balance> balances = (List<Balance>) obj;
				if (balances !=null && balances.size() >0){
					yyDo.doBalance(balances);
				}
				
				break;
			case YongYouConstants.INCOME_QUARTER_REPORT_INT :
				//开始导入利润季报表
				List<IncomeQuarterReport> incomeQuarters = (List<IncomeQuarterReport>) obj;
				if (incomeQuarters != null &&incomeQuarters.size()> 0){
					yyDo.doIncomeQuarterReport(incomeQuarters);
				}
				
				break;
			case YongYouConstants.TAX_REPORT_INT : 
				//开始导入纳税统计表
				List<TaxReport> taxReports = (List<TaxReport>) obj;
				yyDo.doTaxReport(taxReports);
				break;
			case YongYouConstants.DETAIL_ACCOUNT_REPORT_INT : 
				//开始导入明细账
				List<DetailAccountReport> detailAccounts = (List<DetailAccountReport>) obj;
				yyDo.doDetailAccountReport(detailAccounts);
				
				
				break;
			}
			
		}
		
	}
	
	public String logStartCommond(Object obj, int paraCommond, String period, String accountBookId) throws Exception {
		String id = null;
		if (obj != null) {
			
			byte [] b = YongYouConstants.YYMAP.get(new Integer(paraCommond));
			if (b != null){
				id = LogUtil.logStart(new String(b), period, accountBookId);
			}
			
			
		}
		return id;
		
	}
	
	public void logEndCommond(String id){
		LogUtil.logEnd(id);
	}
	public String logStartCommond(Object obj, String period, String accountBookId){
		String id = null;
		try {
			id = logStartCommond(obj, commond, period, accountBookId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	
	public void deleteCommond(Object obj){
		deleteCommond(commond,obj);
	}
	
	public void deleteCommond(int paraCommond, Object obj){

		
		try {
			delCommand(obj, paraCommond);
		} catch (Exception e7) {
			e7.printStackTrace();
		}
		
		
	}
	
	public void executeCommond(Object obj){
		try {
			executeCommond(obj, commond);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doCommond(Object obj, String period, String accountBookId){

		
		try {

			String id = logStartCommond(obj, period, accountBookId);
			deleteCommond(obj);
			executeCommond(obj);
			logEndCommond(id);
		} catch (Exception e7) {
			e7.printStackTrace();
		}
		
		
	}

}
