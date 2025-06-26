package com.guildgate.web.Persistence.exceptions;

/**
 *
 * @author Lavender
 */
public class AvatarNotFoundException extends Exception{
    public AvatarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public AvatarNotFoundException(String message) {
        super(message);
    }
}
