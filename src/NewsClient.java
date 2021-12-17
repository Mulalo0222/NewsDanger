import java.time.LocalDate;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets; 
/**
 * 
 * @author Mulalo Ralinala
 *
 */

public class NewsClient {

	public static ArrayList<NewsHeader> ConnectClient(String Seach) throws IOException
	{
		ArrayList<NewsHeader> NewsResults = new ArrayList<NewsHeader>();
		String Days = LocalDate.now().minusDays(15).toString();
		String URL = "https://newsapi.org/v2/everything?q="+Seach+"&pageSize=100&from="+Days+"&sortBy=publishedAt&apiKey=78c494b34c5c40469b7a5b624a3a375e";
		  URL obj = new URL(URL);
		     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		  
		     
				int responseCode = con.getResponseCode();
			
		    
		     BufferedReader in = new BufferedReader(
		             new InputStreamReader(con.getInputStream()));
		     String inputLine;
		     String json = "";
		     while ((inputLine = in.readLine()) != null) {
		     	json += inputLine;
		     }
		     in.close();
		  //   System.out.println(json);
		     InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		  JsonReader e = Json.createReader(stream);
		  JsonObject finalObj = e.readObject();
		 // System.out.println(finalObj.getString("status"));
		 System.out.println(finalObj.getInt("totalResults"));
		 
		 JsonArray arrayJ = finalObj.getJsonArray("articles");
		 for(int a = 0 ; a < arrayJ.size();a++)
		 {
			 JsonObject jobj = arrayJ .getJsonObject(a);
			 String author = (jobj.isNull("author")) ? "":jobj.getString("author");
			 String title =(jobj.isNull("title")) ? "":jobj.getString("title");
			 String description = (jobj.isNull("description")) ? "":jobj.getString("description");
			 String imageurl = (jobj.isNull("urlToImage")) ? "":jobj.getString("urlToImage");
			 NewsResults.add(new NewsHeader(author,title,description,imageurl));
		 }
		
		//  System.out.println();
		 // System.out.println(arrayJ.getJsonObject(1).getJsonString("title"));
		
	 return NewsResults;	
	}
}
