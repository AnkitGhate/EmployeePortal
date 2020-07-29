/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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