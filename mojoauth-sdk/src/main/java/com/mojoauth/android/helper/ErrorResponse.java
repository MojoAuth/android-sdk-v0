package com.mojoauth.android.helper;


public class ErrorResponse {
    private Integer code;
    private String message;
    private String description;

    /**
     *
     * @return The description
     */
    public String getDescription() {
        return description ;
    }

    /**
     *
     * @param description The description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message The Message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     *
     * @return The code
     */
    public Integer getCode() {
        return code;
    }
    /**
     *
     * @param code The Code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}





