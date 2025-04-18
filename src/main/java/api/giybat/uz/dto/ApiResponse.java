package api.giybat.uz.dto;

import api.giybat.uz.enums.AppLanguage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private String message;


    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(T data, AppLanguage language) {
        this.data = data;
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> ok(T data, AppLanguage language) {
        return new ApiResponse<>(data);
    }

}
