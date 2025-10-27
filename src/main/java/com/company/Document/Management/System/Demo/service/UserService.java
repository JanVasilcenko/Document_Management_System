package com.company.Document.Management.System.Demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String getCurrentUsername() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return (a != null && a.isAuthenticated()) ? a.getName() : "unknown";
    }
}
