package br.com.mateussilva.todolist.login;

import org.springframework.beans.factory.annotation.Autowired;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.mateussilva.todolist.user.IUserRepository;

public class LoginService {

  @Autowired
  private IUserRepository userRepository;

  public Boolean execute(LoginModel credentialsRequest) {

    var user = this.userRepository.findByUsername(credentialsRequest.username);

    System.out.println(user);

    return true;
  }
}
