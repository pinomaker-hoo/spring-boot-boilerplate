package payhere.cafeproduct.global.dto;

public class SwaggerExampleValue {
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "{\"meta\":{\"code\":500,\"message\":\"서버에서 오류가 발생했습니다..\"},\"data\":null}";
    public static final String UN_AUTHENTICATION_RESPONSE = "{\"meta\":{\"code\":401,\"message\":\"JWT 토큰이 잘못되었습니다\"},\"data\":null}";

    // ** POST : /api/v1/user
    public static final String USER_SAVE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"생성했습니다.\"},\"data\":null}";
    public static final String USER_SAVE_EXISTED_NUMBER_RESPONSE = "{\"meta\":{\"code\":400,\"message\":\"이미 가입한 전화번호 입니다.\"},\"data\":null}";

    // ** POST : /api/v1/user/login
    public static final String USER_LOGIN_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"로그인에 성공했습니다.\"},\"data\":{\"accessToken\":\"eyJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiVUJPVE5VV1VlWUR4U2lraW1lWmZsUSIsImlkIjoiaEg5MzRaTEpYWWhFaEV1UERVb1NnQSIsImlhdCI6MTcwMjM4OTQxNSwiZXhwIjoxNzAyMzkxMjE1fQ.TBXT7FwC7F1HhQNiUnZcBS68B9T3SpI4pRPDug38CfIhz-69CUwhfCXruHo5I2yeMKCCWNHbpn5TS5pVEEgqGG_kG1ABinUvH2GCIMhvqoZf6iOTJqjbPxgA63VNoRtNfpHyMn_R1lgW75C-BUreyYSDcxVYGOh6oc5tB8jbfx6O6kRslp14xoX4BKEiOiTjJSIoCddRZY94ru8V7WhTXG6TK927AYZHXeoOuYUZqbDRJc2nxT-2KTM_MYuicnDZqo6XPgxkE4yS5SaSkQZqhUSyXD8nrFfNvckZdXhfItPkybPOYYuMqo4vaWuLndw71TkFqJzEBixTLLPewtJYcg\",\"refreshToken\":\"eyJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiVUJPVE5VV1VlWUR4U2lraW1lWmZsUSIsImlkIjoiaEg5MzRaTEpYWWhFaEV1UERVb1NnQSIsImlhdCI6MTcwMjM4OTQxNSwiZXhwIjoxNzAyOTk0MjE1fQ.ZrMcx1vLkGUSsRFKjEPBqd6L8XBLKr4hiXWgVx0y8Lu-V2uHtWrrfbt8kGPX_lz92v0GCBSP87z42o_Df5yjHagzyRDZL5n3o1evCQWLAvqJCluZtm3Q2FqBKUr1f7iTQj_viHoKE6xCFJ5A3nDGCzk8VwJ86RxDg5SQUX_R0ZhcW33n6c1PJTvaokJPH8VcbuYULkSy82ncYPeVSg6DotVhfeva6Sn15hrnE6tpAP6b9LAmSkZCJr-EXuo20cKXFrSkHZrIaplWT-TL9nZ5Q04G_zEapkFC8rIam0BNh04WgJlufsRIrE9Mrn3Ka27O-QAvRRE3bsva2eIBN18VWg\"}}";
    public static final String USER_LOGIN_NOT_FOUND_RESPONSE = "{\"meta\":{\"code\":404,\"message\":\"유저를 찾을 수 없습니다.\"},\"data\":null}";
    public static final String USER_LOGIN_NOT_MATCH_PASSWORD_RESPONSE = "{\"meta\":{\"code\":400,\"message\":\"비밀번호가 같지 않습니다.\"},\"data\":null}";

    // ** POST : /api/v1/product/category
    public static final String PRODUCT_CATEGORY_SAVE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품 카테고리를 생성합니다.\"},\"data\":null}";
    public static final String PRODUCT_CATEGORY_SAVE_NOT_FOUND_USER_RESPONSE = "{\"meta\":{\"code\":404,\"message\":\"로그인한 유저 정보를 찾을 수 없습니다.\"},\"data\":null}";

    // ** GET : /api/v1/product/category
    public static final String PRODUCT_CATEGORY_FIND_LIST_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품 카테고리 리스트를 조회합니다.\"},\"data\":{\"totalPages\":2,\"currentPage\":0,\"totalItems\":18,\"data\":[{\"id\":19,\"name\":\"coffee\"},{\"id\":18,\"name\":\"coffee\"},{\"id\":17,\"name\":\"coffee\"},{\"id\":16,\"name\":\"coffee\"},{\"id\":15,\"name\":\"coffee\"},{\"id\":14,\"name\":\"coffee\"},{\"id\":13,\"name\":\"coffee\"},{\"id\":12,\"name\":\"coffee\"},{\"id\":11,\"name\":\"coffee\"},{\"id\":10,\"name\":\"coffee\"}]}}";

    // ** GET : /api/v1/product/category/{id}
    public static final String PRODUCT_CATEGORY_FIND_ONE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품 카테고리 정보를 조회합니다.\"},\"data\":{\"id\":10,\"name\":\"coffee\",\"exposeYn\":\"Y\",\"createdDate\":\"2023-12-13T01:28:37.626824\"}}";
    public static final String PRODUCT_CATEGORY_FIND_ONE_NOT_FOUND_RESPONSE = "{\"meta\":{\"code\":404,\"message\":\"상품 카테고리 정보를 찾을 수 없습니다.\"},\"data\":null}";

    // ** PUT : /api/v1/product/category
    public static final String PRODUCT_CATEGORY_UPDATE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품 카테고리 정보를 수정합니다.\"},\"data\":null}";
    public static final String PRODUCT_CATEGORY_UPDATE_NOT_FOUND_RESPONSE = "{\"meta\":{\"code\":404,\"message\":\"상품 카테고리 정보를 찾을 수 없습니다.\"},\"data\":null}";
    public static final String PRODUCT_CATEGORY_UPDATE_FORBIDDEN_RESPONSE = "{\"meta\":{\"code\":403,\"message\":\"해당 카테고리를 수정할 권한이 없습니다.\"},\"data\":null}";

    // ** DELETE : /api/v1/product/category
    public static final String PRODUCT_CATEGORY_DELETE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품 카테고리를 삭제합니다.\"},\"data\":null}";
    public static final String PRODUCT_CATEGORY_DELETE_FORBIDDEN_RESPONSE = "{\"meta\":{\"code\":403,\"message\":\"해당 카테고리를 삭제할 권한이 없습니다.\"},\"data\":null}";

    // ** POST : /api/v1/product
    public static final String PRODUCT_SAVE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품을 생성합니다..\"},\"data\":null}";
    public static final String PRODUCT_SAVE_NOT_FOUND_CATEGORY_RESPONSE = "{\"meta\":{\"code\":404,\"message\":\"상품 카테고리 정보를 찾을 수 없습니다.\"},\"data\":null}";

    // ** PUT : /api/v1/product
    public static final String PRODUCT_UPDATE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품을 수정합니다..\"},\"data\":null}";
    public static final String PRODUCT_UPDATE_NOT_FOUND_RESPONSE = "{\"meta\":{\"code\":404,\"message\":\"상품 정보를 찾을 수 없습니다.\"},\"data\":null}";
    public static final String PRODUCT_UPDATE_FORBIDDEN_RESPONSE = "{\"meta\":{\"code\":403,\"message\":\"상품을 수정할 권한이 없습니다.\"},\"data\":null}";

    // ** DELETE : /api/v1/product
    public static final String PRODUCT_DELETE_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품을 삭제합니다.\"},\"data\":null}";
    public static final String PRODUCT_DELETE_FORBIDDEN_RESPONSE = "{\"meta\":{\"code\":403,\"message\":\"해당 상품을 삭제할 권한이 없습니다.\"},\"data\":null}";

    // ** GET : /api/v1/product/{id}
    public static final String PRODUCT_FIND_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"상품 정보를 조회합니다.\"},\"data\":{\"id\":3,\"price\":30000,\"cost\":10000,\"name\":\"아메리카노\",\"code\":\"ABCD_EF@_123\",\"expirationDate\":\"2024-12-31T12:00:00\",\"size\":\"SMALL\",\"soldOutYn\":\"N\",\"exposeYn\":\"Y\",\"categoryName\":\"coffee\",\"createdDate\":\"2023-12-13T09:45:05.826691\"}}";
    public static final String PRODUCT_FIND_NOT_FOUND_RESPONSE = "{\"meta\":{\"code\":404,\"message\":\"상품 정보를 찾을 수 없습니다.\"},\"data\":null}";;
}
