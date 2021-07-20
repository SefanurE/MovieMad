package moviemad.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import io.javalin.http.Context;

public class EmailUtil {
    private static final String SENDER_EMAIL = "seftestacc@gmail.com";
    private static final String SENDER_PASSWORD = "seftestacc1234";
    private static final int SENDER_PORT = 465;


    /**
     * Sends an email to the email specified in the receivingAddress, with a message and subject using gmail.
     */
    public static void emailUser(String message, String subject, String receivingAddress) throws EmailException {

        // Set up code from https://javalin.io/tutorials/email-sending-example
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(SENDER_PORT);
        email.setAuthenticator(new DefaultAuthenticator(SENDER_EMAIL, SENDER_PASSWORD));
        email.setSSLOnConnect(true);
        email.setFrom(SENDER_EMAIL);
        email.setSubject(subject); // subject from HTML-form
        email.setMsg(message); // message from HTML-form
        email.addTo(receivingAddress);
        email.send(); // will throw email-exception if something is wrong

    }
}
