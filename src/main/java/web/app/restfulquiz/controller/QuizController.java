package web.app.restfulquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.*;
import web.app.restfulquiz.domain.request.QuizSubmitRequest;
import web.app.restfulquiz.domain.response.AllQuizzesResponse;
import web.app.restfulquiz.domain.response.QuizQuestionResponse;
import web.app.restfulquiz.exception.QuizResultNotFoundException;
import web.app.restfulquiz.service.OptionService;
import web.app.restfulquiz.service.QuestionService;
import web.app.restfulquiz.service.QuizService;
import web.app.restfulquiz.service.UserService;
import web.app.restfulquiz.service.impl.OptionServiceImpl;
import web.app.restfulquiz.service.impl.QuestionServiceImpl;
import web.app.restfulquiz.service.impl.QuizServiceImpl;
import web.app.restfulquiz.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
public class QuizController {

    private final boolean isAsync = false;
    private QuizService quizService;
    private OptionService optionService;
    private UserService userService;
    private QuestionService questionService;

    @Autowired
    public QuizController(QuizServiceImpl quizServiceImpl,
                          OptionServiceImpl optionServiceImpl,
                          UserServiceImpl userServiceImpl,
                          QuestionServiceImpl questionServiceImpl) {
        this.quizService = quizServiceImpl;
        this.optionService = optionServiceImpl;
        this.userService = userServiceImpl;
        this.questionService = questionServiceImpl;
    }


    @GetMapping(value = "/quiz", params = "category_id")
    @PreAuthorize("hasAuthority('read')")
    public QuizQuestionResponse getQuiz(@RequestParam Integer category_id) {

        Optional<QuizQues> quiz = quizService.getQuizByCategory(category_id);
        if(!quiz.isPresent()) {
            return QuizQuestionResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("No quiz in the category")
                            .build())
                    .build();
        }

        return QuizQuestionResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Got a quiz")
                        .build())
                .quiz(quiz.get())
                .build();
    }


    @PostMapping("/quiz")
    @PreAuthorize("hasAuthority('write')")
    public QuizResult submitQuiz(@Valid @RequestBody QuizSubmitRequest request,
                                         BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            List<FieldError> errors = bindingResult.getFieldErrors();
//            errors.forEach(error -> System.out.println(
//                    "ValidationError in " + error.getObjectName() + ": " + error.getDefaultMessage()));
//            return QuizResultResponse.builder()
//                    .status(ResponseStatus.builder()
//                            .success(false)
//                            .message("Validation error")
//                            .build())
//                    .build();
//        }

        // score
        List<Integer> choice = new ArrayList<>();
        for(QuestionSubmit q:request.getQuestionSubmits()) {
            choice.add(q.getOption_id());
        }
        int score = 0;
        List<Option> options = new ArrayList<>();
        for(Integer op_id: choice) {
            Optional<Option> option = optionService.getOptionById(op_id);
            if(option.isPresent()) {
                options.add(option.get());
                if(option.get().is_solution()) {
                    score += 20;
                }
            }
        }

        Optional<Quiz> quiz = quizService.getQuizById(request.getQuiz_id());
        Optional<User> user = userService.getUserById(request.getUser_id());

        //record
        Integer record_id = quizService.createQuizRecord(request.getQuiz_id(), request.getUser_id(), score);
        for(QuestionSubmit q: request.getQuestionSubmits()) {
            questionService.createQuestionRecord(record_id, q.getQuestion_id(), q.getOption_id());
        }

        // questions
        List<Question> questions = questionService.getQuestionsByQuiz(request.getQuiz_id());
        List<QuestionDone> questionDones = new ArrayList<>();
        for(Question q: questions) {
            QuestionOps questionOps = new QuestionOps(q, optionService.getOptionsByQuestion(q.getQuestion_id()));
            questionDones.add(new QuestionDone(questionOps, getChoice(request.getQuestionSubmits(), q.getQuestion_id())));
        }

        // create quiz record

//        return QuizResultResponse.builder()
//                .status(ResponseStatus.builder()
//                        .success(true)
//                        .message("Submit the quiz")
//                        .build())
//                .quizResult(QuizResult.builder()
//                        .name(quiz.isPresent()? quiz.get().getName(): null)
//                        .fullname(user.isPresent()? (user.get().getFirstname()+" "+user.get().getLastname()): null)
//                        .time(new Timestamp(System.currentTimeMillis()).toString())
//                        .questions(questionDones)
//                        .score(score)
//                        .build())
//                .build();
        return QuizResult.builder()
                        .name(quiz.isPresent()? quiz.get().getName(): null)
                        .fullname(user.isPresent()? (user.get().getFirstname()+" "+user.get().getLastname()): null)
                        .time(new Timestamp(System.currentTimeMillis()).toString())
                        .questions(questionDones)
                        .score(score)
                        .build();

    }


    @GetMapping("/quizResult")
    @PreAuthorize("hasAuthority('read')")
    public QuizResult getQuizResult(@RequestParam Integer record_id) throws QuizResultNotFoundException {
        Optional<QuizRecord> quizRecord = quizService.getQuizRecordById(record_id);
        if(!quizRecord.isPresent()) throw new QuizResultNotFoundException("Quiz result not found.");

        // Async or not
        Optional<Quiz> quiz = Optional.empty();
        Optional<User> user = Optional.empty();
        if(isAsync) {
            CompletableFuture<Optional<Quiz>> quizFuture = quizService.getQuizByIdAsync(quizRecord.get().getQuiz_id());
            CompletableFuture<Optional<User>> userFuture = userService.getUserByIdAsync(quizRecord.get().getUser_id());
            quiz = quizFuture.join();
            user = userFuture.join();
        } else {
            quiz = quizService.getQuizById(quizRecord.get().getQuiz_id());
            user = userService.getUserById(quizRecord.get().getUser_id());
        }


        List<QuestionDone> questionDones = new ArrayList<>();
        List<QuestionRecord> questionRecords = questionService.getQuestionRecordsByRecord(record_id);
        for(QuestionRecord qr: questionRecords) {
            Optional<Question> q = questionService.getQuestionById(qr.getQuestion_id());
            if(q.isPresent()) {
                List<Option> options = optionService.getOptionsByQuestion(q.get().getQuestion_id());
                questionDones.add(new QuestionDone(new QuestionOps(q.get(), options), qr.getOption_id()));
            }
        }

        return QuizResult.builder()
                .name(quiz.isPresent()? quiz.get().getName(): null)
                .fullname(user.isPresent()? (user.get().getFirstname()+" "+user.get().getLastname()): null)
                .time(quizRecord.get().getTaken_date())
                .questions(questionDones)
                .score(quizRecord.get().getScore())
                .build();
    }


    @GetMapping("/quiz")
    @PreAuthorize("hasAuthority('read')")
    public AllQuizzesResponse getAllQuizzesByUser() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null) {
            return AllQuizzesResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("Not logged in")
                            .build())
                    .build();
        }

        Optional<User> user = userService.getUserByUsername(authentication.getName());
        List<Quiz> quizzes = new ArrayList<>();
        if(user.isPresent()) {
            List<QuizRecord> quizRecords = quizService.getAllQuizRecordsByUser(user.get().getUser_id());
            if(quizRecords.size()>0) {
                Set<Integer> ids = new HashSet<>();
                for(QuizRecord qr: quizRecords) {
                    if(!ids.contains(qr.getQuiz_id())) {
                        ids.add(qr.getQuiz_id());
                        Optional<Quiz> quiz = quizService.getQuizById(qr.getQuiz_id());
                        if(quiz.isPresent()) {
                            quizzes.add(quiz.get());
                        }
                    }
                }
            }
        }

        return AllQuizzesResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all quizzes for user")
                        .build())
                .quizzes(quizzes)
                .build();
    }



    private Integer getChoice(List<QuestionSubmit> qs, Integer question_id) {
        for(QuestionSubmit q: qs) {
            if(q.getQuestion_id() == question_id)
                return q.getOption_id();
        }
        return null;
    }

}
