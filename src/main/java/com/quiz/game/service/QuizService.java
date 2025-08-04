package com.quiz.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.game.dao.QuestionDao;
import com.quiz.game.dao.QuizDao;
import com.quiz.game.model.Question;
import com.quiz.game.model.QuestionWrapper;
import com.quiz.game.model.Quiz;
import com.quiz.game.model.Response;

@Service
public class QuizService {

	@Autowired
	QuizDao quizDao;

	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, String title) {

		List<Question> questions = questionDao.findRandomQuestionsByCategory(category);
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);
		return new ResponseEntity<>("Your Quiz has been Created Succeessfully", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {

		Optional<Quiz> quiz = quizDao.findById(id);
		List<Question> questionsFromDb = quiz.get().getQuestions();
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for (Question q : questionsFromDb) {
			QuestionWrapper qr = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
					q.getOption3(), q.getOption4());
			questionsForUser.add(qr);
		}
		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateScore(int id, List<Response> responses) {
		Quiz quiz = quizDao.findById(id).get();
		List<Question> questions = quiz.getQuestions();

		int right = 0;
		int i = 0;

		for (Response response : responses) {
			if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
				right++;
			}
			i++;
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}

}
