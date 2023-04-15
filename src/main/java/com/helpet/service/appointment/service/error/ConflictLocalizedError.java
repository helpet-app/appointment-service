package com.helpet.service.appointment.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ConflictLocalizedError implements DefaultEnumLocalizedError {
    TIME_SLOT_IS_NOT_AVAILABLE;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.conflict";
    }
}
