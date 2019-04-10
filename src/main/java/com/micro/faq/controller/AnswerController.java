package com.micro.faq.controller;

import com.micro.faq.exception.ResourceNotFoundException;
import com.micro.faq.model.Answer;
import com.micro.faq.repository.AnswerRepository;
import com.micro.faq.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions/{questionId}/answers")
    public List<Answer> getAnswerByQuestionId(@PathVariable Long questionId){
        return answerRepository.findByQuestionId(questionId);
    }

    @PostMapping("/questions/{questionId}/answers")
    public Answer addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answer){
        return questionRepository.findById(questionId)
                .map(question -> {
                    answer.setQuestion(question);
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Question with id %d not found", questionId)));
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public Answer updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId, @Valid @RequestBody Answer answerReq){
        if(!questionRepository.existsById(questionId))
            throw new ResourceNotFoundException(String.format("Question with id %d not found", questionId));

        return answerRepository.findById(answerId)
                .map(answer -> {
                    answer.setText(answerReq.getText());
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Answer with id %d not found", answerId)));
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId){
        if(!questionRepository.existsById(questionId))
            throw new ResourceNotFoundException(String.format("Question with id %d not found", questionId));

        return answerRepository.findById(answerId)
                .map(answer -> {
                    answerRepository.delete(answer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Answer with id %d not found", answerId)));
    }
}
