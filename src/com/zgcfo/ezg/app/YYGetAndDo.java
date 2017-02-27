package com.zgcfo.ezg.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zgcfo.ezg.app.commond.YongYouCommondGet;
import com.zgcfo.ezg.app.commond.YongYouCommondRPOP;
import com.zgcfo.ezg.app.constant.YongYouConstants;
import com.zgcfo.ezg.app.data.YongYouDataGet;
import com.zgcfo.ezg.app.data.YongYouDo;
import com.zgcfo.ezg.entity.yongyou.DetailAccountReport;
import com.zgcfo.ezg.entity.yongyou.Subject;
import com.zgcfo.ezg.entity.yongyou.YongYouDataEntity;
import com.zgcfo.ezg.util.RedisUtil;

public class YYGetAndDo  {

	private static final long serialVersionUID = 0;
	private static int consumeTaskSleepTime = 7*2000;
	private static int singleTaskSleepTime = 1000;
	private static int arrayTaskSleepTime = 200;
	private YongYouDataGet yyGet;
	private YongYouDo yyDo;
	private int commond;

	public YYGetAndDo(YongYouDataGet yyGet, YongYouDo yyDo, int commond) {
		super();
		this.yyGet = yyGet;
		this.yyDo = yyDo;
		this.commond = commond;
	}
	
	public void loadArrayData(YongYouCommondGet yyCommondGet, YongYouCommondRPOP yyCommondPROP, List<Object> listObjs){
		
		byte[] redisKey;
		Object detailList;
		int index = 0;
		try {
			
			if (listObjs !=null && listObjs.size() > 0){
				Subject sbj;
				Integer yongyouId;
				String id=null;
				for (Object obj : listObjs){
					
					sbj = (Subject) obj;
					yongyouId =sbj.getYongyouId();
					if (yongyouId != null){
						detailList = yyCommondGet.getSpecialAndGet(yongyouId.toString());
						redisKey = RedisUtil.getCommondRedisKey(YongYouConstants.DETAIL_ACCOUNT_REPORT_INT);
						if (detailList !=null){
							yyCommondPROP.setCommond(YongYouConstants.DETAIL_ACCOUNT_REPORT_INT);
							
							if (index == 0 ){
								id = yyCommondPROP.logStartCommond(detailList, yyCommondGet.getYyGet().getPeriod(), yyCommondGet.getYyGet().getAccountBookId());
								yyCommondPROP.deleteCommond(detailList);
							}
							yyCommondPROP.executeCommond(detailList);
							index++;
						}
						System.out.println(new Date()+"  get-----  ["+new String(redisKey)+"]  "+Thread.currentThread().getName()+"执行完成");
						
						Thread.sleep(arrayTaskSleepTime);
					}
					
				}
				
				yyCommondPROP.logEndCommond(id);
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadSingleData(YongYouCommondGet yyCommondGet, YongYouCommondRPOP yyCommondPROP){
		
		
		Object obj = null;
		byte[] redisKey;
		
		try {
			//获取数据
			obj = yyCommondGet.getCommondAndGetList();
			if (obj != null){
				
				
				redisKey = yyCommondGet.getCommondRedisKey();
				if (yyCommondGet.getCommond() == YongYouConstants.SUBJECT_INT){
					//处理获取的数据
					yyCommondPROP.doCommond(obj,yyCommondGet.getYyGet().getPeriod(), yyCommondGet.getYyGet().getAccountBookId());
					
					List<Object> list = (List<Object>) obj;
					loadArrayData(yyCommondGet, yyCommondPROP, list);
					
				}else{
					//处理获取的数据
					yyCommondPROP.doCommond(obj, yyCommondGet.getYyGet().getPeriod(), yyCommondGet.getYyGet().getAccountBookId());
				}
				
				System.out.println(new Date()+"  get-----  ["+new String(redisKey)+"]  "+Thread.currentThread().getName()+"执行完成");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void threadLoad(final int paraCommond) throws Exception{
		new Thread(new Runnable(){
			YongYouCommondGet yyCommondGet = new YongYouCommondGet(yyGet, paraCommond);	
			YongYouCommondRPOP yyCommondPROP = new YongYouCommondRPOP(yyDo, paraCommond);
						public void run(){
							loadSingleData(yyCommondGet, yyCommondPROP);
						}
					})
		.start(); 
		Thread.sleep(singleTaskSleepTime);
	}
	
	public void getAndDo() throws Exception{
		
			if (this.commond > -1){
				threadLoad(this.commond);
			}else{
				for (int i = 0,len = 6;i<=len; i++ ){
					threadLoad(i);
				}
			}
			
	}
}

