<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Love My CRM</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="text-center mb-4">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Love My CRM Logo" class="crm-logo">
    </div>
    <h1 class="mb-4 text-center">Bienvenido a Love My CRM</h1>
    <div class="list-group">
        <a href="${pageContext.request.contextPath}/empresas?action=list" class="list-group-item list-group-item-action">Gestionar Empresas</a>
        <a href="${pageContext.request.contextPath}/usuarios?action=list" class="list-group-item list-group-item-action">Gestionar Usuarios</a>
        <a href="${pageContext.request.contextPath}/clientes?action=list" class="list-group-item list-group-item-action">Gestionar Clientes</a>
        <a href="${pageContext.request.contextPath}/proveedores?action=list" class="list-group-item list-group-item-action">Gestionar Proveedores</a>
        <a href="${pageContext.request.contextPath}/productos?action=list" class="list-group-item list-group-item-action">Gestionar Productos</a>
        <a href="${pageContext.request.contextPath}/ventas?action=list" class="list-group-item list-group-item-action">Gestionar Ventas</a>
        <a href="${pageContext.request.contextPath}/compras?action=list" class="list-group-item list-group-item-action">Gestionar Compras</a>
        <a href="${pageContext.request.contextPath}/envios?action=list" class="list-group-item list-group-item-action">Gestionar Envíos</a>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>