package client;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mail {
	private String date;
	private String sender;
	private String receiver;
	private String subject;
	private String content;	

	public Mail() {
		// TODO Auto-generated constructor stub
	}
	public Mail(String sender, String receiver, String subject, String content) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date dateNow = new Date();
		this.date = sdf.format(dateNow);
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	@Override
	public String toString() {
		return "Mail [date=" + date + ", sender=" + sender + ", receiver=" + receiver + ", subject=" + subject
				+ ", content=" + content + "]";
	}
	public static void main(String[] args) {
/*		Mail mail = new Mail("10", "phuc", "yue", "test", "abc");
		String email = mail.code();
		System.out.println(mail.decode(email).toString());*/
	}
}
