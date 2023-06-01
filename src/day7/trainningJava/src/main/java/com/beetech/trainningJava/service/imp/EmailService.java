package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.model.OrderEntityDto;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {
    @Value("${web.server.url}")
    private String webServerUrl;

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine templateEngine;

    public Boolean sendHtmlMessage(String to, String subject, String htmlContent) {
        var mimeMessage = new MimeMailMessage(javaMailSender.createMimeMessage());
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage.getMimeMessage());
        try {
            try {
                mimeMessageHelper.setFrom(new InternetAddress("noreply@datn.com", "Do An Tot Nghiep"));
            } catch (UnsupportedEncodingException ignored) {}
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage.getMimeMessage());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Async("taskExecutor")
    public CompletableFuture<Boolean> sendPaymentInformationHtmlMessage(OrderEntityDto orderEntityDto, String to) {
        var context = new Context();
        context.setVariable("order", orderEntityDto);
        context.setVariable("webServerUrl", webServerUrl);
        var htmlContent = templateEngine.process("email/payment-information", context);
        return CompletableFuture.completedFuture(sendHtmlMessage(to, "Payment Information", htmlContent));
    }

    @Async("taskExecutor")
    public CompletableFuture<Boolean> sendPaymentCancelHtmlMessage(OrderEntityDto orderEntityDto, String to) {
        var context = new Context();
        context.setVariable("order", orderEntityDto);
        context.setVariable("webServerUrl", webServerUrl);
        var htmlContent = templateEngine.process("email/payment-cancel", context);
        return CompletableFuture.completedFuture(sendHtmlMessage(to, "Payment Information", htmlContent));
    }

    @Async("taskExecutor")
    public CompletableFuture<Boolean> sendPaymentSuccessHtmlMessage(OrderEntityDto orderEntityDto, String to) {
        var context = new Context();
        context.setVariable("order", orderEntityDto);
        context.setVariable("webServerUrl", webServerUrl);
        var htmlContent = templateEngine.process("email/payment-success", context);
        return CompletableFuture.completedFuture(sendHtmlMessage(to, "Payment Information", htmlContent));
    }

    @Async("taskExecutor")
    public CompletableFuture<Boolean> sendRegisterTokenEmail(AccountEntity accountEntity, String to) {
        var context = new Context();
        context.setVariable("account", accountEntity);
        context.setVariable("webServerUrl", webServerUrl);
        var htmlContent = templateEngine.process("email/verify-account", context);
        return CompletableFuture.completedFuture(sendHtmlMessage(to, "Verify Account", htmlContent));
    }

    @Async("taskExecutor")
    public CompletableFuture<Boolean> sendForgotPasswordEmail(AccountEntity accountEntity, String to) {
        var context = new Context();
        context.setVariable("account", accountEntity);
        context.setVariable("webServerUrl", webServerUrl);
        var htmlContent = templateEngine.process("email/reset-password", context);
        return CompletableFuture.completedFuture(sendHtmlMessage(to, "Reset Password", htmlContent));
    }
}
