package com.reservation.manager.service.impl;

import com.reservation.manager.dto.ReservationResponseDto;
import com.reservation.manager.exceptions.EmailException;
import com.reservation.manager.model.Reservation;
import com.reservation.manager.service.IEmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Service
@Transactional
public class IEmailServiceImpl implements IEmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailSender;

    @Value("${reservation.manager.name}")
    private String nameBusiness;

    public IEmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendWelcomeEmailTo(String to) throws IOException {
        validateEmailSend();
        String content = "Welcome to the " + nameBusiness + "'s family.\nNow, you can create a reservation, modify and check availability of the rental units.\nGo ahead and explore!";
        sendEmail(to,content,"You've registered successfully!", false);
    }

    @Override
    public void sendReservationCreatedEmailTo(String to, ReservationResponseDto reservation) throws IOException {
        validateEmailSend();
        String content = "**IMPORTANT:** To confirm this reservation you must make the partial payment.\n\nDetails:\n\n" + reservation.toString();
        String subject = "Reservation ID#" + reservation.getId() + " created - Status: IN PROCESS.";
        sendEmail(to,content,subject,true);
    }

    @Override
    public void sendReservationConfirmEmailTo(String to, Reservation reservation) throws IOException {
        validateEmailSend();
        String content = "**RESERVATION CONFIRMED** \n\nDetails:\n\n" + reservation.toString();
        String subject = "Reservation ID#" + reservation.getId() + " CONFIRMED.";
        sendEmail(to,content,subject,true);
    }

    public void validateEmailSend() throws EmailException {
        if (emailSender == null){
            throw new EmailException("Invalid email sender");
        }
    }

    public void sendEmail(String to, String emailContent, String emailSubject, boolean cc){
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailSender);
            helper.setTo(to);
            if (cc)
                helper.setCc(emailSender);
            helper.setSubject(emailSubject);
            helper.setText(emailContent);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
