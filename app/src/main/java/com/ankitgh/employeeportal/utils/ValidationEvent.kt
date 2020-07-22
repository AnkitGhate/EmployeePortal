package com.ankitgh.employeeportal.utils

enum class ValidationStatus {
    INVALID_EMAIL,
    INVALID_PASSWORD,
    VALIDATION_SUCCESS,
    LOADING
}

data class ValidationEvent(val status: ValidationStatus, val message: String? = "", val loadingStatus: Boolean = false) {
    companion object {
        fun invalidEmail(message: String?): ValidationEvent {
            return ValidationEvent(ValidationStatus.INVALID_EMAIL, message)
        }

        fun invalidPassword(message: String?): ValidationEvent {
            return ValidationEvent(ValidationStatus.INVALID_PASSWORD, message)
        }

        fun validationSuccess(): ValidationEvent {
            return ValidationEvent(ValidationStatus.VALIDATION_SUCCESS)
        }

        fun loading(loadingStatus: Boolean): ValidationEvent {
            return ValidationEvent(ValidationStatus.LOADING, loadingStatus = loadingStatus)
        }
    }
}