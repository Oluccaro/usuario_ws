package com.fracaro.usuario.rest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fracaro.usuario.model.Usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@CrossOrigin
@RestController
public class UsuarioREST {
  public static List<Usuario> lista = new ArrayList<Usuario>();

  static{
    lista.add(new Usuario(Long.valueOf(1), "Anderson Administrador", "admin", "admin", "ADMIN"));
    lista.add(new Usuario(Long.valueOf(2), "Gerson Gerente", "gerente", "gerente", "GER"));
    lista.add(new Usuario(Long.valueOf(3), "Fabiano Funcionario", "func", "func", "FUNC"));
  }

  @GetMapping("/usuarios")
  public List<Usuario> obterTodosUsuarios() {
      return lista;
  }

  @GetMapping("/usuarios/{id}")
  public Usuario obterUsuarioPorId(@PathVariable("id") int id){
    return lista.stream().filter(usu -> usu.getId() == id).findAny().orElse(null);
  }

  @PostMapping("/usuarios")
  @ResponseStatus(HttpStatus.CREATED)
  public Usuario inserirUsuario(@RequestBody Usuario usuario) {
    Usuario u = lista.stream().max(Comparator.comparing(Usuario::getId)).orElse(null);
    
    if(u == null){
      usuario.setId(Long.valueOf(1));
    }
    else {
      usuario.setId(u.getId() + 1);
    }
    lista.add(usuario);
    return usuario;
  }
  
  @PutMapping("usuarios/{id}")
  public Usuario alterarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
      Usuario u = lista.stream().filter(usu -> usu.getId() == id).findAny().orElse(null);
      
      if(u != null){
        u.setNome(usuario.getNome());
        u.setLogin(usuario.getLogin());
        u.setSenha(usuario.getSenha());
        u.setPerfil(usuario.getPerfil());
      }
      return u;
  }

  @DeleteMapping("usuarios/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removerUsuario(@PathVariable("id") int id){
    lista.removeIf(usu -> usu.getId() == id);
  }
  
}
