package com.horobets.spring.integration.demo.inbound.email;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public class EmailToXmlOrderTransformer {

    private static final String MIME_TYPE_TEXT_PLAIN = "text/plain";
    public static final String ITEM_SPLITTER = "\n";

    public static void main(String... args) {
    }

    public String transform(MimeMessage m) throws IOException, MessagingException {
        return wrapIntoXml(splitIntoMultipleItems(getMessageBody(m)));
    }

    private String[] splitIntoMultipleItems(String messageBody) {
        return messageBody.split(ITEM_SPLITTER);
    }

    private String wrapIntoXml(String[] messageBody) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("order");

        for (String item : messageBody) {
            root.addElement("item").addAttribute("id", item.trim());
        }

        return document.asXML();
    }

    private String getMessageBody(MimeMessage m) throws IOException, MessagingException {
        String result = null;

        Object contentObject = m.getContent();
        if (contentObject instanceof Multipart) {
            BodyPart clearTextPart = null;
            Multipart content = (Multipart) contentObject;

            for (int i = 0; i < content.getCount(); i++) {
                BodyPart part = content.getBodyPart(i);

                if (part.isMimeType(MIME_TYPE_TEXT_PLAIN)) {
                    clearTextPart = part;
                    break;
                }
            }

            if (clearTextPart != null) {
                result = (String) clearTextPart.getContent();
            }

        } else throw new IllegalArgumentException();

        return result;
    }
}
