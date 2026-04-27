package com.zoopick.server.util;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
@RequiredArgsConstructor
public class EmailProvider {
    private static final String SUBJECT = "[명지대 분실물 찾기 서비스] 인증메일 입니다.";
    private static final Log LOG = LogFactory.getLog(EmailProvider.class);

    private final JavaMailSender javaMailSender;

    public void senderCertificationMail(String email, String certificationNumber) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (Exception exception) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    private String getCertificationMessage(String certificationNumber) {
        return "<div style='font-family: \"Pretendard\", \"Apple SD Gothic Neo\", \"Malgun Gothic\", sans-serif; max-width: 500px; margin: 0 auto; padding: 40px; border: 1px solid #f0f0f0; border-radius: 16px; background-color: #ffffff;'>" +
                "  <h2 style='color: #1a73e8; font-size: 24px; font-weight: 700; margin-bottom: 24px; text-align: center;'>명지대 분실물 찾기 서비스</h2>" +
                "  <div style='text-align: center; color: #333333; line-height: 1.6;'>" +
                "    <p style='font-size: 18px; font-weight: 600; margin-bottom: 8px;'></p>" +
                "    <p style='font-size: 14px; color: #666666; margin-bottom: 32px;'><br/>아래의 인증 코드를 입력하여 가입을 완료해 주세요.</p>" +
                "  </div>" +
                "  <div style='background-color: #f8f9fa; border-radius: 12px; padding: 24px; text-align: center; margin-bottom: 32px;'>" +
                "    <span style='display: block; font-size: 12px; color: #888888; margin-bottom: 8px; letter-spacing: 1px;'>인증 코드</span>" +
                "    <strong style='font-size: 36px; letter-spacing: 10px; color: #1a73e8; font-family: monospace;'>" + certificationNumber + "</strong>" +
                "  </div>" +
                "  <p style='font-size: 12px; color: #999999; text-align: center; line-height: 1.5;'>" +
                "    <br/>" +
                "    &copy; Myongji Univ. Capstone Project" +
                "  </p>" +
                "</div>";
    }
}