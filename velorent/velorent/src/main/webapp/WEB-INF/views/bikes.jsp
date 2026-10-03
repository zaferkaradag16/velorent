<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Велосипеды"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1>Велосипеды</h1>
<p><a class="btn" href="bikes?action=add">+ Добавить велосипед</a></p>

<table>
    <tr>
        <th>Инв. №</th><th>Модель</th><th>Тип</th><th>Рама</th>
        <th>Цена за час, руб.</th><th>Состояние</th><th></th>
    </tr>
    <c:forEach var="b" items="${bikes}">
        <tr>
            <td>${b.invNumber}</td>
            <td><a href="bikes?action=view&id=${b.id}">${b.model}</a></td>
            <td>${b.bikeType}</td>
            <td>${b.frameSize}</td>
            <td class="right">${b.pricePerHour}</td>
            <td><span class="status ${b.status}">${b.statusName}</span></td>
            <td><a href="bikes?action=edit&id=${b.id}">изменить</a></td>
        </tr>
    </c:forEach>
</table>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
