package org.bardframework.commons.utils.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class HttpCallResult {

    private final int responseCode;
    private final byte[] body;
    private final boolean error;
}
