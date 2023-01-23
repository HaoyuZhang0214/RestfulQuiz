package web.app.restfulquiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import web.app.restfulquiz.dao.OptionDao;
import web.app.restfulquiz.domain.entity.Option;
import web.app.restfulquiz.domain.request.QuestionRequest;
import web.app.restfulquiz.service.OptionService;

import java.util.List;
import java.util.Optional;

@Service
public class OptionServiceImpl implements OptionService {

    private OptionDao optionDao;

    @Autowired
    public OptionServiceImpl(OptionDao optionDao) {
        this.optionDao = optionDao;
    }

    @Override
    @Cacheable("options")
    public Optional<Option> getOptionById(int option_id) {
        return optionDao.getOptionById(option_id);
    }

    @Override
    public List<Option> getOptionsByQuestion(int question_id) {
        return optionDao.getOptionsByQuestion(question_id);
    }

    @Override
    public void updateOption(Option option) {
        optionDao.updateOption(option.getOption_id(), option.getContent(), option.is_solution());
    }

    @Override
    public void createOption(QuestionRequest request, int question_id) {
        optionDao.createOption(request, question_id);
    }

}
