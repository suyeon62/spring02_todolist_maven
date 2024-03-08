package com.company.todo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.company.todo.dto.TodoDTO;

@Mapper
@Repository
public interface TodoRepository {
	public List<TodoDTO> getTodoList() throws Exception;
	public int insertTodoList(TodoDTO dto) throws Exception;
	public int updateTodoList(TodoDTO dto) throws Exception;
	public int deleteTodoList(int id) throws Exception;

}
