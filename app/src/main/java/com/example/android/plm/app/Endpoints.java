package com.example.android.plm.app;

public class Endpoints {

    // for real android device, using ip of server.
    // for AVD, using 10.0.2.2
    // for Genymotion using 10.0.3.2
    // public static final String BASE_URL = "http://192.168.2.41/loyalty/public";
    public static final String BASE_URL = "http://10.0.2.2/loyalty/public";
//    public static final String BASE_URL = "http://10.0.3.2/loyalty/public";
    public static final String SIGNUP_URL = BASE_URL + "/customer/signup";
    public static final String LOGIN_URL = BASE_URL + "/customer/login";
    public static final String MEMBERS_URL = BASE_URL + "/customer/members";
    public static final String BASE_SHOP_URL = BASE_URL + "/customer/shop/";
    public static final String SEARCH_SHOP_URL = BASE_URL + "/customer/search/shops";
    public static final String BASE_SHOP_LOGO_URL = BASE_URL + "/images/shops/";
    public static final String NO_LOGO_SHOP_URL = BASE_SHOP_LOGO_URL + "no_image.jpg";
    public static final String BASE_REWARD_IMAGE_URL = BASE_URL + "/images/rewards/";
    public static final String NO_REWARD_IMAGE_URL = BASE_REWARD_IMAGE_URL + "No_image.png";
    public static final String REGISTER_MEMBER_URL = BASE_URL + "/customer/member/register";
    public static final String REWARD_URL = "/rewards";
    public static final String MEMBER_HISTORY_URL = "/member";
    public static final String REDEEM_REWARD_URL = BASE_URL + "/customer/redeem/reward";

}
