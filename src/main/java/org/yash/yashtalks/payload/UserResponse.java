package org.yash.yashtalks.payload;

import lombok.*;
import org.yash.yashtalks.entity.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private User user;
    private Boolean followedByAuthUser;
}
