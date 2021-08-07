package com.code.practice;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TodoFile {

    private String fileName;
    private TodoMetaData metaData;

    public TodoFile() {
        this("todoList");
    }

    public TodoFile(String fileName) {
        this.fileName = fileName;

        writeTodoMetaData();
    }


    public TodoMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(TodoMetaData metaData) {
        this.metaData = metaData;
    }

    private void writeTodoMetaData() {
        boolean exist = FileUtil.exist(fileName);
        if (!exist) {
            File todoF = FileUtil.touch(fileName);
            TodoMetaData todoMetaData = new TodoMetaData();
            String jsonStr = JSONUtil.toJsonStr(todoMetaData);
            FileAppender appender = new FileAppender(todoF, 1, true);
            appender.append(jsonStr);
            appender.flush();
        }

    }

    public void appendItem(String item) {
        TodoItem todoItem = new TodoItem(item);
        appendLine(todoItem);
        String itemLine = String.format(
                "%s. %s\n\nItem %s added",
                todoItem.getIndex(), todoItem.getItem(), todoItem.getItem());
        System.out.println(itemLine);
    }

    private void appendLine(TodoItem todoItem) {
        TodoMetaData metaData = readMetaData();
        int maxIndex = metaData.getMaxIndex();

        todoItem.setIndex(maxIndex + 1);

        File todoF = FileUtil.file(fileName);
        String itemJsonStr = JSONUtil.toJsonStr(todoItem);
        FileAppender appender = new FileAppender(todoF, 1, true);
        appender.append(itemJsonStr);
        appender.flush();

        // refresh metadata
        Path filePath = Paths.get(todoF.getPath());
        try {
            List<String> lines = Files.readAllLines(filePath);
            metaData.setMaxIndex(maxIndex + 1);
            metaData.setAllItemCount(metaData.getAllItemCount() + 1);
            metaData.setTodoItemCount(metaData.getTodoItemCount() + 1);
            String metaJsonStr = JSONUtil.toJsonStr(metaData);
            lines.set(0, metaJsonStr);
            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TodoMetaData readMetaData() {
        FileReader fileReader = new FileReader(fileName);
        String firstLine = fileReader.readString();

        JSONObject jsonObject = JSONUtil.parseObj(firstLine);
        return jsonObject.toBean(TodoMetaData.class);
    }



    public void showTodoItem() {
        TodoMetaData todoMetaData = readMetaData();
        List<TodoItem> todoItems = readTodoItems();

        for (TodoItem todoItem : todoItems) {
            String itemLine = String.format(
                    "%s. %s",
                    todoItem.getIndex(), todoItem.getItem());
            System.out.println(itemLine);
        }

        String total = String.format(
                "\n\nTotal: %s items",
                todoMetaData.getTodoItemCount()
        );
        System.out.println(total);
    }

    public void showAllItem() {
        TodoMetaData todoMetaData = readMetaData();
        List<TodoItem> todoItems = readAllItem();

        for (TodoItem todoItem : todoItems) {
            String itemLine = String.format(
                    "%s. %s%s",
                    todoItem.getIndex(), todoItem.getStatus().equals(Status.DONE.name()) ? "[Done] " : "", todoItem.getItem());
            System.out.println(itemLine);
        }
        String total = String.format(
                "\n\nTotal: %s items, %s item done",
                todoMetaData.getAllItemCount(), todoMetaData.getDoneItemCount()
        );
        System.out.println(total);
    }

    private List<TodoItem> readAllItem() {
        List<TodoItem> todoItems = new ArrayList<>();
        File tf = FileUtil.file(fileName);
        Path filePath = Paths.get(tf.getPath());
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (int i = 1; i < lines.size(); i++) {
                JSONObject jsonObject = JSONUtil.parseObj(lines.get(i));
                TodoItem todoItem = jsonObject.toBean(TodoItem.class);
                todoItems.add(todoItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todoItems;
    }


    private List<TodoItem> readTodoItems() {
        List<TodoItem> todoItems = new ArrayList<>();
        File tf = FileUtil.file(fileName);
        Path filePath = Paths.get(tf.getPath());
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (int i = 1; i < lines.size(); i++) {
                JSONObject jsonObject = JSONUtil.parseObj(lines.get(i));
                TodoItem todoItem = jsonObject.toBean(TodoItem.class);
                if (todoItem.getStatus().equals(Status.TODO.name())) {
                    todoItems.add(todoItem);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todoItems;
    }

    public void itemDoneByIndex(Integer index) {
        TodoMetaData todoMetaData = readMetaData();
        File tf = FileUtil.file(fileName);
        Path filePath = Paths.get(tf.getPath());
        try {
            List<String> lines = Files.readAllLines(filePath);
            todoMetaData.setTodoItemCount(todoMetaData.getTodoItemCount() - 1);
            todoMetaData.setDoneItemCount(todoMetaData.getDoneItemCount() + 1);
            String metaJsonStr = JSONUtil.toJsonStr(todoMetaData);
            lines.set(0, metaJsonStr);

            String itemLine = lines.get(index);
            JSONObject jsonObject = JSONUtil.parseObj(itemLine);
            TodoItem todoItem = jsonObject.toBean(TodoItem.class);
            todoItem.setStatus(Status.DONE.name());
            String itemJsonStr = JSONUtil.toJsonStr(todoItem);
            lines.set(index, itemJsonStr);
            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String itemLine = String.format(
                "Item %s done.",
                index);
        System.out.println(itemLine);
    }
}
