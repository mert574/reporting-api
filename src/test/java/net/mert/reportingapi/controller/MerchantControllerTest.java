package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MerchantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String validEmail = "demo@bumin.com.tr";
    private final String validPasword = "cjaiU8CV";

    @Test
    public void handleLogin_NoParams_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/merchant/user/login")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void handleLogin_InvalidParams_ShouldReturnBadRequest() throws Exception {
        MerchantLoginRequest merchant = new MerchantLoginRequest(validEmail, "wrong-password");

        mockMvc.perform(post("/merchant/user/login")
                .param("email", merchant.getEmail())
                .param("password", merchant.getPassword())
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"))
                .andExpect(jsonPath("code").value(0));
    }

    @Test
    public void handleLogin_WithParams_ShouldReturnOk() throws Exception {
        MerchantLoginRequest merchant = new MerchantLoginRequest(validEmail, validPasword);

        mockMvc.perform(post("/merchant/user/login")
                    .param("email", merchant.getEmail())
                    .param("password", merchant.getPassword())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("token").isNotEmpty());
    }
}
