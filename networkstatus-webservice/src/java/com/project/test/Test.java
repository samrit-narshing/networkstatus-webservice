/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.test;

import com.project.core.util.StringAndNumberUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.lucene.search.highlight.SimpleHTMLEncoder;


import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
 
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
/**
 *
 * @author samri
 */
public class Test {

    public static void main(String[] args) throws Exception {

        String[] nameArray = getListOfString().toArray(new String[getListOfString().size()]);
        System.out.println(nameArray[2]);
    }
    
    private static List<String> getListOfString()
    {
        //New Line Added Again
        //New Line Added
        //List Added
        List<String> tokenKeys = new ArrayList<>();
        tokenKeys.add("One");
        tokenKeys.add("Two");
        tokenKeys.add("Three");
        return tokenKeys;
        
    }
    

    private static String htmlEncode(final String string) {
        final StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            final Character character = string.charAt(i);
            if (CharUtils.isAscii(character)) {

                // Encode common HTML equivalent characters
                stringBuffer.append(
                        StringEscapeUtils.escapeHtml4(character.toString()));
            } else {
                // Why isn't this done in escapeHtml4()?
                stringBuffer.append(
                        String.format("&#x%x;",
                                Character.codePointAt(string, i)));
            }
        }
        return stringBuffer.toString();
    }
}
