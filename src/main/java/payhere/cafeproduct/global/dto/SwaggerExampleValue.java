package payhere.cafeproduct.global.dto;

public class SwaggerExampleValue {
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "{\"meta\":{\"code\":500,\"message\":\"서버에서 오류가 발생했습니다..\"},\"data\":null}";

    // ** POST : /api/v1/user
    public static final String USER_SAVE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"생성했습니다.\"},\"data\":null}";
    public static final String USER_SAVE_EXISTED_NUMBER_RESPONSE = "{\"meta\":{\"code\":400,\"message\":\"이미 가입한 전화번호 입니다.\"},\"data\":null}";

}
