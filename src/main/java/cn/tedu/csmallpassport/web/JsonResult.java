package cn.tedu.csmallpassport.web;

import cn.tedu.csmallpassport.ex.ServiceException;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> implements Serializable {


    /**
     * 状态
     */
    @ApiModelProperty("响应状态码")
    private Integer state;
    /**
     * 操作失败时的提示文本
     */
    @ApiModelProperty("响应文本")
    private String message;
    /**
     * 操作成功时的提示文本
     */
    @ApiModelProperty("正确响应文本")
    private  T data;

    public static  JsonResult<Void> ok() {
        return ok(null);
    }
    public static <T> JsonResult <T> ok(Object data){
        JsonResult jsonResult=new JsonResult();
        jsonResult.state=ServiceCode.OK.getValue();
        jsonResult.data=data;
        return jsonResult;
    }

    public static JsonResult<Void> fail(ServiceCode serviceCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.state = serviceCode.getValue();
        jsonResult.message = message;
        return jsonResult;
    }

    public static JsonResult<Void> fail(ServiceException e){
        return fail(e.getServiceCode(),e.getMessage());
    }


}
