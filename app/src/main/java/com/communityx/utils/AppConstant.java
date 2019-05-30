package com.communityx.utils;

public interface AppConstant {

    String PREF_USER_ID  = "pref_user_id";
    String PREF_SESSION_ID  = "pref_session_id";
    String PREF_IS_LOGIN  = "pref_is_login";
    String PREF_CATEGORY  = "pref_category";
    String PREF_EMAIL = "pref_email";
    String PREF_USERNAME = "pref_username";

    String SAVECARD = "saved_card";
    String DEBITCARD = "debit_card";
    String CREDITCARD = "credit_card";
    String ACTION_SIGN_UP_STUDENT = "STUDENT";
    String ACTION_SIGN_UP_PROFESSIONAL = "PROFESSIONAL";
    String ACTION_SIGN_UP_ORGANIZATION = "ORGANIZATION";
    String IS_OTHER_PROFILE = "is_other_profile";
    String ACCESS_TOKEN_KEY = "access_token_key";
    String STATUS_SUCCESS = "Success";
    String STATUS_FAILURE = "Failure";
    String HIGH_SCHOOL = "HIGH_SCHOOL";
    String COLLEGE = "COLLEGE";
    String FRAGMENT_PROFILE = "fragment_profile";
    String MILTI_PART_FORM_DATA = "multipart/form-data";
    String IMAGE_PARAM = "image";
    String TYPE = "type";
    String SESSION_KEY = "session_key";
    String USER_ID = "user_key";

    String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    int PICK_FROM_CAMERA = 0;
    int PICK_FROM_GALLERY = 1;
    int REQUEST_PERMISSION_CODE = 300;

    String INTENT_USERLIST = "userlist";
}
