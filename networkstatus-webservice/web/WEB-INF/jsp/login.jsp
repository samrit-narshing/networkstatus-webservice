<html xmlns:th="http://www.thymeleaf.org" xmlns:tiles="http://www.thymeleaf.org">
    <head>
        <title tiles:fragment="title">Messages : Create</title>
        <link rel="stylesheet" href="${initParam.PROJECT_ROOT_PATH}/resources/style/styles.css" type="text/css" />
    </head>
    <body>

        <div id="body">

            <input type="hidden" value="SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759">

      
                <span>You are here:</span>
                <strong>Login Page</strong>
      


            <div id="content">

                <c:if test="${not empty pageError}">
                    ${pageError}
                </c:if>
                <c:if test="${not empty pageMessage}">
                    ${pageMessage}
                </c:if>
                <br/>
            
            
                <fieldset>
                    <legend>Please Login</legend>

                    <form name='loginForm'
                          th:action="@{/login}" method='POST'>

                        <!--                     <form name='loginForm'
                                                  action="<c:url value='/login' />" method='POST'>-->

                        <br/>
                        <p><label for="username">User:</label>

                            <input type="text" name="username" id="username" value=""/><br /></p>	

                        <p><label for="password">Password:</label>
                            <input type="password" name="password" id="password" value=""/><br /></p>

                        <p><input name="send" class="formbutton" value="Login" type="submit" /></p>

                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />


                    </form>

                </fieldset>

                <br/><br/><br/><br/>
            </div>

        </div>

    </body>
</html>