<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tic-Tac-Toe</title>
    <link href="static/main.css" rel="stylesheet">
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
</head>
<body>
<h1>Tic-Tac-Toe</h1>

<table>
    <tr>
        <td onclick="window.location='/logic?click=0'">${sessionScope.data.get(0).getSign()}</td>
        <td onclick="window.location='/logic?click=1'">${sessionScope.data.get(1).getSign()}</td>
        <td onclick="window.location='/logic?click=2'">${sessionScope.data.get(2).getSign()}</td>
    </tr>
    <tr>
        <td onclick="window.location='/logic?click=3'">${sessionScope.data.get(3).getSign()}</td>
        <td onclick="window.location='/logic?click=4'">${sessionScope.data.get(4).getSign()}</td>
        <td onclick="window.location='/logic?click=5'">${sessionScope.data.get(5).getSign()}</td>
    </tr>
    <tr>
        <td onclick="window.location='/logic?click=6'">${sessionScope.data.get(6).getSign()}</td>
        <td onclick="window.location='/logic?click=7'">${sessionScope.data.get(7).getSign()}</td>
        <td onclick="window.location='/logic?click=8'">${sessionScope.data.get(8).getSign()}</td>
    </tr>
</table>


<script>

</script>

</body>
</html>