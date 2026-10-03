<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Главная"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1>Пункт проката велосипедов</h1>
<p>Сводка по велосипедам и текущим выдачам.</p>

<div class="cards">
    <div class="card"><span class="num">${bikesTotal}</span>всего велосипедов</div>
    <div class="card free"><span class="num">${bikesFree}</span>свободно</div>
    <div class="card rented"><span class="num">${bikesRented}</span>выдано</div>
    <div class="card repair"><span class="num">${bikesRepair}</span>в ремонте</div>
    <div class="card"><span class="num">${income}</span>выручка, руб.</div>
</div>

<h2>Сейчас на руках</h2>
<c:choose>
    <c:when test="${empty openRentals}">
        <p>Все велосипеды на месте.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr><th>Велосипед</th><th>Клиент</th><th>Телефон</th><th>Выдан</th></tr>
            <c:forEach var="r" items="${openRentals}">
                <tr>
                    <td><a href="bikes?action=view&id=${r.bike.id}">${r.bike.invNumber}</a> ${r.bike.model}</td>
                    <td>${r.clientName}</td>
                    <td>${r.clientPhone}</td>
                    <td>${r.startText}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
