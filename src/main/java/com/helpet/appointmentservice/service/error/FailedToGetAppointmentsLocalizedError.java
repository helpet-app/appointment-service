package com.helpet.appointmentservice.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum FailedToGetAppointmentsLocalizedError implements DefaultEnumLocalizedError {
    VET_DOES_NOT_EXIST,
    PET_DOES_NOT_EXIST,
    USER_DOES_NOT_EXIST,
    USER_IS_NOT_FAMILY_MEMBER,
    PET_IS_NOT_FAMILY_MEMBER
}
