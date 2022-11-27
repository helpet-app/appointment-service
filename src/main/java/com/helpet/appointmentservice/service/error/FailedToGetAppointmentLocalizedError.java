package com.helpet.appointmentservice.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum FailedToGetAppointmentLocalizedError implements DefaultEnumLocalizedError {
    VET_DOES_NOT_HAVE_THIS_APPOINTMENT,
    USER_DOES_NOT_HAVE_THIS_APPOINTMENT,
    PET_DOES_NOT_HAVE_THIS_APPOINTMENT,
    VET_DOES_NOT_EXIST,
    PET_DOES_NOT_EXIST,
    USER_DOES_NOT_EXIST,
    USER_IS_NOT_FAMILY_MEMBER,
    PET_IS_NOT_FAMILY_MEMBER
}
