package br.com.mateussilva.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.mateussilva.todolist.login.LoginModel;
import br.com.mateussilva.todolist.login.LoginService;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserRepository userRepository;
  
  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel) {

    var user = this.userRepository.findByUsername(userModel.getUsername());

    if (user != null) {
      return ResponseEntity.status(400).body("Usuário já existe");
    }

    var password = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
    
    userModel.setPassword(password);

    var userCreated = this.userRepository.save(userModel);

    return ResponseEntity.status(201).body(userCreated);
  }

  @PostMapping("/login")
  public ResponseEntity<Boolean> login(@RequestBody LoginModel credentials) {
    var username = credentials.username;

    var user = this.userRepository.findByUsername(username);

    if (user == null) {
       return ResponseEntity.status(400).body(false);
    }

    var passwordVerify = BCrypt.verifyer().verify(credentials.password.toCharArray(), user.getPassword());
            
    if (!passwordVerify.verified) {
      return ResponseEntity.status(400).body(false);
    }

    return ResponseEntity.status(201).body(true);
  }

  
}
