package com.quizapplication.quizapp.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizapplication.quizapp.Entity.Question;
import com.quizapplication.quizapp.Entity.Quiz;
import com.quizapplication.quizapp.Service.QuizService;

@CrossOrigin(origins = {"http://localhost:59814", "http://10.0.2.2:8080"})
@RestController
@RequestMapping("/quiz")
public class QuizController {
    
    @Autowired
    private QuizService quizService;

    @PostMapping("create-quiz/{categoryId}")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz,@PathVariable("categoryId")Long categoryId){

        Quiz createQuiz = quizService.createQuiz(quiz,categoryId);

        return new ResponseEntity<Quiz>(createQuiz, HttpStatus.CREATED);
    }

    @GetMapping("category-quizs/{categoryId}")
    public ResponseEntity<List<Quiz>> findQuizByCategoryHandler(@PathVariable("categoryId")Long categoryId){

        List<Quiz>quizs = quizService.findQuizByCategory(categoryId);

        return new ResponseEntity<List<Quiz>>(quizs, HttpStatus.FOUND);
    }

    @GetMapping("all-quizQue/{quizId}")
    public ResponseEntity<List<Question>> findAllQuestionOFQuiz(@PathVariable("quizId")Long quizId){

        List<Question>questions = quizService.allQuestionsOfQuiz(quizId);

        return new ResponseEntity<List<Question>>(questions, HttpStatus.FOUND);
    }

    @PutMapping("update-quiz/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz,@PathVariable("quizId")Long quizId){

        Quiz updateQuiz = quizService.updateQuiz(quiz, quizId);

        return new ResponseEntity<Quiz>(updateQuiz, HttpStatus.OK);
    }

}
