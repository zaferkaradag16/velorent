<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${empty bike.id ? 'Новый велосипед' : 'Редактирование'}"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1>${empty bike.id ? 'Новый велосипед' : 'Редактирование велосипеда'}</h1>

<c:if test="${not empty error}">
    <p class="msg error">${error}</p>
</c:if>

<form class="form" method="post" action="bikes">
    <input type="hidden" name="id" value="${bike.id}">

    <label>Инвентарный номер
        <input type="text" name="invNumber" maxlength="10" value="${bike.invNumber}" required>
    </label>
    <label>Модель
        <input type="text" name="model" maxlength="60" value="${bike.model}" required>
    </label>
    <label>Тип
        <select name="bikeType">
            <c:forEach var="t" items="${['Городской', 'Горный', 'Шоссейный', 'Складной', 'Детский']}">
                <option ${t == bike.bikeType ? 'selected' : ''}>${t}</option>
            </c:forEach>
        </select>
    </label>
    <label>Размер рамы
        <select name="frameSize">
            <c:forEach var="s" items="${['XS', 'S', 'M', 'L', 'XL']}">
                <option ${s == bike.frameSize ? 'selected' : ''}>${s}</option>
            </c:forEach>
        </select>
    </label>
    <label>Цена за час, руб.
        <input type="text" name="pricePerHour" value="${bike.pricePerHour}" required>
    </label>
    <label>Состояние
        <select name="status">
            <option value="FREE" ${bike.status == 'FREE' ? 'selected' : ''}>Свободен</option>
            <option value="RENTED" ${bike.status == 'RENTED' ? 'selected' : ''}>Выдан</option>
            <option value="REPAIR" ${bike.status == 'REPAIR' ? 'selected' : ''}>В ремонте</option>
        </select>
    </label>

    <div class="buttons">
        <button type="submit">Сохранить</button>
        <a href="bikes">Отмена</a>
    </div>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
