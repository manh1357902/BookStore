package come.example.bookstore.dto.requests;

import come.example.bookstore.models.enums.GenderEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @NotEmpty(message = "username is required.")
    private String username;
    @NotEmpty(message = "password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;
    @NotEmpty(message = "FullName is required.")
    private String fullName;
    @NotEmpty(message = "Phone is required.")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Invalid phone number format")
    private String phone;
    private GenderEnum gender;
    private String address;
    private Date dob;
}
