package com.hongjun.app.controller;

import com.hongjun.common.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hongjun500
 * @date 2021/6/1 21:19
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Controller
@RequestMapping(value = "/index")
public class AppIndexController extends BaseController{

    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("/static/index.html");
        return modelAndView;
    }
}
