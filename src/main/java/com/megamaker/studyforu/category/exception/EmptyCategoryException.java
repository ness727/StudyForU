package com.megamaker.studyforu.category.exception;

public class EmptyCategoryException extends RuntimeException {
    public EmptyCategoryException() {
        super();
    }

    public EmptyCategoryException(String message) {
        super(message);
    }

    public EmptyCategoryException(Throwable cause) {
        super(cause);
    }
}
