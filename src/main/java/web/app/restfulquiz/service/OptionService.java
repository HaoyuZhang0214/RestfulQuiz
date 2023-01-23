package web.app.restfulquiz.service;

import web.app.restfulquiz.domain.entity.Option;
import web.app.restfulquiz.domain.request.QuestionRequest;

import java.util.List;
import java.util.Optional;

public interface OptionService {

    Optional<Option> getOptionById(int option_id);

    List<Option> getOptionsByQuestion(int question_id);

    void updateOption(Option option);

    void createOption(QuestionRequest request, int question_id);

}
