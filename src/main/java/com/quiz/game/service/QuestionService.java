package com.quiz.game.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.game.dao.QuestionDao;
import com.quiz.game.model.Question;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);
	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);
	}

	public ResponseEntity<String> addQuestion(Question question) {
		try {
			questionDao.save(question);
			return new ResponseEntity<>("Record Added Successfully...!", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Not Inserted Anything...!", HttpStatus.BAD_GATEWAY);
	}

	public ResponseEntity<String> updateQuestion(Question question) {
		try {
			questionDao.save(question);
			return new ResponseEntity<>("Question Updated Successfully...!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Not Updated Anything...!", HttpStatus.BAD_GATEWAY);
	}

	public ResponseEntity<String> deleteQuestionById(int id) {
		try {
			questionDao.deleteById(id);
			return new ResponseEntity<>("Record Deleted Successfully...!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Nothing is Deleted...!", HttpStatus.BAD_GATEWAY);
	}

}
