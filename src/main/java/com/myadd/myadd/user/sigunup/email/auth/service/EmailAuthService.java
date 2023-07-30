package com.myadd.myadd.user.sigunup.email.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    // 의존성 주입을 통해 필요한 객체를 가져옴
    private final JavaMailSender emailSender;
    // 타임리프를 사용하기 위한 객체를 의존성 주입으로 가져옴
    private final SpringTemplateEngine templateEngine;
    // 랜덤 인증 코드
    private String authNum;

    // 랜덤 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        //StringBuffer key = new StringBuffer();
        //for(int i=0;i<6;i++) {
        //    key.append(random.nextInt(10));
        //}
        //authNum = key.toString();
        authNum = String.valueOf(random.nextInt(8888)+1000); // 범위 : 1000 ~ 9999
    }

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        createCode(); //인증 코드 생성
        String setFrom = "myaddauth@gmail.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; // 받는 사람
        String title = "[my ADD] 인증 코드는 " + authNum + "입니다"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); // 제목 설정
        message.setFrom(setFrom); // 보내는 이메일
        message.setText(setContext(authNum), "utf-8", "html");

        return message;
    }

    // 타임리프를 이용한 context 설정
    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }

    // 실제 메일 전송 - controller에서 호출
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        // 메일 전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        // 실제 메일 전송
        emailSender.send(emailForm);

        return authNum; //인증 코드 반환
    }
}
