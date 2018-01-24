package dmt.server.controller.response;

import java.util.Map;

/**
 * Created by Marco on 26/07/2016.
 */
public class ErrorResponse {

    private int httpStatus;
    private String httpReason;
    private ErrorDetail error;
    private Map<String, String> fields;

    public ErrorResponse() {
    }

    public ErrorResponse(int httpStatus, String httpReason, String code, String message) {
        this.httpStatus = httpStatus;
        this.httpReason = httpReason;
        this.error = new ErrorDetail(code, message);
    }

    public ErrorResponse(int httpStatus, String httpReason, String code, String message, Map<String, String> fields) {
        this.httpStatus = httpStatus;
        this.httpReason = httpReason;
        this.error = new ErrorDetail(code, message);
        this.fields = fields;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getHttpReason() {
        return httpReason;
    }

    public void setHttpReason(String httpReason) {
        this.httpReason = httpReason;
    }

    public ErrorDetail getError() {
        return error;
    }

    public void setError(ErrorDetail error) {
        this.error = error;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public class ErrorDetail {

        private String code;
        private String message;

        public ErrorDetail() {
        }

        public ErrorDetail(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
