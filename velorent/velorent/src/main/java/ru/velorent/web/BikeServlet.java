package ru.velorent.web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import ru.velorent.ejb.BikeFacade;
import ru.velorent.ejb.RentalFacade;
import ru.velorent.entity.Bike;

/**
 * Контроллер для велосипедов: список, просмотр, добавление и редактирование.
 * Действие выбирается параметром action.
 */
public class BikeServlet extends HttpServlet {

    private static final String VIEWS = "/WEB-INF/views/";

    @EJB
    private BikeFacade bikeFacade;

    @EJB
    private RentalFacade rentalFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "view": {
                Bike bike = findBike(request);
                if (bike == null) {
                    response.sendRedirect("bikes");
                    return;
                }
                request.setAttribute("bike", bike);
                request.setAttribute("rentals", rentalFacade.findByBike(bike.getId()));
                forward(request, response, "bike-view.jsp");
                break;
            }
            case "add":
                request.setAttribute("bike", new Bike());
                forward(request, response, "bike-form.jsp");
                break;
            case "edit": {
                Bike bike = findBike(request);
                if (bike == null) {
                    response.sendRedirect("bikes");
                    return;
                }
                request.setAttribute("bike", bike);
                forward(request, response, "bike-form.jsp");
                break;
            }
            default:
                request.setAttribute("bikes", bikeFacade.findAll());
                forward(request, response, "bikes.jsp");
        }
    }

    // сохранение данных из формы (новый велосипед или изменение старого)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        Bike bike;
        if (idParam == null || idParam.isEmpty()) {
            bike = new Bike();
        } else {
            bike = bikeFacade.find(Integer.valueOf(idParam));
        }

        bike.setInvNumber(trim(request.getParameter("invNumber")));
        bike.setModel(trim(request.getParameter("model")));
        bike.setBikeType(trim(request.getParameter("bikeType")));
        bike.setFrameSize(trim(request.getParameter("frameSize")));
        bike.setStatus(request.getParameter("status"));

        String error = null;
        try {
            BigDecimal price = new BigDecimal(request.getParameter("pricePerHour").replace(',', '.'))
                    .setScale(2, RoundingMode.HALF_UP);
            if (price.signum() <= 0) {
                error = "Цена должна быть больше нуля";
            }
            bike.setPricePerHour(price);
        } catch (NumberFormatException | NullPointerException e) {
            error = "Цена указана неверно";
        }
        if (bike.getInvNumber().isEmpty() || bike.getModel().isEmpty()) {
            error = "Заполните инвентарный номер и модель";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("bike", bike);
            forward(request, response, "bike-form.jsp");
            return;
        }

        if (bike.getId() == null) {
            bikeFacade.create(bike);
        } else {
            bikeFacade.edit(bike);
        }
        response.sendRedirect("bikes");
    }

    private Bike findBike(HttpServletRequest request) {
        try {
            return bikeFacade.find(Integer.valueOf(request.getParameter("id")));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEWS + page).forward(request, response);
    }
}
