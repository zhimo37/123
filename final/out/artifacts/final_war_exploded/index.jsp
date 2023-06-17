<%--
  Created by IntelliJ IDEA.
  User: ������
  Date: 2023/6/16
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Simple Calculator</title>
  <style>
    body {
      background-image: url('infinity-4874929.jpg');
    }
  </style>
</head>
<body>
<h1 align="center">Calculator</h1>
<form method="post" action="">
  <div style="text-align: center;">
  <select name="operator">
    <option value="A">表达式求值</option>
    <option value="B">堆排序</option>
    <option value="C">KMP</option>
  </select>
  <br><br>
  <input type="text" name="num1" >
  <br><br>
  <input type="text" name="num2">
  <br><br>
  <input type="submit" value="Calculate">
  <br>
  </div>
  <% if (request.getParameter("num1") != null &&
        request.getParameter("num2") != null &&
        request.getParameter("operator") != null) {
  try {
    String result = com.bjpower.CalculatorService.calculate(request.getParameter("num1"),
            request.getParameter("num2"),
            request.getParameter("operator"));
    out.println("Result: " + result);
  } catch (NumberFormatException e) {
    out.println("Invalid input");
  }
  }
  %>
</form>
</body>
</html>