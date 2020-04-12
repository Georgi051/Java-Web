package web;

import services.CarsService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class CreateServlet extends HttpServlet {
    private final CarsService carsService;


    @Inject
    public CreateServlet(CarsService carsService) {
        this.carsService = carsService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/car-create.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
         String brand = req.getParameter("brand");
         String model = req.getParameter("model");
         String year = req.getParameter("year");
         String engine = req.getParameter("engine");
        this.carsService.createCar(brand,model,year,engine);
        resp.sendRedirect("/cars/all");
    }
}
