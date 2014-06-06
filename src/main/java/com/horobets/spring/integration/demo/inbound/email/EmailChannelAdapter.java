package com.horobets.spring.integration.demo.inbound.email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * Created by Oleksandr Horobets on 6/1/14.
 */
public class EmailChannelAdapter {
    public static void main(String... args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/inbound/email.xml");
    }

    public void receive(MimeMessage m) throws IOException, MessagingException {
        String result = null;

        Object contentObject = m.getContent();
        if (contentObject instanceof Multipart) {
            BodyPart clearTextPart = null;
            Multipart content = (Multipart) contentObject;

            for (int i = 0; i < content.getCount(); i++) {
                BodyPart part = content.getBodyPart(i);

                if (part.isMimeType("text/plain")) {
                    clearTextPart = part;
                    break;
                }
            }

            if (clearTextPart != null) {
                result = (String) clearTextPart.getContent();
            }

        } else if (contentObject instanceof String) {
            result = (String) contentObject;
        } else {
            result = null;
        }

        System.out.println(result);
    }
}
