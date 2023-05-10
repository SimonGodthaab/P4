package simple.test12345.Util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailHandler {
    public static void send(String otpToken, String mailTo) {
        System.setProperty("mail.default.address.map", "dummy");


        final String username = "P4OTPAUTH@outlook.com";
        final String password = "Hejmeddig!";


        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.office365.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "587");


        prop.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("P4OTPAUTH@outlook.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mailTo)
            );
            message.setSubject("OTP Code");
            message.setText("Here is your One Time Password:\n" + otpToken);

            Transport.send(message);

            System.out.println("OTP CODE SENT");

        } catch (MessagingException exception) {
            exception.printStackTrace();
            System.out.println(exception.getMessage());


        }
    }

}
