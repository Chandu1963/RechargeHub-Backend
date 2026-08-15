package com.example.demo.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Admin;
import com.example.demo.entity.Customer;
import com.example.demo.entity.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CustomerRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserService userService;

    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;



    public UserDetailsServiceImpl(
            UserService userService,
            CustomerRepository customerRepository,
            AdminRepository adminRepository) {

        this.userService = userService;
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
    }



    @Override
    public UserDetails loadUserByUsername(
            String mobileNumber)
            throws UsernameNotFoundException {



        Customer customer =
                customerRepository
                .findByMobileNumber(mobileNumber)
                .orElse(null);



        if(customer != null) {


            User user =
                    userService
                    .getUserByCustomer(customer)
                    .orElse(null);



            if(user != null) {


                return new org.springframework.security
                        .core.userdetails.User(

                        mobileNumber,
                        "",
                        Collections.singleton(
                                new SimpleGrantedAuthority(
                                "ROLE_"+user.getRole().name())
                        )
                );
            }
        }



        Admin admin =
                adminRepository
                .findByMobileNumber(mobileNumber)
                .orElse(null);



        if(admin != null) {


            return new org.springframework.security
                    .core.userdetails.User(

                    mobileNumber,
                    "",
                    Collections.singleton(
                            new SimpleGrantedAuthority(
                            "ROLE_ADMIN")
                    )
            );
        }



        throw new UsernameNotFoundException(
                "User/Admin not found");
    }

}