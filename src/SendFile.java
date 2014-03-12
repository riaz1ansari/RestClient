import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class SendFile implements Serializable{
	
	private String url;
	private String body;
	private Map<String,String> headerMap=new HashMap<String, String>();
	private String method;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Map<String, String> getHeaderMap() {
		
		return headerMap;
	}
	
	public void addHeader(String key,String value) {
		headerMap.put(key, value);
	}
	
	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap=headerMap;
	}
	
	public void saveToFile(String filePath) throws Exception{
		if(!"sfxml".equals(filePath.substring(filePath.lastIndexOf(".")+1))){
			filePath+=".sfxml";
		}
		File file=new File(filePath);
		
		FileWriter fileWriter=new FileWriter(file,true);
		
		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
		
//		fileWriter.append("<SEND_FILE><PATH>");
//		fileWriter.append(this.url);
//		fileWriter.append("</PATH><METHOD>");
//		fileWriter.append(this.method);
//		fileWriter.append("</METHOD><BODY>");
//		fileWriter.append(this.body);
//		fileWriter.append("</BODY><HEADERS>");
//		Set<String> keySet=this.headerMap.keySet();
//		
//		for (String key : keySet) {
//			fileWriter.append("<HEADER><NAME>");
//			fileWriter.append(key);
//			fileWriter.append("</NAME><VALUE>");
//			fileWriter.append(headerMap.get(key));
//			fileWriter.append("</VALUE></HEADER>");
//		}
//		
//		fileWriter.append("</HEADERS>");
//		fileWriter.append("</SEND_FILE>");
//	
//		fileWriter.flush();
//		fileWriter.close();
		
		out.writeObject(this);
		out.close();
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	

}
