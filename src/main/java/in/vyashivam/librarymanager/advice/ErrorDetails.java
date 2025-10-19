package in.vyashivam.librarymanager.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "message='" + message + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
