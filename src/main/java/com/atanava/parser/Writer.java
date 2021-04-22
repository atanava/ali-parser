package com.atanava.parser;

import java.io.IOException;

@FunctionalInterface
public interface Writer<I, O> {
    O write(I input) throws IOException;
}
