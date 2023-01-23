package web.app.restfulquiz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import web.app.restfulquiz.dao.OptionDao;
import web.app.restfulquiz.domain.entity.Option;
import web.app.restfulquiz.service.impl.OptionServiceImpl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {

    @Mock
    private OptionDao optionDao;

    @InjectMocks
    private OptionServiceImpl optionService;


    @Test
    void getOptionByIdTest() {
        Optional<Option> expected = Optional.ofNullable(Option.builder()
                        .option_id(1)
                        .question_id(1)
                        .content("Option1")
                        .is_solution(false)
                        .build());
        Mockito.when(optionDao.getOptionById(1)).thenReturn(expected);
        assertEquals(expected, optionService.getOptionById(1));
    }


    @Test
    void getOptionsByQuestionTest() {
        List<Option> expected = new ArrayList<>();
        expected.add(Option.builder()
                .option_id(5)
                .question_id(2)
                .content("String")
                .is_solution(true)
                .build());
        expected.add(Option.builder()
                .option_id(6)
                .question_id(2)
                .content("int")
                .is_solution(false)
                .build());
        expected.add(Option.builder()
                .option_id(7)
                .question_id(2)
                .content("double")
                .is_solution(false)
                .build());
        expected.add(Option.builder()
                .option_id(8)
                .question_id(2)
                .content("float")
                .is_solution(false)
                .build());
        Mockito.when(optionDao.getOptionsByQuestion(2)).thenReturn(expected);
        assertEquals(expected, optionService.getOptionsByQuestion(2));
    }

}
