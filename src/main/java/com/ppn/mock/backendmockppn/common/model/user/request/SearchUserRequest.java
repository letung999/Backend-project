package com.ppn.mock.backendmockppn.common.model.user.request;

import com.ppn.mock.backendmockppn.common.BaseSearchRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SearchUserRequest extends BaseSearchRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String age;
    private String phoneNumber;
    private String status;

    public String buildCacheKey(String keyPrefix) {
        StringBuilder builder = new StringBuilder(keyPrefix);
        if (firstName != null) {
            builder.append("-")
                    .append(firstName.toLowerCase());
        }
        if (lastName != null) {
            builder.append("-")
                    .append(lastName.toLowerCase());
        }
        if (email != null) {
            builder.append("-")
                    .append(email.toLowerCase());
        }
        if (gender != null) {
            builder.append("-")
                    .append(gender.toLowerCase());
        }
        if (age != null) {
            builder.append("-")
                    .append(age);
        }
        if (phoneNumber != null) {
            builder.append("-")
                    .append(phoneNumber);
        }
        if (status != null) {
            builder.append("-")
                    .append(status);
        }
        return builder.toString();
    }
}
