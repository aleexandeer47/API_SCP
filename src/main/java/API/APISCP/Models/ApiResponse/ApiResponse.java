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



    public ApiResponse(Boolean success, String message, T data, String timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
    }

    //AUN NO TERMINADO


}
