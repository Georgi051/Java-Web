package app.web.servlets;

import app.domain.models.binding.CarCreateModel;
import app.service.CarService;
import app.util.FileUtil;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {

    private static final String CREATE_PATH = "D:\\SoftUni\\web\\introtojavaEE\\src\\main\\webapp\\views\\create.html";
    private final FileUtil fileUtil;
    private final CarService carService;
    private final ModelMapper modelMapper;

    @Inject
    public CreateServlet(FileUtil fileUtil, CarService carService, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String html = this.fileUtil.readFile(CREATE_PATH);
        resp.getWriter().println(html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CarCreateModel carCreateModel = new CarCreateModel();
        carCreateModel.setBrand(req.getParameter("brand"));
        carCreateModel.setModel(req.getParameter("model"));
        carCreateModel.setYear(Integer.parseInt(req.getParameter("year")));
        carCreateModel.setEngine(req.getParameter("engine"));
        this.carService.createCar(this.modelMapper.map(carCreateModel, CarCreateModel.class));
        resp.sendRedirect("/all");
    }
}
