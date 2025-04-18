package api.giybat.uz.dto;

import api.giybat.uz.enums.AppLanguage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(T data, AppLanguage language) {
        this.data = data;
    }

    public ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> ok(T data, AppLanguage language) {
        return new ApiResponse<>(data);
    }
}
