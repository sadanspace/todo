package com.code.practice;

import cn.hutool.core.util.NumberUtil;

public class Todo implements Command {

    public static void main(String[] args) {
        new Todo().execute(args);
    }

    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("usage todo [args...]");
            return;
        }
        String arg0 = args[0];

        TodoFile todoFile = new TodoFile();

        switch (arg0) {
            case "add":
                System.out.println("add\n");
                StringBuilder item = new StringBuilder();
                if (args.length > 1) {
                    for (int i = 1; i < args.length; i++) {
                        item.append(args[i]);
                    }

                    todoFile.appendItem(item.toString());
                } else {
                    System.out.println("add help info...");
                }
                break;
            case "done":
                System.out.println("done\n");
                if (args.length == 2){
                    String args1 = args[1];
                    boolean isInteger = NumberUtil.isInteger(args1);
                    if (isInteger) {
                        Integer index = Integer.valueOf(args1);
                        todoFile.itemDoneByIndex(index);
                    } else {
                        System.out.println("index must be a int type data.");
                    }
                } else {
                    System.out.println("done help info...");
                }
                break;
            case "list":
                System.out.println("list\n");
                if (args.length > 1){
                    if (args.length == 2 && args[1].equals("--all")){
                        todoFile.showAllItem();
                    } else {
                        System.out.println("you should use: list --all");
                    }
                } else {
                    todoFile.showTodoItem();
                }
                break;
            default:
                System.out.println("default");
                break;
        }

    }

}