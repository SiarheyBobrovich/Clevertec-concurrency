package ru.clevertec.concurrency.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Babrovich Siarhey on 24.03.2023
 */
@Builder
@Getter
@ToString
public class Request {
    private final int index;
}
