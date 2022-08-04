package hyjung.shop_management.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean isSuccess;
    private int statusCode;
    private T data;
    private String message;
}