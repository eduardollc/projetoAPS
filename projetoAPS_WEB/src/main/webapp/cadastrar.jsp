<%-- 
    Document   : cadastrar
    Created on : 29 de ago. de 2026, 00:55:31
    Author     : eduardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%

            String name, email, telefone, senha, cpf, cnh;

            name = request.getParameter("name");
            email = request.getParameter("email");
            telefone = request.getParameter("telefone");
            senha = request.getParameter("senha");
            cpf = request.getParameter("cpf");
            cnh = request.getParameter("cnh");
            



        %>



    </body>
</html>
