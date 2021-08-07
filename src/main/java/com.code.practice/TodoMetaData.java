package com.code.practice;

public class TodoMetaData {
    private int maxIndex;
    private int allItemCount;
    private int todoItemCount;
    private int doneItemCount;

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public int getAllItemCount() {
        return allItemCount;
    }

    public void setAllItemCount(int allItemCount) {
        this.allItemCount = allItemCount;
    }

    public int getTodoItemCount() {
        return todoItemCount;
    }

    public void setTodoItemCount(int todoItemCount) {
        this.todoItemCount = todoItemCount;
    }

    public int getDoneItemCount() {
        return doneItemCount;
    }

    public void setDoneItemCount(int doneItemCount) {
        this.doneItemCount = doneItemCount;
    }

    @Override
    public String toString() {
        return "TodoMetaData{" +
                "maxIndex=" + maxIndex +
                ", allItemCount=" + allItemCount +
                ", todoItemCount=" + todoItemCount +
                ", doneItemCount=" + doneItemCount +
                '}';
    }
}
