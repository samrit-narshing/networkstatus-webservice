/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.*;

/**
 *
 * @author samri
 */
class Personx implements Comparable<Personx> {

    private String firstName, lastName;
    private Integer number;

    public Personx(String firstName, String lastName, Integer number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public Integer getNumber() {
        return number;
    }

    public int compareTo(Personx p) {
        return number.compareTo(p.number);

    }
}

class FirstNameComparator implements Comparator<Personx> {

    public int compare(Personx p1, Personx p2) {
//        return p1.getFirstName().compareTo(p2.getFirstName());
        
        return p1.getNumber().compareTo(p2.getNumber());
    }
}

public class Person {

    public static void log(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Set<Personx> people = new HashSet<Personx>();
        people.add(new Personx("Bob", "Jones", 3));
        people.add(new Personx("Alice", "Yetti", 4));
        people.add(new Personx("Wlice", "Yetti", 1));
        people.add(new Personx("Zice", "Yetti", 100));
        people.add(new Personx("Xlice", "Yetti", 23));

//        log("Sorted list:");
//        List<Personx> peopleList = new LinkedList<Personx>();
//        peopleList.addAll(people);
//        Collections.<Personx>sort(peopleList);
//        for (Personx p : peopleList) {
//            log(p.getName());
//            log(p.getNumber().toString());
//            log("");
//        }
//
//        log("TreeSet:");
//        TreeSet<Personx> treeSet = new TreeSet<Personx>();
//        treeSet.addAll(people);
//        for (Personx p : treeSet) {
//            log(p.getName());
//            log(p.getNumber().toString());
//            log("");
//        }

        log("TreeSet (custom sort):");
        TreeSet<Personx> treeSet2 = new TreeSet<Personx>(new FirstNameComparator());
        treeSet2.addAll(people);
        for (Personx p : treeSet2) {
            log(p.getName());
            log(p.getNumber().toString());
            log("");
        }
        
        Set<Personx> peopls = treeSet2;
        
        for (Personx p : treeSet2) {
            log(p.getName());
            log(p.getNumber().toString());
            log("");
        }
    }
}
