package com.example.lianxi1.controller;
import com.example.lianxi1.pojo.Good;
import com.example.lianxi1.pojo.place;
import com.example.lianxi1.tools.GoodsHbase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.lianxi1.controller.TestDemo.close;
import static com.example.lianxi1.controller.TestDemo.put;

@Controller
public class LoginController {
    @RequestMapping("/index")
    public String a( Model model)throws Exception{
        GoodsHbase goodsHbase = new GoodsHbase();
        List<place> list1 = goodsHbase.getall();
        model.addAttribute("placelist",list1);
        List<Good> list2 = goodsHbase.getGoodSum(1);
        model.addAttribute("goods",list2);
        goodsHbase.close();
        goodsHbase.close();
        return "index";
    }
//    @RequestMapping("/yun")
//    public String b(int id,Model model)throws IOException {
//        GoodsHbase goodsHbase = new GoodsHbase();
//        List<place> list1 = goodsHbase.getall();
//        model.addAttribute("placelist",list1);
//        List<Good> list2 = goodsHbase.getGoodSum(id);
//        model.addAttribute("goods",list2);
//        goodsHbase.close();
//        return "head";
//    }
//    @RequestMapping("/taobao")
//    public String taobao(){
//        return "taobao";
//    }
}
