package pl.net.nowak.ui.core;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mno
 */
public class LayoutInterceptor extends HandlerInterceptorAdapter {

    Logger log = Logger.getLogger(this.getClass().getCanonicalName());

    private static final String DEFAULT_LAYOUT = "index.html";
    private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";
    private List<String> restrictedViews = Arrays.asList(new String[]{
            "simulateError.html", "login.html", "error.html","logout.html", "index.html"
    });


    private String defaultLayout = DEFAULT_LAYOUT;
    private String viewAttributeName = DEFAULT_VIEW_ATTRIBUTE_NAME;

    public void setDefaultLayout(String defaultLayout) {
        Assert.hasLength(defaultLayout);
        this.defaultLayout = defaultLayout;
    }

    public void setViewAttributeName(String viewAttributeName) {
        Assert.hasLength(defaultLayout);
        this.viewAttributeName = viewAttributeName;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.debug("INTERCEPTING REQUEST : " + request.getRequestURI());


        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }
        String originalViewName = modelAndView.getViewName();

        if (isRedirectOrForward(originalViewName)) {
            return;
        }

        if(restrictedViews.contains(originalViewName) ) {
            return;
        }

//        String layoutName = getLayoutName(handler);
        modelAndView.setViewName(this.defaultLayout);
        modelAndView.addObject(this.viewAttributeName, originalViewName);
    }

    private boolean isRedirectOrForward(String viewName) {
        return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
    }

//    private String getLayoutName(Object handler) {
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Layout layout = getMethodOrTypeAnnotation(handlerMethod);
//        if (layout == null) {
//            return this.defaultLayout;
//        } else {
//            return layout.value();
//        }
//    }
//
//    private Layout getMethodOrTypeAnnotation(HandlerMethod handlerMethod) {
//        Layout layout = handlerMethod.getMethodAnnotation(Layout.class);
//        if (layout == null) {
//            return handlerMethod.getBeanType().getAnnotation(Layout.class);
//        }
//        return layout;
//    }
}
