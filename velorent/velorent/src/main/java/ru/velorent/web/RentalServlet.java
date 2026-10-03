package ru.velorent.web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.velorent.ejb.BikeFacade;
import ru.velorent.ejb.RentalFacade;

/**
 * Контроллер для выдач: журнал выдач, выдача и возврат велосипеда.
 */
public class RentalServlet extends HttpServlet {

    @EJB
    private RentalFacade rentalFacade;

    @EJB
    private BikeFacade bikeFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("new".equals(request.getParameter("action"))) {
            request.setAttribute("freeBikes", bikeFacade.findFree());
            request.setAttribute("selectedBike", request.getParameter("bikeId"));
            request.getRequestDispatcher("/WEB-INF/views/rental-form.jsp").forward(request, response);
        } else {
            request.setAttribute("rentals", rentalFacade.findAll());
            request.getRequestDispatcher("/WEB-INF/views/rentals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("close".equals(action)) {
            rentalFacade.closeRental(Integer.valueOf(request.getParameter("id")));
            response.sendRedirect("rentals");
            return;
        }

        // оформление новой выдачи
        String name = request.getParameter("clientName");
        String phone = request.getParameter("clientPhone");
        String bikeId = request.getParameter("bikeId");
        if (name == null || name.isBlank() || phone == null || phone.isBlank() || bikeId == null) {
            request.setAttribute("error", "Заполните все поля формы");
            request.setAttribute("freeBikes", bikeFacade.findFree());
            request.getRequestDispatcher("/WEB-INF/views/rental-form.jsp").forward(request, response);
            return;
        }
        rentalFacade.openRental(Integer.valueOf(bikeId), name.trim(), phone.trim());
        response.sendRedirect("rentals");
    }
}
