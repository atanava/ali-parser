package com.atanava.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;

public class JsonToCsvWriter implements Writer<ArrayNode, Boolean> {

    private final String output;

    public JsonToCsvWriter(String output) {
        this.output = output;
    }

    @Override
    public Boolean write(ArrayNode input) throws IOException {
        CsvSchema.Builder builder = CsvSchema.builder();
        builder.setUseHeader(true);

        input.elements()
                .next()
                .fieldNames()
                .forEachRemaining(builder::addColumn);

        CsvSchema csvSchema = builder.build();

        new CsvMapper().writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File(output), input);

        return true;
    }
}
