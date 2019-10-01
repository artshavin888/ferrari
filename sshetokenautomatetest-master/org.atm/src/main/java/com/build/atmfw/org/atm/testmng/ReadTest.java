package com.build.atmfw.org.atm.testmng;

import java.util.Hashtable;

import com.build.atmfw.org.atm.utilities.TextFileMng;

public class ReadTest {

	public ReadTest() {
		tf=new TextFileMng();
	}
	private TextFileMng tf;
	Hashtable<String, String[]> tc = new Hashtable<String, String[]>();
	@SuppressWarnings("unchecked")
	public void readTestcase(String tcName ) {
		String filetestcase="D:\\test tc\\tc.txt";
		filetestcase=tcName;
		tf.ReadCsv(filetestcase);
		tc=(Hashtable<String, String[]>) tf.GetHTable().clone();
		tf.GetHTable().clear();
	}
	public Hashtable<String, String[]> GetHTTestcase(){
		return tc;
	}
	Hashtable<String, String[]> ts = new Hashtable<String, String[]>();
	@SuppressWarnings("unchecked")
	public void readTestset() {
		String filetestcase="D:\\test tc\\ts.txt";
		tf.ReadCsv(filetestcase);
		ts=(Hashtable<String, String[]>) tf.GetHTable().clone();
		tf.GetHTable().clear();
	}
	public Hashtable<String, String[]> GetHTTestset(){
		return ts;
	}
	Hashtable<String, String[]> dti = new Hashtable<String, String[]>();
	@SuppressWarnings("unchecked")
	public void readIteration(String dtiName) {
		String filetestcase="D:\\test tc\\dti.txt";
		filetestcase=dtiName;
		tf.ReadCsv(filetestcase);
		dti=(Hashtable<String, String[]>) tf.GetHTable().clone();
		tf.GetHTable().clear();
		
	}
	public Hashtable<String, String[]> GetHTDataIteration(){
		return dti;
	}
}
