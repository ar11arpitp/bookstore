package com.online.bookstore.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCodeEnums {
    CREATED_SUCCESS("SUCCESS-01", "Created Successfully."),
    UPDATED_SUCCESS("SUCCESS-02", "ID: %s Updated Successfully."),
    DELETED_SUCCESS("SUCCESS-03", "ID: %s Deleted Successfully."),
    DELETED_VIDEO_SUCCESS("SUCCESS-04", "This Video Deleted Successfully."),
    COLLECTION_IDS_DELETED_SUCCESS("SUCCESS-05", "These IDs: %s Deleted Successfully."),
    UPLOAD_VIDEOS_SUCCESS("SUCCESS-06", "These videos Uploaded Successfully."),
    UPLOAD_VIDEO_SUCCESS("SUCCESS-07", "This video Uploaded Successfully."),
    DELETED_IMAGE_SUCCESS("SUCCESS-08", "This image Deleted Successfully."),
    ADD_TO_CART_SUCCESS("SUCCESS-09", "Added to cart successfully."),
    DELETED_CART_ITEM_SUCCESS("SUCCESS-10", "CartItem Deleted Successfully."),;

    final String successCode;
    final String message;
}
