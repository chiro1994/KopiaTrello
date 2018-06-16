package pl.lodz.p.cti.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cti.models.TaskModel;
import pl.lodz.p.cti.models.UserModel;
import pl.lodz.p.cti.services.TaskService;
import pl.lodz.p.cti.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller @RequiredArgsConstructor public class MainController {

    private final UserService userService;
    private final TaskService taskService;
    private final SimpMessagingTemplate template;

    @GetMapping("/login") public String login(Model model) {
        return "/login";
    }

    @GetMapping("/register") public String registerGET() {
        return "/register";
    }

    @PostMapping("/register") public String registerPost(Model model,
            @RequestParam("username")
                    String username,
            @RequestParam("password")
                    String password,
            @RequestParam("role")
                    String role) {
        UserModel userModel = userService.findByUsernameAndPassword(username, password);
        if (userModel != null) {
            model.addAttribute("error", "Już istnieje taki uzytkownik!");
            model.addAttribute("savedLogin", false);
        } else {
            userService.save(UserModel.builder().username(username).password(password).role(role).build());
            model.addAttribute("savedLogin", true);
            model.addAttribute("logged", false);
            return "/login";
        }
        return "/register";
    }

    @RequestMapping("/addTask") public String addTask(
            @RequestParam("descriptions") String descriptions, HttpServletResponse response) {

        try {
            TaskModel taskModel = TaskModel.
                    builder().authorId(1L).descriptions(descriptions).taskTable(1).startDate(0L)
                    .endDate(0L).build();

            taskService.save(taskModel);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return "redirect:/tasksList";
    }

    @GetMapping("/tasksList")
    public String tasksList(Model model) {
        model.addAttribute("tasks", taskService.findAllTask());
        return "/tasksList";
    }

    @RequestMapping(value = "/tasks/update/{id}/{taskTable}", method = RequestMethod.POST)
    public void updateTaskStatus(@PathVariable("id") long id, @PathVariable("taskTable") int taskTable){
        taskService.setPositionTask(taskTable, id);
    }

    @RequestMapping("/saveTask") public @ResponseBody void saveTask(
            @RequestParam("authorId")
                    Long authorId,
            @RequestParam("descriptions")
                    String descriptions,
            @RequestParam("taskTable")
                    int taskTable,
            @RequestParam("startDate")
                    Long startDate,
            @RequestParam("endDate")
                    Long endDate, HttpServletResponse response) {

        try {
            TaskModel taskModel = TaskModel.
                    builder().authorId(authorId).descriptions(descriptions).taskTable(taskTable).startDate(startDate)
                    .endDate(endDate).build();

            taskService.save(taskModel);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping("/getAllTask") public @ResponseBody List<TaskModel> getAllTask() {
        return taskService.findAllTask();
    }

    @RequestMapping("/getTask") public @ResponseBody TaskModel getTask(
            @RequestParam("taskId")
                    Long authorId) {
        return taskService.findTask(authorId);
    }

    @RequestMapping("/getTasksAuthor") public @ResponseBody List<TaskModel> getTasksAuthor(
            @RequestParam("authorId")
                    Long authorId) {
        return taskService.getTasksAuthor(authorId);
    }

    @RequestMapping("/deleteTask") public @ResponseBody void deleteTask(
            @RequestParam("taskId")
                    Long taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping("/403") public String error403() {
        return "/error/403";
    }

}
