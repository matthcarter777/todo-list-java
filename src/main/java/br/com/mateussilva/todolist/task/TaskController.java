package br.com.mateussilva.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  
  @Autowired
  private ITaskRepository taskRepository;
  
  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    taskModel.setIdUser((UUID)  request.getAttribute("idUser"));

    var currentDate = LocalDateTime.now();

    if (currentDate.isAfter(taskModel.getStartAt()) || 
      taskModel.getStartAt().isAfter(taskModel.getEndAt())) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("A data de inicio/Final deve ser maior que a data autual");
    }

    var task = this.taskRepository.save(taskModel);

    return ResponseEntity.status(200).body(task);
  }

  @GetMapping("/")
  public List<TaskModel> list(HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");

    var tasks = this.taskRepository.findByIdUser((UUID)idUser);

    return tasks;
  }
}
