package com.reservation.manager.service;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.reservation.manager.model.Reservation;

public interface IMpService {
    Preference createPreference(Reservation reservation) throws MPException, MPApiException;
}
