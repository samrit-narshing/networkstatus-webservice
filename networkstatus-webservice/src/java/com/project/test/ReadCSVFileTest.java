/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.test;

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
import com.project.core.util.CsvParserUtil;

/**
 *
 * @author samri_g64pbd3
 */
public class ReadCSVFileTest {

//    final static String fileName = "C:\\Users\\samri_g64pbd3\\Desktop\\Work\\jars\\Master Table File.csv";
    
     final static String fileName = "D:\\tempfile\\server\\csv\\admin.csv";

    public static void main(String[] args) throws IOException, CsvException {

//        readAll();
//        System.out.println("\n\n\n>>>>>>>>>>>>>>>>>>>>>>NEXT<<<<<<<<<<<<<<<<<<<<<\n\n\n");
//        readByLine();
//         System.out.println("\n\n\n>>>>>>>>>>>>>>>>>>>>>>NEXT<<<<<<<<<<<<<<<<<<<<<\n\n\n");
//        readAllSkipFirstLine();
//        readToObject();
        processObject();
                
    }
    
    public static void processObject() throws IOException, CsvException
    {
        List<NetworkCSVObject> networkCSVObjects = new CsvParserUtil().readNetworkCSVObject(fileName);
        networkCSVObjects.forEach(x->System.out.println(x.getHostName()+"-"+x.getIpAddress()));
    }

    public static void readAll() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
        }
    }

    public static void readByLine() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                System.out.println(lineInArray[0] + " " + lineInArray[1] + " " + "etc...");
            }
        }
    }

    public static void readAllSkipFirstLine() throws IOException, CsvException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(fileName))
                .withCSVParser(csvParser) // custom CSV parser
                .withSkipLines(1) // skip the first line, header info
                .build()) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
        }
    }

    public static void readToObject() throws IOException, CsvException {

        List<NetworkCSVObject> beans = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(NetworkCSVObject.class)
                .build()
                .parse();

//        beans.forEach(System.out::println);

        beans.forEach((b) -> {
            System.out.println(b.getHostName() + " -- " + b.getIpAddress());
        });
    }

}
