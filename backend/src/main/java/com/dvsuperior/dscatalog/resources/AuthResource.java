package com.dvsuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dvsuperior.dscatalog.dto.EmailDTO;
import com.dvsuperior.dscatalog.dto.ProductDTO;
import com.dvsuperior.dscatalog.dto.UserDTO;
import com.dvsuperior.dscatalog.dto.UserInsertDTO;
import com.dvsuperior.dscatalog.dto.UserUpdateDTO;
import com.dvsuperior.dscatalog.entities.User;
import com.dvsuperior.dscatalog.services.AuthService;
import com.dvsuperior.dscatalog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;
    
    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO body){
        authService.createRecoverToken(body);
        return ResponseEntity.noContent().build();
    }
    /*
    @PutMapping(value = "/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody NewPasswordDTO body){
        authService.resetPassword(body);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<Void> creatRecoverToken(EmailDTO body){
        Page<UserDTO> list = service.findAllPaged();
        return ResponseEntity.ok().body(list);
    }
    */

}
