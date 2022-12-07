package com.helpet.service.appointment.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ForbiddenLocalizedError implements DefaultEnumLocalizedError {
    USER_IS_NOT_RELATED_TO_PET;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.forbidden";
    }
}
