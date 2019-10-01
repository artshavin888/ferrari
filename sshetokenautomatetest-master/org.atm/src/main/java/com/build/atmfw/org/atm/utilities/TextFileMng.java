package com.build.atmfw.org.atm.utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class TextFileMng {

	public TextFileMng() {
		// TODO Auto-generated constructor stub
	}

	Hashtable<String, String[]> htable = new Hashtable<String, String[]>();
	private String fileName="D:\\Test_Tools\\testrun\\test.csv";
//	private String fileName="D:\\test tc\\tc.txt";
	public void ReadCsv(String filename) {
		try {
			if (filename.equals(null)) {
				filename=fileName;
			}
//			BufferedReader reader = new BufferedReader(new FileReader(filename));
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));
			
        int l=0;
		while (true) {
            String line;
				line = reader.readLine();
            if (line == null) {
                break;
            }
            String[] parts = line.split(",");
            l+=1;
            String hName;
            if (l==1) {
            	 hName="HD";
			}else
			{
				hName="HT"+l;
			}
            
            htable.put(hName, parts);

        }


//		System.out.println(htable.get("HD")[0]+" : "+htable.get("HD")[1]+" : "+htable.get("HD")[2]);

        reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.gc ();
		System.runFinalization ();
	}
	
//	Hashtable<String, String[]> httofile = new Hashtable<String, String[]>();
	public Hashtable<String, String[]> GetHTable() {
		return htable;
	}
	public void ClearHashtable() {
		htable.clear();
	}
}
