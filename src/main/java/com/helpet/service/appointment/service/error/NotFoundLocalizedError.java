package com.helpet.service.appointment.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum NotFoundLocalizedError implements DefaultEnumLocalizedError {
    ACCOUNT_DOES_NOT_EXIST,
    VET_DOES_NOT_EXIST,
    PET_DOES_NOT_EXIST,
    USER_DOES_NOT_HAVE_THIS_PET,
    TIME_SLOT_DOES_NOT_EXIST,
    VET_DOES_NOT_HAVE_THIS_APPOINTMENT,
    USER_DOES_NOT_HAVE_THIS_APPOINTMENT,
    PET_DOES_NOT_HAVE_THIS_APPOINTMENT;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.not-found";
    }
}
