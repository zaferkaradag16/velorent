<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Велосипед ${bike.invNumber}"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1>${bike.model}</h1>

<table class="info">
    <tr><th>Инвентарный номер</th><td>${bike.invNumber}</td></tr>
    <tr><th>Тип</th><td>${bike.bikeType}</td></tr>
    <tr><th>Размер рамы</th><td>${bike.frameSize}</td></tr>
    <tr><th>Цена за час</th><td>${bike.pricePerHour} руб.</td></tr>
    <tr><th>Состояние</th><td><span class="status ${bike.status}">${bike.statusName}</span></td></tr>
</table>

<p>
    <a class="btn" href="bikes?action=edit&id=${bike.id}">Редактировать</a>
    <c:if test="${bike.status == 'FREE'}">
        <a class="btn" href="rentals?action=new&bikeId=${bike.id}">Выдать клиенту</a>
    </c:if>
    <a href="bikes">к списку</a>
</p>

<h2>История выдач</h2>
<c:choose>
    <c:when test="${empty rentals}">
        <p>Этот велосипед еще ни разу не выдавался.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr><th>Клиент</th><th>Выдан</th><th>Возвращен</th><th>Сумма, руб.</th></tr>
            <c:forEach var="r" items="${rentals}">
                <tr>
                    <td>${r.clientName}</td>
                    <td>${r.startText}</td>
                    <td>${r.endText}</td>
                    <td class="right">${r.open ? '—' : r.totalCost}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
