package com.atanava.parser;

import java.io.IOException;

@FunctionalInterface
public interface Reader<I, O> {
    O read(I input) throws IOException;
}
