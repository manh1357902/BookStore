package come.example.bookstore.dto.responses;

import come.example.bookstore.models.entities.Role;
import come.example.bookstore.models.enums.GenderEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String fullName;
    private String phone;
    private GenderEnum gender;
    private String address;
    private Date dob;
    private List<RoleResponse> roles;
}
