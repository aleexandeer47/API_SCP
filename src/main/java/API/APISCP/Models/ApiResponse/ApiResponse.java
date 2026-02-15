package API.APISCP.Models.ApiResponse;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T data;
    private String timestamp;



    public ApiResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operación exitosa", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) { return new ApiResponse<>(true, message, data);
    }

    public static ApiResponse<?> error(String message) {

        return new ApiResponse<>(false, message, null);
    }

    public ApiResponse<?> error(String message, T data){return new ApiResponse<>(true, message, data);}


}
