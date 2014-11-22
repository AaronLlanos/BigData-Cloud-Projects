import java.util.*;
import java.io.*;

public class InvertedIndex{

	public static void pairs(String line) { 

		//Split the log entry into individual values
		HashMap<String, String> map = new HashMap<String, String>();
		String arr[] = line.split("\\|\t\\|");
		String temp[];
		boolean first = true;
		for (String x: arr) {
			temp = x.split(":");
			if(first){
				temp[0] = temp[0].split("\\t")[1];
				first = false;
			}

			//Error check the values
			if(temp.length == 2)
				map.put(temp[0], temp[1]);
			else if(temp.length >= 2){
				String value = "";
				for (int i = 1; i < temp.length; i++) {
					value += temp[i];
				}
				map.put(temp[0], value);
			}
			else
				map.put(temp[0], null);
		}
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("Key:\t\""+entry.getKey()+"\"\tValue:\t"+entry.getValue());
		}

	}

	public static void main(String[] args) {

		String file1 = "L	lead_id:3244766618990811151|	|id:10614729|	|pos:538|	|log_file:tcleads2/leads/2014/09/30/leads.log|	|record_pos:0|	|type:good|	|time:2014-09-30 09:52:26|	|apikey:74151314133126130439110311215271|	|bidtype:lead|	|bidder_id:ShiftDigital|	|campaign_id:201409|	|source:http://shiftdigital.com|	|url:|	|gateway:shiftdigital|	|advertiser:shiftdigital.com|	|domain:shiftdigital.com|	|vertical:cars|	|category:cars|	|channel_id:www.google.com|	|userid:2efa713e280ce1ca6a81e2139891d251|	|tabversion:0|	|recordid:3400563819749399538|	|revenue:8.19|	|vst_cid:|	|external_id:16479535|	|enc_pii:|	|key_ver:0|	|active:1|	|lead_hash:1273689354970548983|	|lead_amount:9.0|	|test:false|	|response_code:200|	|response_desc:OK|	|lead_type:thankyou|	|ab:{\"abv\":\"1.26\",\"app\":\"nodejs\",\"caravana\":\"default\",\"dayslistedsort\":\"dayslisted\",\"dealerdistance\":\"dealerdistance\",\"derivedattributesdrill\":\"derivedlistdrill\",\"discountoncert\":\"default\",\"monthlypaymentsearch\":\"monthpayrateandterms\",\"newlylisted\":\"newlylisted\",\"reducedprice\":\"default\",\"ringrevenue\":\"default\",\"savedsearch\":\"default\",\"thankyousimilars\":\"default\",\"updatedleadform\":\"vdpleadform\",\"v\":\"v1\"}|	|processed_by:partner|	|cached:false|	|customer_zip:59601|	|franchise_id:|	|make:Cadillac|	|model:DTS|	|body_style:Sedan|	|trim:|	|vin:1G6KD57YX6U174670|	|dealer_name:Butte's Milehigh Chrysler Jeep Dodge|	|dealer_id:45444|	|zip:59701|	|year:2006|	|vehicle_condition:used|	|interior_color:|	|exterior_color:Silver|	|kbb_type:vast|	|dma_code:754|	|transactiontypeid:|	|sicookieid:|	|ppcconversionid:|	|price:0|	|mileage:|	|franchise_dealer_type:";		
		//pairs(file1);
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {
			System.out.println(rand.nextDouble());
		}

	}
}