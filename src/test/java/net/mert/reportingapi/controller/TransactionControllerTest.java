package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.MerchantLoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MerchantLoginService merchantLoginService;

    private String token;

    @Before
    public void setUp() {
        MerchantLoginRequest loginData = new MerchantLoginRequest("demo@bumin.com.tr", "cjaiU8CV");
        Optional<TokenResponse> login = merchantLoginService.login(loginData);

        if (login.isPresent()) {
            token = login.get().getToken();
        } else {
            throw new UnsupportedOperationException("Cannot get a valid JWT Token!");
        }
    }

    /* URL: /transaction */
    @Test
    public void getTransactionWithNoParamAndHeaderShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transaction")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getTransactionWithNoHeaderWithParamShouldReturnBadRequest() throws Exception {
        String transactionId = "1010981-1539271547-1293";

        mockMvc.perform(post("/transaction")
                .param("transactionId", transactionId)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getTransactionWithNoParamWithHeaderShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transaction")
                .header("Authorization", token)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getTransactionWithExpiredTokenShouldReturnTokenExpired() throws Exception {
        String wrongToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJ1c2VyIiwibWVyY2hhbnRJZCI6Mywic3ViTWVyY2hhbnRJZHMiOlszLDc0LDkzLDExOTEsMTI5NSwxMTEsMTM3LDEzOCwxNDIsMTQ1LDE0NiwxNTMsMzM0LDE3NSwxODQsMjIwLDIyMSwyMjIsMjIzLDI5NCwzMjIsMzIzLDMyNywzMjksMzMwLDM0OSwzOTAsMzkxLDQ1NSw0NTYsNDc5LDQ4OCw1NjMsMTE0OSw1NzAsMTEzOCwxMTU2LDExNTcsMTE1OCwxMTc5LDEyOTMsMTI5NF0sInRpbWVzdGFtcCI6MTU0MzQxMzg3MH0.oeWxzEh_u1KlLG2dHnGwmm6kA_SUw9hbB_YDGzvAIBU";

        mockMvc.perform(post("/transaction")
                .header("Authorization", wrongToken)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"))
                .andExpect(jsonPath("message").value("Token Expired"));
    }

    @Test
    public void getTransactionValidParamAndHeaderShouldReturnTransaction() throws Exception {
        String transactionId = "1010981-1539271547-1293";

        mockMvc.perform(post("/transaction")
                .header("Authorization", token)
                .param("transactionId", transactionId)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("fx").isMap())
                .andExpect(jsonPath("customerInfo").isMap())
                .andExpect(jsonPath("merchant").isMap())
                .andExpect(jsonPath("transaction").isMap());
    }


    /* URL: /transaction/list */
    @Test
    public void listTransactionsWithNoParamAndHeaderShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transaction/list")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void listTransactionsWithNoHeaderWithParamsShouldReturnBadRequest() throws Exception {
        String fromDate = "2000-01-01", toDate = "2020-12-31";

        mockMvc.perform(post("/transaction/list")
                .param("fromDate", fromDate)
                .param("toDate", toDate)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void listTransactionsWithNoParamsWithHeaderShouldReturnEmptyData() throws Exception {
        mockMvc.perform(post("/transaction/list")
                .header("Authorization", token)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    public void listTransactionsWithExpiredTokenShouldReturnTokenExpired() throws Exception {
        String wrongToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJ1c2VyIiwibWVyY2hhbnRJZCI6Mywic3ViTWVyY2hhbnRJZHMiOlszLDc0LDkzLDExOTEsMTI5NSwxMTEsMTM3LDEzOCwxNDIsMTQ1LDE0NiwxNTMsMzM0LDE3NSwxODQsMjIwLDIyMSwyMjIsMjIzLDI5NCwzMjIsMzIzLDMyNywzMjksMzMwLDM0OSwzOTAsMzkxLDQ1NSw0NTYsNDc5LDQ4OCw1NjMsMTE0OSw1NzAsMTEzOCwxMTU2LDExNTcsMTE1OCwxMTc5LDEyOTMsMTI5NF0sInRpbWVzdGFtcCI6MTU0MzQxMzg3MH0.oeWxzEh_u1KlLG2dHnGwmm6kA_SUw9hbB_YDGzvAIBU";

        mockMvc.perform(post("/transaction/list")
                .header("Authorization", wrongToken)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"))
                .andExpect(jsonPath("message").value("Token Expired"));
    }

    @Test
    public void listTransactionsValidParamAndHeaderShouldReturnList() throws Exception {
        String fromDate = "2000-01-01", toDate = "2020-12-31";

        mockMvc.perform(post("/transaction/list")
                .header("Authorization", token)
                .param("fromDate", fromDate)
                .param("toDate", toDate)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("per_page").exists())
                .andExpect(jsonPath("current_page").exists())
                .andExpect(jsonPath("from").exists())
                .andExpect(jsonPath("to").exists())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath("data").isNotEmpty());
    }


    /* URL: /transactions/report */
    @Test
    public void getResponseWithNoHeaderNoParamsShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transactions/report")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getResponseWithNoHeaderWithParamsShouldReturnBadRequest() throws Exception {
        String fromDate = "2000-01-01", toDate = "2020-12-31";

        mockMvc.perform(post("/transactions/report")
                .param("fromDate", fromDate)
                .param("toDate", toDate)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getResponseWithNoParamsWithHeaderShouldReturnEmptyResponseField() throws Exception {
        mockMvc.perform(post("/transactions/report")
                .header("Authorization", token)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("response").isEmpty());
    }

    @Test
    public void getResponseWithParamsAndHeaderShouldReturnProperResponse() throws Exception {
        String fromDate = "2000-01-01", toDate = "2020-12-31";

        mockMvc.perform(post("/transactions/report")
                .header("Authorization", token)
                .param("fromDate", fromDate)
                .param("toDate", toDate)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("response").isArray());
    }

    @Test
    public void getResponseWithExpiredTokenShouldReturnTokenExpired() throws Exception {
        String wrongToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJ1c2VyIiwibWVyY2hhbnRJZCI6Mywic3ViTWVyY2hhbnRJZHMiOlszLDc0LDkzLDExOTEsMTI5NSwxMTEsMTM3LDEzOCwxNDIsMTQ1LDE0NiwxNTMsMzM0LDE3NSwxODQsMjIwLDIyMSwyMjIsMjIzLDI5NCwzMjIsMzIzLDMyNywzMjksMzMwLDM0OSwzOTAsMzkxLDQ1NSw0NTYsNDc5LDQ4OCw1NjMsMTE0OSw1NzAsMTEzOCwxMTU2LDExNTcsMTE1OCwxMTc5LDEyOTMsMTI5NF0sInRpbWVzdGFtcCI6MTU0MzQxMzg3MH0.oeWxzEh_u1KlLG2dHnGwmm6kA_SUw9hbB_YDGzvAIBU";

        mockMvc.perform(post("/transactions/report")
                .header("Authorization", wrongToken)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"))
                .andExpect(jsonPath("message").value("Token Expired"));
    }
}
