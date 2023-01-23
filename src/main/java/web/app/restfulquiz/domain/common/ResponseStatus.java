package web.app.restfulquiz.domain.common;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class ResponseStatus {

    private boolean success;
    private String message;

}