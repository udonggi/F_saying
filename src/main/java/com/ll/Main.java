package com.ll;

import java.io.Serializable;
import java.util.Scanner;

class SayingInfo implements Serializable {
    String author;
    String saying;
    int id;
    public SayingInfo(int num, String saying, String author){
        this.id = num;
        this.saying = saying;
        this.author = author;
    }
    public void showInfo(){
        System.out.println(id+" / "+saying+" / "+author);
    }
}

class AppManager{
    public static Scanner sc = new Scanner(System.in);

    SayingInfo[] infoStorage = new SayingInfo[50];
    int num = 0;
    int inputId = 1;

    public void inputSaying(){
        System.out.printf("명언 : ");
        String saying = sc.nextLine();
        System.out.printf("작가 : ");
        String author = sc.nextLine();
        SayingInfo info = new SayingInfo(inputId, saying, author);

        infoStorage[num]=info;
        System.out.printf("%d번 명언이 등록되었습니다.\n",inputId);
        num++;
        inputId++;
    }

    public void list() {
        for(int i = 0; i <num; i++){
            infoStorage[i].showInfo();
        }
    }

    public void deleteSaying(String n) {

        int id = Integer.parseInt(n);
        int data_num = searchSaying(id);
        if(data_num==-1){
            System.out.printf("%d번 명언은 존재하지 않습니다.\n",id);
        }
        else {
            for (int i = data_num; data_num < (num - 1); data_num++) {
                infoStorage[data_num] = infoStorage[data_num + 1]; //삭제한 데이터의 뒤 데이터 당기기
            }
            num--;
            System.out.println(id+"번 명언이 삭제되었습니다.");
        }
    }

    public int searchSaying(int id){
        for(int i = 0; i <num; i++){
            SayingInfo searchInfo = infoStorage[i];
            if(id == searchInfo.id){
                return i;
            }
        }
        return -1;
    }

    public void changeSaying(String n) {
        int id = Integer.parseInt(n);
        int data_num = searchSaying(id);
        System.out.println("명언(기존) : "+infoStorage[data_num].saying);
        System.out.printf("명언 : ");
        String newSaying = sc.nextLine();
        System.out.println("작가(기존) : "+infoStorage[data_num].author);
        System.out.printf("작가 : ");
        String newAuthor = sc.nextLine();
        infoStorage[data_num].saying = newSaying;
        infoStorage[data_num].author = newAuthor;
    }


}

public class Main {
    public static void main(String[] args){
        AppManager app = new AppManager();
        String choice;
        String another;
        String id;
        System.out.println("== 명언 앱 ==");

        while(true){
            System.out.printf("명령) ");
            choice = AppManager.sc.nextLine();
            another = String.valueOf(choice.charAt(0))+choice.charAt(1);

            switch(another){
                case "등록":
                    app.inputSaying();
                    break;
                case "목록":
                    System.out.println("번호  /   작가  /   명언");
                    System.out.println("------------------------");
                    app.list();
                    break;
                case "삭제":
                    id = String.valueOf(choice.charAt(6));
                    app.deleteSaying(id);
                    break;
                case "수정":
                    id = String.valueOf(choice.charAt(6));
                    app.changeSaying(id);
                    break;
                case "종료":
                    return;
            }
        }
    }
}