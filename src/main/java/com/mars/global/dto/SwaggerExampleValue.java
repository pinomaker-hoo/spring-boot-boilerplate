package com.mars.global.dto;

public class SwaggerExampleValue {
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "{\"code\":401,\"status\":\"INTERNAL_SERVER\",\"message\":\"서버에서 오류가 발생했습니다.\"}";
    public static final String UN_AUTHENTICATION_RESPONSE =  "{\"code\":401,\"status\":\"UNAUTHORIZED\",\"message\":\"만료된 JWT 토큰입니다.\"}";

    // ** POST : /api/v1/user
    public static final String USER_SAVE_RESPONSE = "{\"code\":200,\"status\":\"OK\",\"message\":\"회원가입에 성공하였습니다.\"}";
    public static final String USER_SAVE_EXISTED_NUMBER_RESPONSE = "{\"code\":400,\"status\":\"BAD_REQUEST\",\"message\":\"이미 사용 중인 아이디 입니다.\"}";

    // ** POST : /api/v1/user/login
    public static final String USER_LOGIN_RESPONSE = "{\"code\":200,\"status\":\"OK\",\"data\":{\"accessToken\":\"eyJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiVUJPVE5VV1VlWUR4U2lraW1lWmZsUSIsImlkIjoiR0lTd2pBczdfYng4djJ4REFfY25uZyIsImlhdCI6MTcwNDM5Mzg0MCwiZXhwIjoxNzA0Mzk1NjQwfQ.IqPb3l-Rz2iqRNSHHKMqqQMHQh7nc4o0lCFuEKWmViNBoXxLFcDBM-H95LO4ZGZAhuJt3EjKXgoLih2DHS25A1UD87meAZOWwKJ1Q7P16Cp7GS3txPTSDs8FvMHNx5mCoUCgJQ2Lbss7t4kGgdcR0ZTDdNGg0sZRUj_ok9hPH0M8VqkZay3i_b5xjy400MxL0X27OfyK22kj1GbkIwGnkN2MgRVrFmvDnzcs0s2Mqd8SjJqHCX5ELr5uJjwFGBQa81uRLgfsT6Vn3rWYPpAduq87Ot8wUJPZ-NnsV_vB06kgQ_8p5O8DogKRmIxsDoyxPgIesxcYvyfaIa_ZiP-Iag\",\"refreshToken\":\"eyJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiVUJPVE5VV1VlWUR4U2lraW1lWmZsUSIsImlkIjoiR0lTd2pBczdfYng4djJ4REFfY25uZyIsImlhdCI6MTcwNDM5Mzg0MCwiZXhwIjoxNzA0OTk4NjQwfQ.JI6T-YJXxifd5R8N49efBhfgyULj5X-A60P5qn1APLi5pZYW2gWaTg-DGUohwLyFpMfdPcA2FySor71hihhem5y6zGGJ3PI0ZW_XVUjloJaPeZ2B-qsOPePDoQmMoK4vzmBd2ZXZbXKdoW40ZmufnnpsUl2gkgEF3BafEE2b7QUfTBr9Dw9PnQBs0PZDA19kDChSaANi8AVg4LDftXCcuezznRDIyrTY3MLNSD976VQ7DE2jEt6XZdyP5lDAH93IpmX3p74DdaUdtcschMU4FyTLEDYnSJekhwdwMryo6iAusbY6kAWZ_zKJXPPEnEQZat6s82QLTh9_2AYKNhy3Ug\"},\"message\":\"로그인에 성공했습니다.\"}";
    public static final String USER_LOGIN_NOT_FOUND_RESPONSE =  "{\"code\":404,\"status\":\"NOT_FOUND\",\"message\":\"유저를 찾을 수 없습니다.\"}";
    public static final String USER_LOGIN_NOT_MATCH_PASSWORD_RESPONSE = "{\"code\":400,\"status\":\"BAD_REQUEST\",\"message\":\"비밀번호가 같지 않습니다.\"}";

    // ** POST : /api/v1/user/reissue
    public static final String REISSUE_TOKEN_RESPONSE = "{\"meta\":{\"code\":200,\"message\":\"토큰을 재발급 받습니다.\"},\"data\":{\"accessToken\":\"eyJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiVUJPVE5VV1VlWUR4U2lraW1lWmZsUSIsImlkIjoiaEg5MzRaTEpYWWhFaEV1UERVb1NnQSIsImlhdCI6MTcwMjM4OTQxNSwiZXhwIjoxNzAyMzkxMjE1fQ.TBXT7FwC7F1HhQNiUnZcBS68B9T3SpI4pRPDug38CfIhz-69CUwhfCXruHo5I2yeMKCCWNHbpn5TS5pVEEgqGG_kG1ABinUvH2GCIMhvqoZf6iOTJqjbPxgA63VNoRtNfpHyMn_R1lgW75C-BUreyYSDcxVYGOh6oc5tB8jbfx6O6kRslp14xoX4BKEiOiTjJSIoCddRZY94ru8V7WhTXG6TK927AYZHXeoOuYUZqbDRJc2nxT-2KTM_MYuicnDZqo6XPgxkE4yS5SaSkQZqhUSyXD8nrFfNvckZdXhfItPkybPOYYuMqo4vaWuLndw71TkFqJzEBixTLLPewtJYcg\",\"refreshToken\":\"eyJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiVUJPVE5VV1VlWUR4U2lraW1lWmZsUSIsImlkIjoiaEg5MzRaTEpYWWhFaEV1UERVb1NnQSIsImlhdCI6MTcwMjM4OTQxNSwiZXhwIjoxNzAyOTk0MjE1fQ.ZrMcx1vLkGUSsRFKjEPBqd6L8XBLKr4hiXWgVx0y8Lu-V2uHtWrrfbt8kGPX_lz92v0GCBSP87z42o_Df5yjHagzyRDZL5n3o1evCQWLAvqJCluZtm3Q2FqBKUr1f7iTQj_viHoKE6xCFJ5A3nDGCzk8VwJ86RxDg5SQUX_R0ZhcW33n6c1PJTvaokJPH8VcbuYULkSy82ncYPeVSg6DotVhfeva6Sn15hrnE6tpAP6b9LAmSkZCJr-EXuo20cKXFrSkHZrIaplWT-TL9nZ5Q04G_zEapkFC8rIam0BNh04WgJlufsRIrE9Mrn3Ka27O-QAvRRE3bsva2eIBN18VWg\"}}";

    // ** POST : /api/v1/user
    public static final String SAMPLE_RESPONSE = "{\"code\":200,\"status\":\"OK\",\"message\":\"샘플\"}";
}
