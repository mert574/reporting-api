package net.mert.reportingapi;

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

    @Test
    public void getTokenWithNoParamsShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/merchant/user/login")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getTokenWithValidParamsShouldReturnToken() throws Exception {
        MerchantLoginRequest merch = new MerchantLoginRequest("email@mail.net", "passw");

        mockMvc.perform(post("/merchant/user/login")
                    .param("email", merch.getEmail())
                    .param("password", merch.getPassword())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("token").isNotEmpty());
    }
}