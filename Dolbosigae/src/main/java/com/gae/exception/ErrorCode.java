package com.gae.exception;

public enum ErrorCode {
    EMPTY_FILE_EXCEPTION("File is empty"),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("IO Exception occurred while uploading image"),
    INVALID_FILE_EXTENTION("Invalid file extension"),
    NO_FILE_EXTENTION("No file extension found"),
    PUT_OBJECT_EXCEPTION("Exception while putting object to S3"),
    IO_EXCEPTION_ON_IMAGE_DELETE("IO Exception occurred while deleting image");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
