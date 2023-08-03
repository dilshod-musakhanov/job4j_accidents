package ru.job4j.accidents.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @MockBean
    private AccidentTypeService accidentTypeService;

    @MockBean
    private RuleService ruleService;

    @Test
    @WithMockUser
    public void shouldReturnCreateAccidentPage() throws Exception {
        this.mockMvc.perform(get("/accident/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/createAccident"));
    }

    @Test
    @WithMockUser
    public void shouldReturnEditAccidentPage() throws Exception {
        Set<Rule> rules = Set.of(
                new Rule(1, "Rule1"),
                new Rule(2, "Rule2"),
                new Rule(3, "Rule3")
        );
        Optional<Accident> accident = Optional.of(new Accident(
                20,
                "TestA",
                "Text",
                "Address",
                new AccidentType(),
                rules));
        when(accidentService.findById(20)).thenReturn(accident);
        this.mockMvc.perform(get("/accident/edit?id=" + 20))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/editAccident"));
    }

    @Test
    @WithMockUser
    public void shouldSaveAccidentAndRedirectIndexPage() throws Exception {
        String[] rIds = {"1", "2", "3"};

        mockMvc.perform(post("/accident/save")
                .param("name", "TestName")
                .param("text", "TestText")
                .param("address", "TestAddress")
                .param("type.id", "4")
                .param("rIds", rIds))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService, times(1)).save(accidentCaptor.capture(), eq(rIds));
        Accident capturedAccident = accidentCaptor.getValue();
        assertThat(capturedAccident.getName()).isEqualTo("TestName");
        assertThat(capturedAccident.getText()).isEqualTo("TestText");
        assertThat(capturedAccident.getAddress()).isEqualTo("TestAddress");
        assertThat(capturedAccident.getType().getId()).isEqualTo(4);
    }

    @Test
    @WithMockUser
    public void shouldUpdateAccidentAndRedirectIndexPage() throws Exception {
        String[] rIds = {"1", "2", "3"};

        mockMvc.perform(post("/accident/update")
                        .param("name", "TestName")
                        .param("text", "TestText")
                        .param("address", "TestAddress")
                        .param("type.id", "4")
                        .param("rIds", rIds))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService, times(1)).update(accidentCaptor.capture(), eq(rIds));
        Accident capturedAccident = accidentCaptor.getValue();
        assertThat(capturedAccident.getName()).isEqualTo("TestName");
        assertThat(capturedAccident.getText()).isEqualTo("TestText");
        assertThat(capturedAccident.getAddress()).isEqualTo("TestAddress");
        assertThat(capturedAccident.getType().getId()).isEqualTo(4);
    }

}