package com.megamaker.studyforu.post.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(Throwable cause) {
        super(cause);
    }
}
