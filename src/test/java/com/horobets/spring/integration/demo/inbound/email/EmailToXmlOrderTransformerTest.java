package com.horobets.spring.integration.demo.inbound.email;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class EmailToXmlOrderTransformerTest {
    @Mock
    private MimeMessage mimeMessageMock;
    @Mock
    private Multipart multipartMock;
    @Mock
    private BodyPart bodyPartMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private void configureMocks(String messageBody) throws MessagingException, IOException {
        when(bodyPartMock.isMimeType(anyString())).thenReturn(true);
        when(bodyPartMock.getContent()).thenReturn(messageBody);

        when(multipartMock.getCount()).thenReturn(1);
        when(multipartMock.getBodyPart(0)).thenReturn(bodyPartMock);

        when(mimeMessageMock.getContent()).thenReturn(multipartMock);
    }

    @Test
    public void shouldReturnValidXmlForOneLineOrder() throws IOException, MessagingException {
        //given
        EmailToXmlOrderTransformer transformer = new EmailToXmlOrderTransformer();
        configureMocks("123");

        //when
        String xml = transformer.transform(mimeMessageMock);

        //then
        assertThat(xml, equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<order><item id=\"123\"/></order>"
        ));
    }

    @Test
    public void shouldReturnValidXmlForMultiLineOrder() throws IOException, MessagingException {
        //given
        EmailToXmlOrderTransformer transformer = new EmailToXmlOrderTransformer();
        configureMocks("123\n321\n132");

        //when
        String xml = transformer.transform(mimeMessageMock);

        //then
        assertThat(xml, equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<order><item id=\"123\"/><item id=\"321\"/><item id=\"132\"/></order>"
        ));
    }
}
