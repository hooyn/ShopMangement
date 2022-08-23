package hyjung.shop_management.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    @ApiModelProperty(example = "true, false")
    private boolean isSuccess;

    @ApiModelProperty(example = "Http Status Code")
    private int statusCode;

    @ApiModelProperty(example = "Data")
    private T data;

    @ApiModelProperty(example = "Message")
    private String message;
}