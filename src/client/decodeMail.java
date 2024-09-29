package client;

import org.json.JSONObject;


public class decodeMail {
	public static String encode(Mail mail) {
        JSONObject json = new JSONObject();
        json.put(Key.DATE.name(), mail.getDate());
        json.put(Key.SENDER.name(), mail.getSender());
        json.put(Key.RECEIVER.name(), mail.getReceiver());
        json.put(Key.SUBJECT.name(), mail.getSubject());
        json.put(Key.CONTENT.name(), mail.getContent());
        return json.toString();
	}
	public static Mail decode(String json) {
		JSONObject jsonObject = new JSONObject(json);
		Mail mail = new Mail();
		mail.setDate(jsonObject.getString(Key.DATE.name()));
		mail.setSender(jsonObject.getString(Key.SENDER.name()));
		mail.setReceiver(jsonObject.getString(Key.RECEIVER.name()));
		mail.setSubject(jsonObject.getString(Key.SUBJECT.name()));
		mail.setContent(jsonObject.getString(Key.CONTENT.name()));
		return mail;				
	}
	public enum Key{
		DATE,
		SENDER,
		RECEIVER,
		SUBJECT,
		CONTENT		
	}
}
