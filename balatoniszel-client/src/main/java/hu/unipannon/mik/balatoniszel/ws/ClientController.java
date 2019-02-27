package hu.unipannon.mik.balatoniszel.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ClientController {
    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    private final BalatoniSzel client;

    @Autowired
    public ClientController(BalatoniSzel client) {
        this.client = client;
    }

    @GetMapping(path = "/")
    public ModelAndView index() {
        ModelAndView      indexView = new ModelAndView("index");
        List<Reservation> reservationList = client.reservations();
        if(reservationList != null) {
            LOG.info("Reservations: {}", reservationList.size());
        }
        indexView.addObject("reservations", reservationList);
        return indexView;
    }

    @PostMapping(path="/reserve")
    public ModelAndView reserve(@RequestParam(name = "startDate") String startDate,
                                @RequestParam(name = "endDate") String endDate,
                                @RequestParam(name = "numberOfBeds") int numberOfBeds,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "address") String address,
                                @RequestParam(name = "document") String document,
                                @RequestParam(name = "email") String email) {
        client.reserve(startDate, endDate, numberOfBeds, name, document, address, email);
        return new ModelAndView("redirect:/");
    }

}
