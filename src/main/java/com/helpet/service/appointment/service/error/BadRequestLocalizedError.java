package com.helpet.service.appointment.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum BadRequestLocalizedError implements DefaultEnumLocalizedError {
    CAN_ONLY_CANCEL_APPOINTMENT_WITH_SCHEDULED_STATUS,
    IT_IS_TOO_LATE_TO_CANCEL_APPOINTMENT,
    IT_IS_TOO_EARLY_TO_COMPLETE_APPOINTMENT,
    DATE_IS_INVALID;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.bad-request";
    }
}
