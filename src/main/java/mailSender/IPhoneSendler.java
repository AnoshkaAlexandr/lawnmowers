package mailSender;

public interface IPhoneSendler {

	void send(String subject, String text, String fromEmail, String toEmail);
	
}
