package web.app.restfulquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.*;
import web.app.restfulquiz.domain.request.QuestionRequest;
import web.app.restfulquiz.domain.request.QuestionUpdateRequest;
import web.app.restfulquiz.domain.response.*;
import web.app.restfulquiz.exception.UserNotFoundException;
import web.app.restfulquiz.service.OptionService;
import web.app.restfulquiz.service.QuestionService;
import web.app.restfulquiz.service.QuizService;
import web.app.restfulquiz.service.UserService;
import web.app.restfulquiz.service.impl.OptionServiceImpl;
import web.app.restfulquiz.service.impl.QuestionServiceImpl;
import web.app.restfulquiz.service.impl.QuizServiceImpl;
import web.app.restfulquiz.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private QuizService quizService;
    private QuestionService questionService;
    private OptionService optionService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl,
                           QuizServiceImpl quizServiceImpl,
                           QuestionServiceImpl questionServiceImpl,
                           OptionServiceImpl optionServiceImpl) {
        this.userService = userServiceImpl;
        this.quizService = quizServiceImpl;
        this.questionService = questionServiceImpl;
        this.optionService = optionServiceImpl;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('read')")
    public AllUsersResponse getAllUsers() {
        List<User> users = userService.getAllUsers();

        return AllUsersResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all users")
                        .build())
                .users(users)
                .build();
    }


    @GetMapping(value = "/user", params = "user_id")
    @PreAuthorize("hasAuthority('read')")
    public UserWithoutPassResponse getUserById(@RequestParam Integer user_id) throws UserNotFoundException {
        Optional<User> user = userService.getUserById(user_id);

        if(!user.isPresent())
            throw new UserNotFoundException("User not found");
//            return UserWithoutPassResponse.builder()
//                    .status(ResponseStatus.builder()
//                            .success(false)
//                            .message("No user found")
//                            .build())
//                    .build();

        return UserWithoutPassResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get the user")
                        .build())
                .user(new UserWithoutPass(user.get()))
                .build();
    }


    @PatchMapping("/user")
    @PreAuthorize("hasAuthority('update')")
    public UserResponse updateUserStatus(@RequestParam Integer user_id) throws UserNotFoundException{
        Optional<User> user = userService.updateUserStatus(user_id);

        if(!user.isPresent())
            throw new UserNotFoundException("User not found");
//            return UserResponse.builder()
//                    .status(ResponseStatus.builder()
//                            .success(false)
//                            .message("User not found")
//                            .build())
//                    .build();

        return UserResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("User status updated")
                        .build())
                .user(user.get())
                .build();
    }


    @GetMapping("/quiz")
    @PreAuthorize("hasAuthority('read')")
    public AllQuizzesResponse getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        return AllQuizzesResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all quizzes")
                        .build())
                .quizzes(quizzes)
                .build();
    }


    @GetMapping(value = "/quiz", params = "category_id")
    @PreAuthorize("hasAuthority('read')")
    public AllQuizzesResponse getQuizzesByCategory(@RequestParam Integer category_id) {
        List<Quiz> quizzes = quizService.getQuizzesByCategory(category_id);

        if(quizzes.size()==0)
            return AllQuizzesResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("Not a valid category")
                            .build())
                    .quizzes(quizzes)
                    .build();

        return AllQuizzesResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all quizzes under the category")
                        .build())
                .quizzes(quizzes)
                .build();
    }


    @GetMapping(value = "/quiz", params = "user_id")
    @PreAuthorize("hasAuthority('read')")
    public AllQuizzesResponse getQuizzesByUser(@RequestParam Integer user_id) {
        Optional<User> user = userService.getUserById(user_id);
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
        } else {
            return AllQuizzesResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("User not found")
                            .build())
                    .build();
        }

        return AllQuizzesResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all quizzes for user")
                        .build())
                .quizzes(quizzes)
                .build();
    }


    @PatchMapping("/question")
    @PreAuthorize("hasAuthority('update')")
    public QuestionResponse updateQuestion(@RequestBody QuestionUpdateRequest request,
                                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(error -> System.out.println(
                    "ValidationError in " + error.getObjectName() + ": " + error.getDefaultMessage()));
            return QuestionResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("Validation error")
                            .build())
                    .build();
        }

        Optional<Question> question = questionService.updateQuestion(request);
        // option update needed
        if(!question.isPresent())
            return QuestionResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("Question not found")
                            .build())
                    .build();

        if(request.getOptions().size()>0) {
            for(Option op: request.getOptions()) {
                optionService.updateOption(op);
            }
        }

        return QuestionResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Update the question")
                        .build())
                .question(question.get())
                .build();

    }


    @GetMapping(value = "/question")
    @PreAuthorize("hasAuthority('read')")
    public AllQuestionsResponse getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();

        return AllQuestionsResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all questions")
                        .build())
                .questions(questions)
                .build();
    }


    @PostMapping("/question")
    @PreAuthorize("hasAuthority('write')")
    public QuestionResponse createQuestion(@Valid @RequestBody QuestionRequest request,
                                           BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(error -> System.out.println(
                    "ValidationError in " + error.getObjectName() + ": " + error.getDefaultMessage()));
            return QuestionResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("Validation error")
                            .build())
                    .build();
        }

        Optional<Question> question = questionService.createQuestion(request);
        if(question.isPresent()) {
            optionService.createOption(request, question.get().getQuestion_id());
        }

        return QuestionResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Create a question")
                        .build())
                .question(question.get())
                .build();
    }


}
