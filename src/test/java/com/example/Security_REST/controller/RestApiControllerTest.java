package com.example.Security_REST.controller;


import com.example.Security_REST.DAO.RoleRepository;
import com.example.Security_REST.model.Role;
import com.example.Security_REST.model.User;
import com.example.Security_REST.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApiControllerTest {
    private final MockMvc mockMvc;
    private final WebApplicationContext context;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final RoleRepository roleRepository;


    @Autowired
    public RestApiControllerTest(MockMvc mockMvc, WebApplicationContext context, ObjectMapper objectMapper, UserService userService, RoleRepository roleRepository) {
        this.mockMvc = mockMvc;
        this.context = context;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    //    @PostConstruct
//    public void addDefaultRole() {
//        roleRepository.save(new Role("ROLE_USER"));
//        roleRepository.save(new Role("ROLE_ADMIN"));
//        Set<Role> roles1 = new HashSet<>();
//        roles1.add(roleRepository.findById(1));
//        Set<Role> roles2 = new HashSet<>();
//        roles2.add(roleRepository.findById(1));
//        roles2.add(roleRepository.findById(2));
//        User user1 = new User("user", "Jobs", 25, "user@mail.com", "user", roles1);
//        User user2 = new User("admin", "Potter", 30, "admin@mail.com", "admin", roles2);
//        userService.save(user1);
//        userService.save(user2);
//    }
    @Test
    @Transactional
    @WithMockUser(username = "admin",roles={"USER","ADMIN"})
    void RestApiControllerTest() throws Exception {
//        GIVEN
        int userId = 1;
        roleRepository.save(new Role("ROLE_USER"));
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleRepository.findById(1));

        User user = userService.getById(userId);
        User user5 = new User("admin", "Potter", 30, "admin@mail.com", "admin", roles1);
        int userId5 = user5.getUserId();
        userService.save(user5);

//                GET
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));

//                GET by id
        mockMvc.perform(get("/api/users/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.surname").value(user.getSurname()));

        //        POST

//        mockMvc.perform(
//                        post("/api/users")
//                                .content(objectMapper.writeValueAsString(user))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.userId").value(user.getUserId()))
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.surname").value(user.getSurname()));

//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON))
////                        .content(objectMapper.writeValueAsString(user)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value(user.getUserId()))
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.surname").value(user.getSurname()));

//                 DELETE

        mockMvc.perform(delete("/api/users/{userId5}", userId5))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
