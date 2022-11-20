package org.yash.yashtalks.payload;

import lombok.*;
import org.yash.yashtalks.entity.Comment;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Comment comment;
}
