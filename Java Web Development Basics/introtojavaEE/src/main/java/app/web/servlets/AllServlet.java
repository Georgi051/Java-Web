package app.web.servlets;

import app.domain.models.view.CarViewModel;
import app.service.CarService;
import app.util.FileUtil;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/all")
public class AllServlet extends HttpServlet {
    private final static String ALL_PATH = "D:\\SoftUni\\web\\introtojavaEE\\src\\main\\webapp\\views\\all.html";
    private final FileUtil fileUtil;
    private final CarService carService;
    private final ModelMapper modelMapper;

    @Inject
    public AllServlet(FileUtil fileUtil, CarService carService, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String html = this.fileUtil.readFile(ALL_PATH);
        StringBuilder sb = new StringBuilder();
        List<CarViewModel> carViewModelList = this.carService.allCars().stream()
                .map(c -> this.modelMapper.map(c,CarViewModel.class))
                .collect(Collectors.toList());

        for (CarViewModel car : carViewModelList) {
            sb.append(String.format("<li class=\"d-flex justify-content-around\">\n" +
                    "<div class=\"col-md-4 d-flex flex-column text-center mb-3\">\n" +
                    "<h2 class=\"text-white text-center\">Brand: %s</h2>\n" +
                    "<h4 class=\"text-white text-center\">Model: %s</h4>\n" +
                    "<h4 class=\"text-white text-center\">Year: %d</h4>\n" +
                    "<h4 class=\"text-white text-center\">Engine: %s</h4>\n" +
                    "</div>\n"+
                    "</li>",car.getBrand(),car.getModel(),car.getYear(),car.getEngine()));
        }
        html = html.replace("{{replace}}",sb.toString());
        resp.getWriter().println(html);
    }
}
