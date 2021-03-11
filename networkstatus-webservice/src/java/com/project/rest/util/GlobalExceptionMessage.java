/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util;

/**
 *
 * @author samri
 */
public class GlobalExceptionMessage {

    public static String FORBIDDEN_MESSAGE = "Unauthorized ,Forbidden or Restricted Resouces.";
    public static int FORBIDDEN_APPLICATION_CODE = 1403;

    public static String NOTFOUND_MESSAGE = "Resouces not Found.";
    public static int NOTFOUND_APPLICATION_CODE = 1404;

    public static String BADREQUST_MESSAGE = "Bad Request.";
    public static int BADREQUEST_APPLICATION_CODE = 1400;

    public static String CONFLICT_MESSAGE = "Duplicate or Conflic Resource.";
    public static int CONFLICT_APPLICATION_CODE = 1409;

    public static String INTERNALSERVERERROR_MESSAGE = "Internal Server Error.";
    public static int INTERNALSERVERERROR_APPLICATION_CODE = 1500;

}
