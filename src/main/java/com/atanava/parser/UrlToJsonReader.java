package com.atanava.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class UrlToJsonReader implements Reader<String, ArrayNode> {

    @Override
    public ArrayNode read(String strUrl) throws IOException {
        ArrayNode allResults = new ArrayNode(null);
        ObjectMapper mapper = new ObjectMapper();

        int offset = 0;
        String postback = "";
        do {
            JsonNode node = mapper.readTree(urlToJsonStringReader.read(strUrl + "&offset=" + offset + "&postback=" + postback));

            ArrayNode results = node.withArray("results");
            allResults.addAll(results);

            offset += 10;
            postback = node.get("postback") == null ? postback : node.get("postback").asText();

        } while (offset <= 90);

        return allResults;
    }

    private final Reader<String, String> urlToJsonStringReader = strUrl -> {
        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    };

}
