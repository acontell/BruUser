<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Bru User</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/libraries/bootstrap.css">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <h1>Bru User</h1>
        <h2>User</h2>
        <div class="alert alert-info alert-info-medium">
            <strong>Info!</strong> If the user name doesn't exit, the user will be created, otherwise they will get updated.
        </div>
        <jsp:include page="user_form/main.jsp" />
        <h2>List of users</h2>
        <jsp:include page="users_list/main.jsp" />
    </body>
    <script src='js/libraries/jquery.js'></script>
    <script src='js/libraries/bootstrap.js'></script>
    <script src='js/libraries/lodash.js'></script>
    <script  src="js/events.js"></script>
    <script  src="js/ajax.js"></script>
    <script  src="js/users_list.js"></script>
    <script  src="js/user_form.js"></script>
    <script  src="js/bus.js"></script>
</html>
