package com.build.atmfw.org.atm.testmng;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.build.atmfw.org.atm.utilities.TimeUtil;

public class TestResult {
private String fileName,pathName,fullfileName;
private String resultHtml="";
	public TestResult() {
		pathName="D:\\logHTML\\";
		fileName=	"Result of testing "+TimeUtil.GetNameDateTime();
		fullfileName=pathName+fileName+".html";
//		System.out.println(fullfileName);
		resultHtml+=ResultHeader();
	}
	
	public void testclass() {
		System.out.println(fullfileName);
	}
	

	private String ResultHeader(){
		String sheader="<!DOCTYPE html><html><head><title>Test Result : "+TimeUtil.GetNameDateTime() +"</title><meta charset=\\\"utf-8\\\">"
				+ "<style>table, th, td {border: 1px solid black;}"
				+"#para1 {  text-align: center;  background-color: #ff2222;color: white;}"
				+"#para2 {  text-align: center;  color: blue;}"
				+"th{  background-color: #006666;  color: white;}"
				+"tr:hover {background-color:#d6d6d6;}"
				+ "</style>"
				+ "</head>"
				+ "<body><div style=\"overflow-x:auto;\"><table><tr><th>Step</th><th>Time</th><th>Test Description</th><th>Action</th><th>Result</th></tr>";
	return sheader;
	}
	
	public String WriteResult(String timestmp,String step,String description,String action,String result) {
		String txt="";
		if (step.equals("xx")||step.equals("XX")||step.equals("-")) {
		}else {
			if (result.equals("Passed")) {
				txt="<tr id='para2'>";
			}else if(result.equals("Failed")){
				txt="<tr id='para1'>";
			}else {
				txt="<tr>";
			}
			txt+="<td>"+step +"</td><td>"+TimeUtil.GetDateTime()+"</td><td>" + description +"</td><td>" +action+"</td><td>"+ result + "</td></tr>";
//			System.out.println(TimeUtil.GetDateTime()+"    "+step+" "+description+"   "+action +"   "+result);
//			System.out.println(txt);
		}
		resultHtml+=txt;
		return txt;
	}
	public String GetresultHtml() {
		return resultHtml;
	}

	public void AppendFile(String text){
		Writer output;
		
		try {
			output = new BufferedWriter(new FileWriter(fullfileName,true));  //clears file every time
			output.append(text);
			output.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public void WriteFile(String text) {
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
//				    new FileOutputStream(fullfileName), "UTF-8"));
					new FileOutputStream(fullfileName), "TIS-620"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			    try {
					out.write(text);
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    


	}
}
