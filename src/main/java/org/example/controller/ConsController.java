package org.example.controller;//package org.example.controller;
//
//import org.example.dao.ConsDao;
//import org.example.dao.ConsumerEntityDaoImpl;
//import org.example.entity.Consumer;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.sql.SQLException;
//
//@Controller
//@RequestMapping("/")
//public class ConsController {
//    private final ConsDao consDao = ConsumerEntityDaoImpl.getInstance();
//
//    @RequestMapping("/hello")
//    public String hello(Model model) throws SQLException {
//        model.addAttribute("cons", consDao.findAllConsumer());
//        return "hello";
//    }
//
//    @GetMapping("/{id}")
//    public String consumerById(@PathVariable("id") int id, Model model) throws SQLException {
//        model.addAttribute("cons", consDao.findConsumerById(id));
//        return "hello";
//    }
//
//    @GetMapping("/add")
//    public  String addCons(@ModelAttribute("cons") Consumer consumer) {
//        return "add";
//    }
//
//    @PostMapping("/hello")
//    public String create(@ModelAttribute("cons") @Valid Consumer consumer, BindingResult bindingResult) throws SQLException {
//        if (bindingResult.hasErrors())
//            return "add";
//
//        consDao.save(consumer);
//        return "redirect:/hello";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable("id") int id, Model model) throws SQLException {
//        model.addAttribute("cons", consDao.findConsumerById(id));
//        return "edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("cons") @Valid Consumer consumer,
//                         BindingResult bindingResult) throws SQLException {
//        if (bindingResult.hasErrors()) {
//            return "edit";
//        }
//        consDao.update(consumer);
//        return "redirect:/hello";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) throws SQLException {
//        consDao.deleteById(id);
//        return "redirect:/hello";
//    }
//}
