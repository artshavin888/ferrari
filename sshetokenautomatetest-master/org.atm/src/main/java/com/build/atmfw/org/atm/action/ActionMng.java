package com.build.atmfw.org.atm.action;

import com.build.atmfw.org.atm.device.SetDevice;
import com.build.atmfw.org.atm.testmng.ReadTest;
import com.build.atmfw.org.atm.testmng.TestResult;
import com.build.atmfw.org.atm.utilities.TimeUtil;

public class ActionMng {
	private ReadTest rt ;
	private SetDevice dv;
//	private Hashtable<String, String> objectDynamic;
	private String step,description,action,objecttype,object,data,expect;
	MainAction ma;
	public ActionMng() {
		 dv=new SetDevice();
		 rt =new ReadTest();
		 dv.setchromeDv();
		ma =new MainAction();
		 rt.readTestset();
		 
		 for (int its = 2; its <= rt.GetHTTestset().size(); its++) {
			 //test set
//			 System.out.println(rt.GetHTTestset().get("HT"+its)[2]);
			 System.out.println(its);
			 rt.readIteration(rt.GetHTTestset().get("HT"+its)[3]);
			 for (int idti = 2; idti <= rt.GetHTDataIteration().size(); idti++) {
				 rt.readTestcase(rt.GetHTTestset().get("HT"+its)[2]);
				 TestResult tr=new TestResult();
				 tr.testclass();
				 for (int itc = 2; itc <= rt.GetHTTestcase().size(); itc++) {
					 
						step=rt.GetHTTestcase().get("HT"+itc)[0];
						description=rt.GetHTTestcase().get("HT"+itc)[1];
						action =rt.GetHTTestcase().get("HT"+itc)[2];
						objecttype=rt.GetHTTestcase().get("HT"+itc)[3];
						object=rt.GetHTTestcase().get("HT"+itc)[4];
						data=rt.GetHTTestcase().get("HT"+itc)[5];
						expect=rt.GetHTTestcase().get("HT"+itc)[6];
//					 Run test case
					 CheckIteration(rt.GetHTTestcase().get("HT"+itc),rt.GetHTDataIteration().get("HT"+idti));
					 ConvertChar(rt.GetHTTestcase().get("HT"+itc),rt.GetHTDataIteration().get("HT"+idti));
					 try {
						 ma.CallAction(dv.getChromeDiver(),step,description,action,objecttype,object,data,expect);
//						 if (ma.GetResult().equals("Failed")) {
//							ma.CaptureElementImg(objecttype, object);
//							tr.WriteResult(TimeUtil.GetDateTime(), step, description, action, ma.GetResult());
//						}else {
							tr.WriteResult(TimeUtil.GetDateTime(), step, description, action, ma.GetResult());
//						}
						 
					} catch (Exception e) {
						// TODO: handle exception
					}
					
//					 System.out.println(TimeUtil.GetDateTime()+"    "+step+" "+description+"   "+action +"   "+ma.GetResult());
				 }
				 System.out.println(tr.GetresultHtml());
				 tr.WriteFile(tr.GetresultHtml());
				 
				 System.out.println("===================================");
			}
			 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

			 
//			 rt.readIteration(rt.GetHTTestset().get("HT"+its)[3]);

			 
		 }

	}
	private void CheckIteration(String[] tc,String[] dti) {
		String dt,exp;
		if (tc[5].contains("dti")) {
			System.out.println(tc[5]);
			dt=dti[Integer.parseInt(tc[5].replaceAll("dti", ""))];
			data=dt;
		}
		if (tc[6].contains("dti")) {
			exp=dti[Integer.parseInt(tc[6].replaceAll("dti", ""))];
			expect =exp;

		}
	}
	private void ConvertChar(String[] tc,String[] dti) {
		String dt,exp;
		if (tc[5].contains("|=>Comma|")) {
			System.out.println(tc[5]);
			dt=dti[Integer.parseInt(tc[5].replaceAll("|>Comma", ","))];
			data=dt;
		}
		if (tc[6].contains("|=>Comma|")) {
			exp=dti[Integer.parseInt(tc[6].replaceAll("|>Comma", ","))];
			expect =exp;

		}
		
		if (tc[5].contains("|=>assign|")) {
			data=ma.SetValue(tc[5].replace("|=>assign|", ""));
			
		}
		if (tc[6].contains("|=>assign|")) {
			data=ma.SetValue(tc[6].replace("|=>assign|", ""));
		}
		if (tc[5].contains("|=>get|")) {
			data=ma.GetValue(tc[5].replace("|=>get|", ""));
		}
		if (tc[6].contains("|=>get|")) {
			data=ma.GetValue(tc[6].replace("|=>get|", ""));
		}
	}
	

}