package com.atanava.parser;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class Application {

    public static void main(String[] args) {

        try (InputStream input = new FileInputStream("src/main/resources/app.properties")) {
            Date start = new Date();

            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("url");
            String output = props.getProperty("output");

            Reader<String, ArrayNode> reader = new UrlToJsonReader();
            Writer<ArrayNode, Boolean> writer = new JsonToCsvWriter(output);

            boolean success = writer.write(reader.read(url));

            Date end = new Date();

            System.out.printf("Parsed successful = %b.\nParsing time = %d millis.\nSee result in file: %s",
                    success, (end.getTime() - start.getTime()), output);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
