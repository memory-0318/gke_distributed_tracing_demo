package org.example.model;

import lombok.*;

/**
 * @author Brian Su <brian.su@tpisoftware.com>
 * @description:
 * @date: 2021/6/23
 */
@Data
@Setter(value = AccessLevel.NONE)
@Builder(setterPrefix = "set", toBuilder = true)
public class ResponseWrapper<T> {
    private boolean success;
    private T data;

    @NoArgsConstructor
    public static class NoArgs { }
}
