package ru.velorent.web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.velorent.ejb.BikeFacade;
import ru.velorent.ejb.RentalFacade;
import ru.velorent.entity.Bike;

/**
 * Контроллер главной страницы: краткая сводка по пункту проката.
 */
public class MainServlet extends HttpServlet {

    @EJB
    private BikeFacade bikeFacade;

    @EJB
    private RentalFacade rentalFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("bikesTotal", bikeFacade.count());
        request.setAttribute("bikesFree", bikeFacade.countByStatus(Bike.FREE));
        request.setAttribute("bikesRented", bikeFacade.countByStatus(Bike.RENTED));
        request.setAttribute("bikesRepair", bikeFacade.countByStatus(Bike.REPAIR));
        request.setAttribute("income", rentalFacade.totalIncome());
        request.setAttribute("openRentals", rentalFacade.findOpen());
        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
}
