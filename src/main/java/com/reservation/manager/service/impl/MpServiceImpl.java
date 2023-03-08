package com.reservation.manager.service.impl;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.reservation.manager.model.Reservation;
import com.reservation.manager.service.IMpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class MpServiceImpl implements IMpService {
    private final static Long HOURS_TO_EXPIRE = 24L;

    @Value(value = "${mercado.pago.env.access.token}")
    private String mpAccessToken;

    @Value(value = "${reservation.manager.name}")
    private String nameBusiness;

    @Value(value = "${mercado.pago.urls.return.success}")
    private String urlSuccess;

    @Value(value = "${mercado.pago.urls.return.pending}")
    private String urlPending;

    @Value(value = "${mercado.pago.urls.return.failure}")
    private String urlFailure;
    private List<PreferenceItemRequest> itemsList = new ArrayList<>();

    public void createItem(Reservation reservation){
        itemsList.clear();
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .id(String.valueOf(reservation.getId()))
                .title("Reservation in " + reservation.getUnit().getName() + " for " + String.valueOf(reservation.getAmountOfPeople()) + " people.")
                .description("check-in: " + reservation.getCheckIn() + " || check-out: " + reservation.getCheckOut())
                .pictureUrl("http://www.myapp.com/myimage.jpg")
                .categoryId("travels")
                .quantity(1)
                .currencyId("ARS")
                .unitPrice(BigDecimal.valueOf(reservation.getPartialPayment()))
                .build();
        itemsList.add(item);
    }

    public PreferenceBackUrlsRequest preferenceBackUrls(String urlSuccess,String urlPending,String urlFailure, Long idReservation){
        return PreferenceBackUrlsRequest.builder()
                .success(urlSuccess + String.valueOf(idReservation))
                .pending(urlPending)
                .failure(urlFailure)
                .build();
    }

    public PreferencePaymentMethodsRequest paymentMethodsRequest(String excludePayment){
        PreferencePaymentMethodRequest idExcluded = PreferencePaymentMethodRequest.builder().id(excludePayment).build();
        return PreferencePaymentMethodsRequest
                .builder()
                .excludedPaymentMethods(Collections.singletonList(idExcluded))
                .build();
    }

    public Preference createPreference(Reservation reservation) throws MPException, MPApiException {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("Content-type","application/json");
        customHeaders.put("Authorization",mpAccessToken);

        MPRequestOptions requestOption= MPRequestOptions.builder()
                .accessToken(mpAccessToken)
                .customHeaders(customHeaders)
                .build();

        createItem(reservation);
        PreferenceRequest request = PreferenceRequest.builder()
                .items(itemsList)
                .payer(createPayer(reservation))
                .externalReference(String.valueOf(reservation.getPartialPayment()))
                .autoReturn("approved")
                .paymentMethods(paymentMethodsRequest("credit_card"))
                .binaryMode(true)
                .statementDescriptor(nameBusiness)
                .expires(true)
                .expirationDateFrom(setExpireDate(true))
                .expirationDateTo(setExpireDate(false))
                .dateOfExpiration(setExpireDate(false))
                .backUrls(preferenceBackUrls(urlSuccess,urlPending,urlFailure,reservation.getId())).build();

        PreferenceClient client = new PreferenceClient();
        return client.create(request,requestOption);
    }
    public OffsetDateTime setExpireDate(boolean isDateFrom){
        if (isDateFrom)
            return LocalDateTime.now().atZone(ZoneId.systemDefault()).toOffsetDateTime();
        else
            return LocalDateTime.now().plusHours(HOURS_TO_EXPIRE).atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
    public PreferencePayerRequest createPayer(Reservation reservation){
        return PreferencePayerRequest.builder()
                .address(AddressRequest.builder().streetName(reservation.getUser().getAddress()).build())
                .name(reservation.getUser().getFirstName())
                .surname(reservation.getUser().getLastName())
                .email(reservation.getUser().getEmail())
                .phone(PhoneRequest.builder().areaCode(reservation.getUser().getAreaCode()).number(reservation.getUser().getPhoneNumber()).build())
                .identification(IdentificationRequest.builder().type(String.valueOf(reservation.getUser().getDocumentType())).number(reservation.getUser().getDocumentNumber()).build())
                //TODO ADD ZIP CODE & STREET NUMBER IN USER ENTITY IF THESE IS NECESSARY
                //USER TEST
                //.address(AddressRequest.builder().streetName(reservation.getUser().getAddress()).build())
                //.name("NAME USER TEST")
                //.email("EMAIL USER TEST")
                //.identification(IdentificationRequest.builder().type("DNI").number("USER'S DOCUMENT NUMBER").build())
                .build();
    }

}
