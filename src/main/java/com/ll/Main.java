package com.ll;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
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

    public int hashCode(){
        return id;
    }
    public boolean equals(Object obj){
        SayingInfo cmp = (SayingInfo) obj;
        if(id == cmp.id){
            return true;
        }
        else
            return false;
    }
}

class AppManager {
    private final File dataFile = new File("data.dat");
    HashSet<SayingInfo> infoStorage = new HashSet<>();

    static AppManager inst = null;
    public static Scanner sc = new Scanner(System.in);

    public static AppManager createManagerInst() {
        if (inst == null)
            inst = new AppManager();
        return inst;
    }

    int inputId = 1;

    AppManager(){
        readFromFile();
    }

    private void readFromFile() {
        if(dataFile.exists()==false){
            return;
        }
        try{
            FileInputStream file = new FileInputStream(dataFile);
            ObjectInputStream in = new ObjectInputStream(file);

            while(true){
                SayingInfo info = (SayingInfo) in.readObject();
                if(info==null)
                    break;
                infoStorage.add(info);
                inputId = info.id;
                inputId++;
            }
            in.close();
        }
        catch(IOException | ClassNotFoundException e){
            return;
        }
    }

    public void inputSaying() {
        System.out.print("명언 : ");
        String saying = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();
        SayingInfo info = new SayingInfo(inputId, saying, author);

        infoStorage.add(info);
        System.out.printf("%d번 명언이 등록되었습니다.\n", inputId);
        inputId++;
    }

    public void list() {
        Iterator<SayingInfo> itr = infoStorage.iterator();
        while (itr.hasNext()) {
            SayingInfo info = itr.next();
            info.showInfo();
        }
    }

    public void deleteSaying(String n) {

        int id = Integer.parseInt(n);
        Iterator<SayingInfo> itr = infoStorage.iterator();
        while (itr.hasNext()) {
            SayingInfo delete = itr.next();
            if (id == delete.id) {
                itr.remove();
                System.out.println(id+"번 명언이 삭제되었습니다.");
                return;
            }
        }
        System.out.println(id+"번 명언은 존재하지 않습니다.");
    }

    public SayingInfo searchSaying(int id) {
        Iterator<SayingInfo> itr = infoStorage.iterator();
        while (itr.hasNext()) {
            SayingInfo info = itr.next();
            if (id == info.id) {
                return info;
            }
        }
        return null;
    }

    public void changeSaying(String n) {
        int id = Integer.parseInt(n);
        SayingInfo info = searchSaying(id);
        System.out.println("명언(기존) : " + info.saying);
        System.out.print("명언 : ");
        String newSaying = sc.nextLine();
        System.out.println("작가(기존) : " + info.author);
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine();
        info.saying = newSaying;
        info.author = newAuthor;
    }


    public void storeToFile() {
        try{
            FileOutputStream file = new FileOutputStream(dataFile);
            ObjectOutputStream out = new ObjectOutputStream(file);

            Iterator<SayingInfo> itr = infoStorage.iterator();
            while(itr.hasNext()){
                out.writeObject(itr.next());
            }
            out.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
    public class Main {
        public static void main(String[] args) {
            AppManager app = AppManager.createManagerInst();
            String choice;
            String another;
            String id;
            System.out.println("== 명언 앱 ==");

            while (true) {
                System.out.print("명령) ");
                choice = AppManager.sc.nextLine();
                another = String.valueOf(choice.charAt(0)) + choice.charAt(1);

                switch (another) {
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
                        app.storeToFile();
                        return;
                }
            }
        }
    }