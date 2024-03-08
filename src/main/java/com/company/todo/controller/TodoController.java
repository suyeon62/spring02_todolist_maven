package com.company.todo.controller;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.todo.dto.TodoDTO;
import com.company.todo.service.TodoService;

import lombok.extern.slf4j.Slf4j;

/*
[TodoList API URI 설계]
조회   /todo/all                 GET
등록   /todo                     POST
수정   /todo/{id}/{completed}    PUT
삭제   /todo/{id}                DELETE
*/
@Slf4j
@RestController
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	public TodoController() {
		
	}
	
	//http://localhost:8090/todo/all
//	@GetMapping("/todo/all")
//	public List<TodoDTO> getList() throws Exception{
//		log.info("{}", todoService.search());
//		return todoService.search();
//	}
	
	@ResponseBody
	@GetMapping("/todo/all")
	public ResponseEntity<List<TodoDTO>> getList() throws Exception{
		log.info("{}", todoService.search());
		//return new ResponseEntity<List<TodoDTO>>(todoService.search(), HttpStatus.OK);
		//return ResponseEntity.ok(todoService.search());
		return ResponseEntity.ok().body(todoService.search());
	}
	
	//http://localhost:8090/todo
	//{"todoname":잠자기}
	@PostMapping("/todo")
	public ResponseEntity<HashMap<String, String>> postTodo(@RequestBody TodoDTO dto) throws Exception {
		int chk=todoService.insert(dto);
		
		HashMap<String, String> map=new HashMap();
		map.put("createDate", new Date().toString());
		map.put("message", "insert Ok");
		map.put("cnt", String.valueOf(chk));
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		if(chk>=1) {
			//return new ResponseEntity<HashMap<String, String>>(map, headers, HttpStatus.OK);
			return ResponseEntity.ok().headers(headers).body(map);
		}else {
			//return new ResponseEntity<HashMap<String,String>>(HttpStatus.NOT_ACCEPTABLE);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}
	
	///http://localhost:8090/todo/{id}/{completed}
	@PutMapping("/todo/{id}/{completed}")
	public ResponseEntity<Integer> putTodo(@PathVariable("id") int id, 
											@PathVariable("completed") int completed) throws Exception{
		TodoDTO dto=new TodoDTO();
		dto.setId(id);
		dto.setCompleted(completed==0?1:0);
		int rowCount=todoService.update(dto);
		//return new ResponseEntity<Integer>(rowCount, HttpStatus.OK);
		return ResponseEntity.ok(rowCount);
	}
	
	// http://localhost:8090/todo/1	
		@DeleteMapping("/todo/{id}")
		public ResponseEntity<Integer> deleteTodo(@PathVariable("id") int id) throws Exception{		
			int rowCount = todoService.delete(id);
			//return new ResponseEntity<Integer>(rowCount, HttpStatus.OK);
			return ResponseEntity.ok(rowCount);
		}

} //end class
