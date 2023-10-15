package br.com.mateussilva.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.converter.HttpMessageNotReadableException;

import br.com.mateussilva.todolist.errors.ExceptionHandlerController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private String description;

  @Column(length = 50)
  private String title;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private String prority;
  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime creeatedAt;

  public void setTitle(String title) throws Exception {
    if (title.length() > 50) {
        throw new Exception("Título com mais de 50 caracteres.");
    }
    this.title = title;
  }

}
