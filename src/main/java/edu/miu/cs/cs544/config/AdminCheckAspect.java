package edu.miu.cs.cs544.config;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.repository.UserRepository;
import edu.miu.cs.cs544.domain.RoleType;
import edu.miu.cs.cs544.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminCheckAspect {

    @Autowired
    private UserRepository userRepository;

    @Before("execution(* edu.miu.cs.cs544.service.ProductService.addProduct(..)) || " +
            "execution(* edu.miu.cs.cs544.service.ProductService.updateProduct(..)) || " +
            "execution(* edu.miu.cs.cs544.service.ProductService.deleteProduct(..))")
    public void checkAdminUser(JoinPoint joinPoint) throws CustomError{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        if (user == null || user.getRoleType() != RoleType.ADMIN) {
            throw new CustomError("You are not authorized to perform this action");
        }
    }
}