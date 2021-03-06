package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService service;
    @MockBean
    private TaskMapper taskMapper;
    private TaskDto taskDto;
    private Task task;

    @Before
    public void init() {
        taskDto = new TaskDto(1L, "title", "content");
        task = new Task(1L, "title", "content");
    }

    @Test
    public void shouldFetchEmptyTaskList() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(taskDtos);
        //When & Then
        mockMvc.perform(get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTasksList() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(taskDto);
        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(taskDtos);
        //When & Then
        mockMvc.perform(get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title")))
                .andExpect(jsonPath("$[0].content", is("content")));
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);
        when(service.getTask(1L)).thenReturn(Optional.of(task));
        //When & Then
        mockMvc.perform(get("/v1//tasks/taskId")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("taskId", "1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        when(service.getTask(1L)).thenReturn(Optional.of(task));
        //When & Then
        mockMvc.perform(delete("/v1//tasks/taskId")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        when(taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        when(service.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}