package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import net.mert.reportingapi.model.response.ResponseTemplate;
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
    private final String validTransactionId = "1010981-1539271547-1293";
    private final String validEmail = "demo@bumin.com.tr";
    private final String validPasword = "cjaiU8CV";
    private final String validFromDate = "2000-01-01";
    private final String validToDate = "2020-12-31";

    @Before
    public void setUp() {
        MerchantLoginRequest loginData = new MerchantLoginRequest(validEmail, validPasword);
        Optional<ResponseTemplate> login = merchantLoginService.login(loginData);

        if (login.isPresent()) {
            token = ((TokenResponse) login.get()).getToken();
        } else {
            throw new UnsupportedOperationException("Cannot get a valid JWT Token!");
        }
    }

    /* URL: /transaction */
    @Test
    public void getTransaction_WithNoParamAndHeader_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transaction")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getTransaction_WithParamAndNoHeader_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("transactionId", validTransactionId)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getTransaction_WithHeaderAndNoParam_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transaction")
                .header("Authorization", token)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void getTransaction_WithParamAndExpiredToken_ShouldReturnBadRequest() throws Exception {
        String wrongToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJ1c2VyIiwibWVyY2hhbnRJZCI6Mywic3ViTWVyY2hhbnRJZHMiOlszLDc0LDkzLDExOTEsMTI5NSwxMTEsMTM3LDEzOCwxNDIsMTQ1LDE0NiwxNTMsMzM0LDE3NSwxODQsMjIwLDIyMSwyMjIsMjIzLDI5NCwzMjIsMzIzLDMyNywzMjksMzMwLDM0OSwzOTAsMzkxLDQ1NSw0NTYsNDc5LDQ4OCw1NjMsMTE0OSw1NzAsMTEzOCwxMTU2LDExNTcsMTE1OCwxMTc5LDEyOTMsMTI5NF0sInRpbWVzdGFtcCI6MTU0MzQxMzg3MH0.oeWxzEh_u1KlLG2dHnGwmm6kA_SUw9hbB_YDGzvAIBU";

        mockMvc.perform(post("/transaction")
                .header("Authorization", wrongToken)
                .param("transactionId", validTransactionId)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"))
                .andExpect(jsonPath("message").value("Token Expired"));
    }

    @Test
    public void getTransaction_WithParamAndHeader_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/transaction")
                .header("Authorization", token)
                .param("transactionId", validTransactionId)
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
    public void listTransactions_NoParamAndHeader_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transaction/list")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void listTransactions_WithParamsAndNoHeader_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transaction/list")
                .param("fromDate", validFromDate)
                .param("toDate", validToDate)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void listTransactions_WithHeaderAndNoParams_ShouldReturnEmptyData() throws Exception {
        mockMvc.perform(post("/transaction/list")
                .header("Authorization", token)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    public void listTransactions_WithParamsAndExpiredToken_ShouldReturnBadRequest() throws Exception {
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
    public void listTransactions_WithParamAndHeader_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/transaction/list")
                .header("Authorization", token)
                .param("fromDate", validFromDate)
                .param("toDate", validToDate)
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
    public void transactionsReport_NoHeaderAndParams_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transactions/report")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void transactionsReport_WithParamsAndNoHeader_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/transactions/report")
                .param("fromDate", validFromDate)
                .param("toDate", validToDate)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("DECLINED"));
    }

    @Test
    public void transactionsReport_WithHeaderAndNoParams_ShouldReturnEmptyData() throws Exception {
        mockMvc.perform(post("/transactions/report")
                .header("Authorization", token)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("response").isEmpty());
    }

    @Test
    public void transactionsReport_WithParamsAndHeader_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/transactions/report")
                .header("Authorization", token)
                .param("fromDate", validFromDate)
                .param("toDate", validToDate)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").value("APPROVED"))
                .andExpect(jsonPath("response").isArray());
    }

    @Test
    public void transactionsReport_WithParamsAndExpiredToken_ShouldReturnBadRequest() throws Exception {
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
