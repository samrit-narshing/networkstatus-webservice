/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import com.project.test.NetworkCSVObject;

/**
 *
 * @author samri_g64pbd3
 */
public class CsvParserUtil {

    public List<NetworkCSVObject> readNetworkCSVObject(String csvFilePath) throws IOException, CsvException {

        List<NetworkCSVObject> networkCSVObjects = new CsvToBeanBuilder(new FileReader(csvFilePath))
                .withType(NetworkCSVObject.class)
                .build()
                .parse();

//        beans.forEach(System.out::println);
//        beans.forEach((b) -> {
//            System.out.println(b.getHostName() + " -- " + b.getIpAddress());
//        });
        return networkCSVObjects;
    }

}
