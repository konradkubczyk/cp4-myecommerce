package com.kubczyk.myecommerce.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.kubczyk.myecommerce.ConfigReader;

@Service
public class EmailServiceImpl implements EmailService {

    JavaMailSender javaMailSender = createJavaMailSender();

    ConfigReader configReader;

    public void sendSimpleMail(EmailDetails details) {

        ConfigReader configReader = createConfigReader();

        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(configReader.getConfig("EMAIL_USERNAME"));
            simpleMailMessage.setTo(details.getRecipient());
            simpleMailMessage.setText(details.getContent());
            simpleMailMessage.setSubject(details.getSubject());

            javaMailSender.send(simpleMailMessage);
            System.out.println(
                    "Email with subject \"" + details.getSubject() + "\" has been sent to " + details.getRecipient());
        }

        catch (Exception exception) {
            System.out.println(
                    "An error occurred while attempting to send an email with subject " + details.getSubject() + " to "
                            + details.getRecipient() + " : " + exception.getMessage());
        }
    }

    private JavaMailSender createJavaMailSender() {

        try {

            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

            ConfigReader configReader = createConfigReader();

            javaMailSender.setHost(configReader.getConfig("EMAIL_HOST"));
            javaMailSender.setPort(Integer.parseInt(configReader.getConfig("EMAIL_PORT")));
            javaMailSender.setUsername(configReader.getConfig("EMAIL_USERNAME"));
            javaMailSender.setPassword(configReader.getConfig("EMAIL_PASSWORD"));

            return javaMailSender;

        } catch (Exception exception) {
            System.out
                    .println("An error occurred while attempting to create JavaMailSender: " + exception.getMessage());
            return null;
        }
    }

    private ConfigReader createConfigReader() {
        if (configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }
}
