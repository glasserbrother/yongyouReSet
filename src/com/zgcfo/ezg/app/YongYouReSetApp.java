package com.zgcfo.ezg.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zgcfo.ezg.app.data.YongYouDataGet;
import com.zgcfo.ezg.app.data.YongYouDo;
import com.zgcfo.ezg.entity.yongyou.YongYouDataEntity;
import com.zgcfo.ezg.service.YongYouDoData;
import com.zgcfo.ezg.util.AppMySQLConn;
import com.zgcfo.ezg.util.MyFormat;

public class YongYouReSetApp {
	
	
	public static void reset(List<String> months, String bookid, int paraCommond){
		YongYouDoData app = new YongYouDoData();
		List<YongYouDataEntity> list = app.getYongYouDataList(bookid);
		if (list ==null || list.size() == 0){
			System.out.println("账号或对应公司数据为空");
		}else{
			System.out.println("list: "+list.size());
			YongYouDataEntity yyData;
			YongYouDataGet yyGet;
			
			
			AppMySQLConn conn = new AppMySQLConn();
			YongYouDo yyDo = new YongYouDo(conn);
			
			for (int i = 0,len = months.size(); (i < len ); i++) {
				try {
					
					// 产生一个任务，并将其加入到线程池
					System.out.println(new Date()+"------------------------执行第"+i+"个");
					yyData = list.get(0);
					yyData.setCurrMonth(months.get(i));
					System.out.println(yyData);
					
					yyGet = new YongYouDataGet(yyData.getLoginName(),yyData.getPwd()
							,yyData.getBookId(),yyData.getYongyouId(), yyData.getCurrMonth());
					
					new YYGetAndDo(yyGet, yyDo, paraCommond).getAndDo();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println(new Date()+"------------------------完成第"+i+"个");
			}
			
			
			System.out.println(new Date() +"全部完成--恭喜");
		}
	}
	
	public static void main(String[] args) {
		String currMonth = null;
		String _month;
		String bookid = null;
		String s_commond = null;
		int i_commond = -1;
		List<String> months = new ArrayList<String>();
		if (args.length > 0 ){
			bookid= args[0];
		}
		if (args.length > 1 ){
			s_commond = args[1];
			if (MyFormat.isStrNull(s_commond) || "noneed".equals(s_commond)){
				
			}else{
				i_commond = MyFormat.formatInt(s_commond);
			}
		}
		if (args.length > 2 ){
			
			String datePattern1 = "\\d{6}";
			Pattern pattern = Pattern.compile(datePattern1);
            Matcher match ;
             
			for (int i = 2,len = args.length; i<len; i++){
				_month = args[i];
				if (MyFormat.isStrNull(_month) || "noneed".equals(_month)){
					System.out.println("月份参数不能为空");
					continue;
				}
				match = pattern.matcher(_month);
				if (!match.matches()){
					System.out.println("月份参数格式不对");
					continue;
				}
				
				months.add(_month);
			}
			if (months.size() > 0){
				currMonth = months.get(0);
			}
			
		}
		
		
		if (MyFormat.isStrNull(bookid) || "noneed".equals(bookid)){
			System.out.println("公司参数不能为空");
			return;
		}
		
		reset(months, bookid, i_commond);
		
		
	}
	
	public static void domain(String[] args) {
		List<String> months = new ArrayList<String>();
		months.add("201612");
		String bookid = "52f2d3fa-ddfd-11e6-8fdc-28e347652f73";
		int i_commond = -1;//4
		reset(months, bookid, i_commond);
	}

}
