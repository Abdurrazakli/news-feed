package newsApp.controllers.newsController;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.NewsNotFound;
import newsApp.models.newsModel.News;
import newsApp.models.userModels.NUserDetails;
import newsApp.services.userService.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Controller
@RequestMapping("/")
public class NewsController {

    private final UserService userService;

    public NewsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public RedirectView mainPage(){
        return new RedirectView("news");
    }
    @GetMapping("news")
    public String loadByPage(Model model,
                            Authentication auth,
                            @RequestParam(value = "page",required = false,defaultValue = "0") Integer page){
        log.info("Main-page GET request worked!");
        Page<News> pages = userService.loadLatestNewsPages(page);

        NUserDetails userDetails =(NUserDetails)auth.getPrincipal();
        model.addAttribute("userName",userDetails.getFullName());
        model.addAttribute("pages",pages);
        return "main-page";
    }

    @GetMapping("news/{newsId}")
    public String getOneNewsDetailed(@PathVariable("newsId") long newsId,
                                     Authentication auth,
                                     Model model) throws NewsNotFound {
        log.info(String.format("NewsId:%d sent to service!",newsId));
        News newsById = userService.getNewsById(newsId);
        NUserDetails userDetails =(NUserDetails)auth.getPrincipal();

        model.addAttribute("userName",userDetails.getFullName());
        model.addAttribute("news",newsById);

        return "open-tab";
    }
}
