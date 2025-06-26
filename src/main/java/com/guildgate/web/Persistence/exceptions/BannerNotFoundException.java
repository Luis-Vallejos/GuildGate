package com.guildgate.web.Persistence.exceptions;

/**
 *
 * @author Lavender
 */
public class BannerNotFoundException extends Exception {
    public BannerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public BannerNotFoundException(String message) {
        super(message);
    }
}
