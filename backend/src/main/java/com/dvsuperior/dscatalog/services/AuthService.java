package com.dvsuperior.dscatalog.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.dvsuperior.dscatalog.dto.EmailDTO;
import com.dvsuperior.dscatalog.entities.PasswordRecover;
import com.dvsuperior.dscatalog.entities.User;
import com.dvsuperior.dscatalog.repositories.PasswordRecoverRepository;
import com.dvsuperior.dscatalog.repositories.UserRepository;
import com.dvsuperior.dscatalog.services.execeptions.ResourceEntityNotFoundException;


@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;


    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;


    @Transactional
    public void createRecoverToken(EmailDTO body) {

        User user = repository.findByEmail(body.getEmail());
        if (user == null) {
            throw new ResourceEntityNotFoundException("Email não encontrado");
        }

        String token = UUID.randomUUID().toString();


        PasswordRecover entity = new PasswordRecover();
        entity.setEmail(body.getEmail());
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        entity = passwordRecoverRepository.save(entity);

        String text = "Acesse o link para definir uma nova senha\n\n"
            + recoverUri + token + ". Validade de " + tokenMinutes + " minutos";

        emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);
    }

}
