package utc2.itk62.e_reader.service.impl;

import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.service.MailService;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final FreeMarkerConfig freemarkerConfig;

    @Override
    public void send(String to, String subject, String template, Map<String, Object> templateModel) throws EReaderException {
        try {
            Template freemarkerTemplate = freemarkerConfig.getConfiguration()
                    .getTemplate(template);
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            helper.setTo(to);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Exception {}", e.getMessage());
            // TODO: create message
            throw new EReaderException("");
        }
    }
}
