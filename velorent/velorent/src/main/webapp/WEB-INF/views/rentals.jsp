<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Журнал выдач"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1>Журнал выдач</h1>
<p><a class="btn" href="rentals?action=new">+ Выдать велосипед</a></p>

<table class="journal">
    <tr>
        <th>№</th><th>Велосипед</th><th>Клиент</th><th>Телефон</th>
        <th>Выдан</th><th>Возвращен</th><th>Сумма, руб.</th><th></th>
    </tr>
    <c:forEach var="r" items="${rentals}">
        <tr class="${r.open ? 'open' : ''}">
            <td>${r.id}</td>
            <td><a href="bikes?action=view&id=${r.bike.id}">${r.bike.invNumber}</a></td>
            <td>${r.clientName}</td>
            <td>${r.clientPhone}</td>
            <td>${r.startText}</td>
            <td>${r.endText}</td>
            <td class="right">${r.open ? '—' : r.totalCost}</td>
            <td>
                <c:if test="${r.open}">
                    <form method="post" action="rentals" class="inline">
                        <input type="hidden" name="action" value="close">
                        <input type="hidden" name="id" value="${r.id}">
                        <button type="submit">Принять возврат</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
