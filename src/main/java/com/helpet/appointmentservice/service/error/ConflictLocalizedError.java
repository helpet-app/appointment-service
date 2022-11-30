package com.helpet.appointmentservice.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ConflictLocalizedError implements DefaultEnumLocalizedError {
    TIME_SLOT_IS_NOT_AVAILABLE,
    DATE_IS_INVALID;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.conflict";
    }
}
