package com.refactorlabs.cs378;

import java.util.*;

public class MRDPUtils {

   	// This helper function parses the email into a Map for us.
	public static HashMap<String, String> transformEmailToMap(String email) { 
		/**This needs to parse these fields from the string(email) 
		*	Message-ID:
		*	Date:
		*	From:
		*	To:
		*	Subject:
		*	Cc:  [optional]
		*	Mime-Version:
		*	Content-Type:
		*	Content-Transfer-Encoding
		*	Bcc:   [optional]
		*
		*/

		HashMap<String, String> map = new HashMap<String, String>();
		int i;
		String tmp;

			//All values seperated by tab
			StringTokenizer st = new StringTokenizer(email, "\t");

			while(st.hasMoreTokens()){
				tmp = st.nextToken();
				i = tmp.indexOf(": ");
				if(i < 0){
					System.out.println(email);
					System.out.println("DOESNT HAVE AN I: "+tmp);
					break;
				}
				if(tmp.substring(0, i).compareTo("X-From") == 0){
					break;
				}
				map.put(tmp.substring(0, i).trim(), tmp.substring(i+1, tmp.length()).trim());
			}
		
		return map;
	}
	
}