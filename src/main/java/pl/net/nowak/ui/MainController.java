package pl.net.nowak.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Locale;

/**
 * Created by mno
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String root(Locale locale) {
        return "redirect:/login.html";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal,Model model) {
        if(principal != null ) {
            model.addAttribute("view","shared/dashboard.html");
            return "index.html";
        }else {
            model.addAttribute("loginError", true);
            return "login.html";
        }
    }

    /** Home page. */
    @RequestMapping("/index.html")
    public String index(Model model) {

        model.addAttribute("view","shared/dashboard.html");
        return "index.html";
    }

    /** Login form. */
    @RequestMapping("/login.html")
    public String login() {
        return "login.html";
    }

    /** Simulation of an exception. */
    @RequestMapping("/simulateError.html")
    public void simulateError() {
        throw new RuntimeException("This is a simulated error message");
    }

    /** Error page. */
    @RequestMapping("/error.html")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<ul>");
        while (throwable != null) {
            errorMessage.append("<li>").append(escapeTags(throwable.getMessage())).append("</li>");
            throwable = throwable.getCause();
        }
        errorMessage.append("</ul>");
        model.addAttribute("errorMessage", errorMessage.toString());
        return "error.html";
    }

    /** Substitute 'less than' and 'greater than' symbols by its HTML entities. */
    private String escapeTags(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
