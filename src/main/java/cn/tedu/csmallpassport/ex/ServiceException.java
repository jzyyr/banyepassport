package cn.tedu.csmallpassport.ex;


import cn.tedu.csmallpassport.web.ServiceCode;

/**
 * 业务异常
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public class ServiceException extends RuntimeException{
    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode, String message){
        super(message);
        this.serviceCode=serviceCode;

    }
    public ServiceCode getServiceCode() {
        return serviceCode;
    }
}
