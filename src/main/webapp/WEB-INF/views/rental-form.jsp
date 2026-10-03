<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Выдача велосипеда"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1>Выдача велосипеда</h1>

<c:if test="${not empty error}">
    <p class="msg error">${error}</p>
</c:if>

<c:choose>
    <c:when test="${empty freeBikes}">
        <p class="msg">Свободных велосипедов сейчас нет.</p>
    </c:when>
    <c:otherwise>
        <form class="form" method="post" action="rentals">
            <label>Велосипед
                <select name="bikeId">
                    <c:forEach var="b" items="${freeBikes}">
                        <option value="${b.id}" ${b.id == selectedBike ? 'selected' : ''}>
                            ${b.invNumber} — ${b.model} (${b.pricePerHour} руб./ч)
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label>ФИО клиента
                <input type="text" name="clientName" maxlength="80" required>
            </label>
            <label>Телефон
                <input type="text" name="clientPhone" maxlength="20" placeholder="+7 900 000-00-00" required>
            </label>
            <div class="buttons">
                <button type="submit">Оформить выдачу</button>
                <a href="rentals">Отмена</a>
            </div>
        </form>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
