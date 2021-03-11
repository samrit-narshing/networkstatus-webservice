/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Samrit
 */
public class MailUtil {

    public void sendEmail(List<String> receiverEmails, String subject, String message) {

        try {
            // Check emails must be aleast one
            if (receiverEmails.size() >= 0) {
                Properties mailServerProperties;
                Session getMailSession;
                MimeMessage generateMailMessage;
                // Step1
                System.out.println("\n 1st ===> setup Mail Server Properties..");
                mailServerProperties = System.getProperties();
                mailServerProperties.put("mail.smtp.port", "587");
                mailServerProperties.put("mail.smtp.auth", "true");
                mailServerProperties.put("mail.smtp.starttls.enable", "true");
                System.out.println("Mail Server Properties have been setup successfully..");

                // Step2
                System.out.println("\n\n 2nd ===> get Mail Session..");
                getMailSession = Session.getDefaultInstance(mailServerProperties, null);
                generateMailMessage = new MimeMessage(getMailSession);
                generateMailMessage.setFrom("seemsserver.siamsecure@gmail.com");
                for (String email : receiverEmails) {
                    generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                }
                // generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
                generateMailMessage.setSubject(subject);
                String emailBody = message + "<br><br> Regards, <br>SEEMS WEB SERVER";
                generateMailMessage.setContent(emailBody, "text/html");
                System.out.println("Mail Session has been created successfully..");

                // Step3
                System.out.println("\n\n 3rd ===> Get Session and Send mail");
                Transport transport = getMailSession.getTransport("smtp");

                // Enter your correct gmail UserID and Password
                // if you have 2FA enabled then provide App Specific Password
                transport.connect("smtp.gmail.com", "seemsserver.siamsecure@gmail.com", "@sscp@ssw0rd");
                transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
                transport.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log4jUtil.fatal(e);
        }
    }

    public static void main(String[] args) {
        List<String> emails = new ArrayList<>();
        emails.add("samrit_narshing@hotmail.com");
        new MailUtil().sendEmail(emails, "Test Sun", "Helllooo");
    }

}
