package com.doodledoodle.backend.draw.controller;

import static com.doodledoodle.backend.support.docs.ApiDocumentUtils.getDocumentRequest;
import static com.doodledoodle.backend.support.docs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.doodledoodle.backend.draw.dto.response.DrawResponse;
import com.doodledoodle.backend.draw.service.DrawService;
import com.doodledoodle.backend.draw.service.DrawServiceImpl;
import com.doodledoodle.backend.support.docs.RestDocumentTest;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("Draw 컨트롤러의")
@WebMvcTest(DrawController.class)
class DrawControllerTest extends RestDocumentTest {

    @MockBean private DrawService drawService;

    @Test
    @DisplayName("그림을 저장하는 API가 수행되는가")
    void saveDraw() throws Exception {
        //given
        DrawResponse expected = new DrawResponse(UUID.randomUUID());
        given(drawService.saveDraw(any(), any(), any())).willReturn(expected);
        ResultActions perform =
                mockMvc.perform(
                                post("/draws/games/" + UUID.randomUUID() + "/player-no/1")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(
                                        toRequestBody(
                                            "여기에 파일을 넣어주세요")));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
            .andDo(document("save draw and send to kafka", getDocumentRequest(), getDocumentResponse()));

    }
}
