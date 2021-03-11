<%-- 
    Document   : view
    Created on : May 23, 2016, 4:53:08 PM
    Author     : Samrit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <strong>Upload License</strong>
            </div>
            <div id="content">
                
                <!--Div Content Start-->
                <c:if test="${not empty pageError}">
                    <div id="pageError">${pageError}</div>
                </c:if>
                <c:if test="${not empty pageMessage}">
                    <div id="pageMessage">${pageMessage}</div>
                </c:if>
                
                <br/>
                <br/>
                <!--                    <h2>Form</h2>-->
                <fieldset>
                    <legend> Upload License </legend>
                    <br/>


                    <form  name="form1" method="post" action="/semms-webservice/core/license/submitUploadLicenseKey"  enctype="multipart/form-data">
                        <p>
                            <label for="searchText">Upload Valid License Key : </label>
                            &nbsp;&nbsp;<input name="file" type="file" id="keyfile" style="width: 440px"/>
                            <br/>
                        <p><input name="send" class="formbutton" value="Upload" type="submit" onClick="return confirmSubmit_Upload()"/></p>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    </form>
                </fieldset>

                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
    </body>
</html>
