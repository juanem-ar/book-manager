package com.reservation.manager.service.impl;

import com.reservation.manager.dto.ReservationResponseDto;
import com.reservation.manager.exceptions.WhatsappException;
import com.reservation.manager.service.IWhatsappService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WhatsappServiceImpl implements IWhatsappService {

    @Value("${whatsapp.api.phone.number.id}")
    private String phoneSender;

    @Value("${whatsapp.api.user.access.token}")
    private String token;

    @Value("${whatsapp.api.version}")
    private String version;

    @Value("${whatsapp.api.enable}")
    private boolean enable;

    @Value("${whatsapp.api.templates.newReservation}")
    private String newReservationTemplate;

    @Override
    public void sendWhatsapp(ReservationResponseDto reservation) throws Exception{
        isEnable(enable);
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/" + version +"/" +phoneSender +"/messages"))
                    .header("Authorization", "Bearer "+ token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "{"+
                                    "\"messaging_product\": \"whatsapp\","+
                    "\"to\": \"" + reservation.getPhone().substring(1) + "\","+
                    "\"type\": \"template\","+
                    "\"template\": {"+
                "\"name\": \""+ newReservationTemplate +"\","+
                        "\"language\": {"+
                    "\"code\": \"es_AR\""+
                "},"+
                "\"components\": [{"+
                    "\"type\": \"body\","+
                        "\"parameters\": [{"+
                        "\"type\": \"text\","+
                            "\"text\": \"" + String.valueOf(reservation.getId()) + "\"" +
                    "},{"+
                        "\"type\": \"text\","+
                            "\"text\": \""+reservation.getUnitName()+"\""+
                    "},{"+
                        "\"type\": \"date_time\","+
                            "\"date_time\": {"+
                        "\"fallback_value\": \"" + reservation.getCheckIn().toString() + "\""+
                    "}},{"+
                        "\"type\": \"date_time\","+
                            "\"date_time\": {"+
                        "\"fallback_value\": \"" + reservation.getCheckOut().toString() + "\""+
                    "}},{"+
                        "\"type\": \"text\","+
                            "\"text\": \""+String.valueOf(reservation.getAmountOfPeople())+"\""+
                    "},{"+
                        "\"type\": \"text\","+
                            "\"text\": \"14:00hs\""+
                    "},{"+
                        "\"type\": \"text\","+
                            "\"text\": \"10:00hs\""+
                    "},{"+
                        "\"type\": \"text\","+
                            "\"text\": \""+reservation.getFullName()+"\""+
                    "},{"+
                        "\"type\": \"text\","+
                            "\"text\": \""+reservation.getPhone()+"\""+
                    "},{"+
                        "\"type\": \"text\","+
                            "\"text\": \"Transferencia\""+
                    "},{"+
                        "\"type\": \"text\","+
                            "\"text\": \""+String.valueOf(reservation.getPercent())+"%\""+
                    "},{"+
                        "\"type\": \"currency\","+
                            "\"currency\": {"+
                        "\"fallback_value\": \"Total\","+
                                "\"code\": \"ARS\","+
                                "\"amount_1000\": "+String.valueOf(reservation.getTotalAmount()*1000)+
                    "}},{"+
                        "\"type\": \"currency\","+
                            "\"currency\": {"+
                        "\"fallback_value\": \"Monto Abonado\","+
                                "\"code\": \"ARS\","+
                                "\"amount_1000\": "+ String.valueOf(reservation.getPartialPayment()*1000) +
                    "}"+
                    "},"+
                    "{"+
                        "\"type\": \"currency\","+
                            "\"currency\": {"+
                        "\"fallback_value\": \"Debe\","+
                                "\"code\": \"ARS\","+
                                "\"amount_1000\": "+ String.valueOf(reservation.getDebit()*1000) +
                    "}"+
                    "}"+
                "]"+
                "},"+
                "{"+
                    "\"type\": \"button\","+
                        "\"sub_type\": \"url\","+
                        "\"index\": \"0\","+
                        "\"parameters\": ["+
                    "{"+
                        "\"type\": \"text\"," +
                            "\"text\": \""+String.valueOf(reservation.getId())+"\"}]}]}}"
                    ))
                    .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }catch (URISyntaxException | IOException | InterruptedException e){
            throw new WhatsappException(e.getMessage());
        }
    }

    public void isEnable(boolean enable) throws WhatsappException {
        if(!enable){
            throw new WhatsappException("no funca whatsapp api");
        }
    }
}
