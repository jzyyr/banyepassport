package cn.tedu.csmallpassport.web;

public enum ServiceCode {
    OK(200),
    ERR_CONFLICT(409),
    ERR_NOT_FOUND(404),
    ERR_BAD_REQUEST(400),
    ERR_UNAUTHORIZED(401),
    ERR_UNAUTHORIZED_DISABLE(402),
    ERR_FORBIDDEN(403),
    ERR_INSERT(500),
    ERR_DELETE(501),
    ERR_UPDATE(502),
    ERR_SELECT(503),
    ERR_JWT_SIGNATURE(600),
    ERR_JWT_MALFORMED(601),
    ERR_JWT_EXPIRED(602),
    ERR_UNKNOWN(999);
    private Integer value;
    ServiceCode(Integer value){
        this.value=value;
    }

    public Integer getValue(){
        return value;
    }
}
