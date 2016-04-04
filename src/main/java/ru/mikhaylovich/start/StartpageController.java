package ru.mikhaylovich.start;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by mhty on 03.04.16.
 */

@Controller
public class StartpageController {
    private static final String OAUTH_CLIENT_ID = "4cf40771cd091de1955a";

    private static final String OAUTH_CLIENT_SECRET = "851ec963380032f473d1d1b9547cfeed86e86ee0";


    @RequestMapping("/reg")
    public RedirectView regAuth(Model model) {
        OAuth20Service service = new ServiceBuilder()
                .apiKey(OAUTH_CLIENT_ID)
                .apiSecret(OAUTH_CLIENT_SECRET)
                .callback("http://spring.mikhaylovich.com/start")
                .build(GitHubApi.instance());

        String authUrl = service.getAuthorizationUrl();
        return new RedirectView(authUrl);
    }

    @RequestMapping("/start")
    public RedirectView getCode(RedirectAttributes redirectAttributes, @RequestParam("code") String code) {
        OAuth20Service service = new ServiceBuilder()
                .apiKey(OAUTH_CLIENT_ID)
                .apiSecret(OAUTH_CLIENT_SECRET)
                .callback("http://spring.mikhaylovich.com/start")
                .build(GitHubApi.instance());

        OAuth2AccessToken accessToken = service.getAccessToken(code);
//        final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.hh.ru/me", service);
//        service.signRequest(accessToken, request);
//        final Response response = request.send();
//        redirectAttributes.addAttribute("name", response.getBody());
        redirectAttributes.addAttribute("name", code);

        //OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.hh.ru/me", service);
        return new RedirectView("/greeting");
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


}
