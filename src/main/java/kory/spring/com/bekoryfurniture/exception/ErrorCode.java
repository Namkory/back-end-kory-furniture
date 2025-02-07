package kory.spring.com.bekoryfurniture.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    KEY_INVALID(1001, "Invalid message key"),
    USER_TYPE_INVALID(401, "User type invalid"),
    USER_NOT_EXIST(401, "User name does not exist"),
    WRONG_PASSWORD(401, "Wrong password"),
    USERNAME_PASSWORD_INVALID(401, "Invalid user name and password"),
    INVALID_TOKEN(401, "Invalid token"),
    UNAUTHENTICATED(401, "Unauthenticated"),
    USERNAME_INVALID(401, "Username must be least 3 characters"),
    PASSWORD_INVALID(401, "Password must be least 8 characters"),
    NAME_REQUIRED(400, "Name required"),
    IMAGE_REQUIRED(400, "Image required"),
    THUMBNAIL_REQUIRED(400, "Thumbnail required"),
    DOB_REQUIRED(400, "Date of birth required"),
    GENDER_REQUIRED(400, "Gender required"),
    PAYMENT_METHOD_REQUIRED(400, "paymentMethod required"),
    LAST_PURCHASE_DATE_REQUIRED(400, "LastPurchaseDate required"),
    STATUS_REQUIRED(400, "Status required"),
    EMAIL_REQUIRED(400, "Email required"),
    INCORRECT_EMAIL_FORMAT(400, "Incorrect email format"),
    ADDRESS_REQUIRED(400, "address required"),
    PHONE_REQUIRED(400, "phone required"),
    SALE_PRICE_REQUIRED(400, "Sale price  required"),
    ESTABLISHED_PRICE_REQUIRED(400, "Established price required"),
    DESCRIPTION_REQUIRED(400, "Description required"),
    AMOUNT_REQUIRED(400, "Amount required"),
    SIZE_REQUIRED(400, "Size required"),
    USERNAME_REQUIRED(400, "User name required"),
    PASSWORD_REQUIRED(400, "Password required"),
    OLD_PASSWORD_REQUIRED(400, "Old password required"),
    NEW_PASSWORD_REQUIRED(400, "New password required"),
    TOKEN_REQUIRED(400, "Token required"),
    CUSTOMER_ID_REQUIRED(400, "Customer id required"),
    PRODUCTS_ID_REQUIRED(400, "Products Id required"),
    TOTAL_MONEY_REQUIRED(400, "Total money required"),
    ORDER_DATE_REQUIRED(400, "Order date required"),
    PRODUCT_ID_REQUIRED(400, "Product id required"),
    USER_NAME_ADMIN_EXISTS(400, "Admin with this username already exists"),
    CONTENT_REQUIRED(400, "Content required"),
    DATE_REQUIRED(400, "Date required"),
    USER_IMAGE_REQUIRED(400, "User image required"),
    COMMENTER_NAME_REQUIRED(400, "Commenter name required"),
    COMMENT_CONTENT_REQUIRED(400, "Comment content required"),

    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
