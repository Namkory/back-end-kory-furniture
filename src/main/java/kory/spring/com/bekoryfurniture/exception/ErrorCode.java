package kory.spring.com.bekoryfurniture.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESSFUL(200, "Create new successful"),
    GET_BY_ID_SUCCESSFUL(200, "Get by id successful"),
    UPDATE_SUCCESSFUL(200, "Update successful"),
    DISABLE_ACCOUNT(200, "Account disabled successfully"),
    DELETE_SUCCESSFUL(200, "Delete successful"),
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
    ADDRESS_REQUIRED(400, "Address required"),
    PHONE_REQUIRED(400, "Phone required"),
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
    COMMENTER_NAME_REQUIRED(400, "Customer name required"),
    COMMENT_CONTENT_REQUIRED(400, "Comment content required"),
    CATEGORY_NAME_EXISTED(400, "Category name existed"),
    NOT_FOUND_CATEGORY(400, "This category could not be found!!"),
    NOT_FOUND_PRODUCT(400, "This product could not be found!!"),
    NOT_FOUND_ADMIN(400, "Admin doesn't exist"),
    NOT_FOUND_CUSTOMER(400, "Customer doesn't exist"),
    NOT_FOUND_ORDER(400, "Order doesn't exist"),
    NOT_FOUND_COMMENT(400, "Comment doesn't exist"),
    INVALID_CATEGORY_ID(400, "Invalid category id"),
    INVALID_PRODUCT_ID_OR_CUSTOMER_ID(400, "Invalid product id or customer id"),
    INVALID_CUSTOMER_ID_OR_PRODUCT_ID(400, "Invalid customerId or productId"),
    NOT_FOUND_SHOPPING_CART(400, "This Shopping cart item could not be found!!"),
    INVALID_CUSTOMER_ID_OR_LIST_PRODUCT_ID(400, "Invalid customer id or array product id can't be null"),
    OUT_OF_STOCK(400, "Out of stock - "),
    UNAUTHORIZED (400, "You do not have permission to delete this comment."),
    ACCOUNT_DISABLED(400, "This account has been disabled")



    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
