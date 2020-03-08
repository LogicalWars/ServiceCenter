

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class DataBase {

    public String nameUser;
    public boolean Error = true;
    public ArrayList<ArrayList<String>> dataBase = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> dataAnswer = new ArrayList<>();

    public void readInList() throws IOException {

        dataBase.clear(); // clear collection @dataBase

        for (int i = 0; i<12 ; i++) {
            dataBase.add(new ArrayList<>());
        }

        /**Index 0  -  A list of questions
         * Index 1  -  List of Answers No. 1
         * Index 2  -  List of Question No. 2
         * Index 3  -  List of Question No. 3
         * Index 4  -  List of correct answers
         * Index 5  -  Image List
         * Index 6  -  List All Results
         * Index 7  -  A list of nameUser
         * Index 8  -  Current result List
         * Index 9  -  Random List
         * Index 10  -  A list of Date
         * Index 11  -  A list of Passwords
         * */

        String questionStr;
        BufferedReader inQuestion = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/question.xml"), "UTF-8"));
        while ((questionStr = inQuestion.readLine()) != null) {
            dataBase.get(0).add(questionStr);
        }
        inQuestion.close();

        String answerStr;
        BufferedReader inAnswer = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/answer.xml"), "UTF-8"));
        for (int i = 0; i < dataBase.get(0).size(); i++) {
            if ((answerStr = inAnswer.readLine()) != null) {
                String[] answer = answerStr.split("#");
                dataBase.get(1).add(answer[0]);
                dataBase.get(2).add(answer[1]);
                dataBase.get(3).add(answer[2]);
            }else{
                dataBase.get(1).add("Error");
                dataBase.get(2).add("Error");
                dataBase.get(3).add("Error");
                Error = false;
            }
        }
        inAnswer.close();

        String correctAnswerStr;
        BufferedReader inCorrectAnswer = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/correctAnswer.xml"), "UTF-8"));
        for (int i = 0; i < dataBase.get(0).size(); i++) {
            if ((correctAnswerStr = inCorrectAnswer.readLine()) != null) {
                dataBase.get(4).add(correctAnswerStr);
            }else{
                dataBase.get(4).add("Error");
                Error = false;
            }
        }
        inCorrectAnswer.close();

        String imgStr;
        BufferedReader inImg = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/img.xml"), "UTF-8"));
        for (int i = 0; i < dataBase.get(0).size(); i++) {
            if ((imgStr = inImg.readLine()) != null) {
                dataBase.get(5).add(imgStr);
            }else{
                dataBase.get(5).add("Error");
                Error = false;
            }
        }
        inImg.close();

        String resultsStr;
        BufferedReader inResults = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/results.xml" ), "UTF-8"));
        while ((resultsStr = inResults.readLine()) != null) {
            String results[] = resultsStr.split("%");
            String[] s = results[1].split("#");
            dataBase.get(10).add(s[s.length-1]);
            dataBase.get(6).add(results[1]);
            dataBase.get(7).add(results[0]);
        }
        inResults.close();

        String passwordsStr;
        BufferedReader inPasswords = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/pass.xml"), "UTF-8"));
            while ((passwordsStr = inPasswords.readLine()) != null) {
                dataBase.get(11).add(passwordsStr);
            }
        inPasswords.close();

        for (int i = 0; i <dataBase.get(0).size() ; i++) {
            dataBase.get(8).add(null);
        }

        for (int i = 0; i <dataBase.get(0).size() ; i++) {
            dataBase.get(9).add(null);
        }

        for (byte i = 0; i<dataBase.get(0).size(); i++){
            int a = (int)(Math.random()*dataBase.get(0).size());
            String b = String.valueOf(a);
            if (dataBase.get(9).contains(b)){
                while(dataBase.get(9).get(i)==null) {
                    a = (int) (Math.random() * dataBase.get(0).size());
                    b = String.valueOf(a);
                    if (!dataBase.get(9).contains(b)) {
                        dataBase.get(9).set(i, b);
                    }
                }
            }else{
                dataBase.get(9).set(i, b);
            }
        }
    }

    public void countAnswer(int index){

        dataAnswer.clear(); // clear collection @wAnswer

        dataAnswer.add(new ArrayList<Integer>()); //wrongAnswerInt              index 0
        dataAnswer.add(new ArrayList<Integer>()); //numberOfCorrectAnswers      index 1
        dataAnswer.add(new ArrayList<Integer>()); //numberOfWrongAnswers        index 2
        dataAnswer.add(new ArrayList<Integer>()); //correctAnswerInt            index 3

        for (int i = 0; i <dataBase.get(0).size() ; i++) {
            dataAnswer.get(0).add(0);
        }

        String[] s = dataBase.get(6).get(index).split("#");
        for (byte i=0; i<s.length-1; i++) {
            String[] k = s[i].split("_");
                int r1 = Integer.parseInt(k[0]);
                int r2 = Integer.parseInt(k[1]);
            if (r1 < dataBase.get(0).size()) {
                dataAnswer.get(0).set(r1, r2);
            }else{
                System.out.println("Exception read results");
                break;
            }
        }


        int w=0;
        for (int i = 0; i < dataAnswer.get(0).size() ; i++) {
            if (dataAnswer.get(0).get(i) != 0){
                w++;
            }
        }
        int c = dataBase.get(0).size() - w;

        dataAnswer.get(1).add(c);
        dataAnswer.get(2).add(w);

        for (int i = 0; i <dataBase.get(0).size() ; i++) {
            int r = Integer.parseInt(dataBase.get(4).get(i));
            dataAnswer.get(3).add(r);
        }
    }

    /**МЕТОД ЗАПИСЫВАЕТ ДАННЫЕ В ФАЙЛ results.xml*/

    public void writeData() throws IOException {
        BufferedWriter in = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/results.xml", true), "UTF-8"));
        if(dataBase.get(6).size()>0) {
            in.write("\n");
        }
        for (byte i=0; i<dataBase.get(8).size(); i++) {
            if (dataBase.get(8).get(i)!=null){
                in.write(dataBase.get(8).get(i));
            }
        }
        String calendarData = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        in.write(calendarData);
        in.close();
    }

    /**МЕТОД ПЕРЕЗАПИСЫВАЕТ ФАЙЛЫ ПРИ НАЖАТИИ НА КНОПКУ "Изменить"*/

    public void recordingData(int index, String question, String Answer0, String Answer1, String Answer2, String correctAns, String imgName) throws IOException{
        dataBase.get(0).set(index, question);
        dataBase.get(1).set(index, Answer0);
        dataBase.get(2).set(index, Answer1);
        dataBase.get(3).set(index, Answer2);
        dataBase.get(4).set(index, correctAns);
        dataBase.get(5).set(index, imgName);

        BufferedWriter inQuestion = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/question.xml"), "UTF-8"));
        for (byte i=0; i<dataBase.get(0).size(); i++) {
            inQuestion.write(dataBase.get(0).get(i) + "\n");
        }
        inQuestion.close();

        BufferedWriter inCorrectAnswer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/correctAnswer.xml"), "UTF-8"));
        for (byte i=0; i<dataBase.get(0).size(); i++) {
            inCorrectAnswer.write(dataBase.get(4).get(i) + "\n");
        }
        inCorrectAnswer.close();

        BufferedWriter inAnswer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/answer.xml"), "UTF-8"));
        for (byte i=0; i<dataBase.get(0).size(); i++) {
            for (int j = 1; j < 4 ; j++) {
                inAnswer.write(dataBase.get(j).get(i)+"#");
            }
            inAnswer.write("\n");
        }
        inAnswer.close();

        BufferedWriter inImg = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/img.xml"), "UTF-8"));
        for (byte i = 0; i< dataBase.get(0).size(); i++) {
            inImg.write(dataBase.get(5).get(i) + "\n");
        }
        inImg.close();

    }

    /**МЕТОД УДАЛЯЕТ СТРОКУ С ФАЙЛА results*/

    public void recordingResults(int Index)throws IOException{

        ArrayList<String> resultsList = new ArrayList<>();
        String resultsStr;

        BufferedReader outResults = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/results.xml" ), "UTF-8"));
        while ((resultsStr = outResults.readLine()) != null) {//Чтение построчно методом readLine()
            resultsList.add(resultsStr);
        }
        outResults.close();

        resultsList.remove(Index);

        BufferedWriter inResults = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/results.xml"), "UTF-8"));
        for (byte i=0; i<resultsList.size(); i++) {
            if (i != (resultsList.size()-1)) {
                inResults.write(resultsList.get(i) + "\n");
            }else{
                inResults.write(resultsList.get(i));
            }
        }
        inResults.close(); // Поток обязательно закрыть
    }

    /**МЕТОД ДОБАВЛЯЕТ НОВЫЙ ВОПРОС*/

    public void addQuestion()throws IOException{

        ArrayList<ArrayList<String>> addQuestion = new ArrayList<>();
        addQuestion.add(new ArrayList<String>());// Question                index 0
        addQuestion.add(new ArrayList<String>());// Answer                  index 1
        addQuestion.add(new ArrayList<String>());// CorrectAnswer           index 2
        addQuestion.add(new ArrayList<String>());// Img                     index 3

        String question;
        BufferedReader inQuestion = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/question.xml" ), "UTF-8"));
        while ((question = inQuestion.readLine()) != null) {
            addQuestion.get(0).add(question);
        }
        inQuestion.close();

        String answer;
        BufferedReader inAnswer = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/answer.xml" ), "UTF-8"));
        while ((answer = inAnswer.readLine()) != null) {
            addQuestion.get(1).add(answer);
        }
        inAnswer.close();

        String correctAnswer;
        BufferedReader inCorrectAnswer = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/correctAnswer.xml" ), "UTF-8"));
        while ((correctAnswer = inCorrectAnswer.readLine()) != null) {
            addQuestion.get(2).add(correctAnswer);
        }
        inCorrectAnswer.close();

        String img;
        BufferedReader inImg = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/img.xml" ), "UTF-8"));
        while ((img = inImg.readLine()) != null) {
            addQuestion.get(3).add(img);
        }
        inImg.close();

        addQuestion.get(0).add("New question");
        addQuestion.get(1).add("Answer_1#Answer_2#Answer_3#");
        addQuestion.get(2).add("1");
        addQuestion.get(3).add("1.JPG");

        BufferedWriter outQuestion = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/question.xml"), "UTF-8"));
        for (byte i=0; i<addQuestion.get(0).size(); i++) {
            if (addQuestion.get(0).get(i)!=null){
                if ((addQuestion.get(0).size() - 1)!=i) {
                    outQuestion.write(addQuestion.get(0).get(i) + "\n");
                }else{
                    outQuestion.write(addQuestion.get(0).get(i));
                }
            }
        }
        outQuestion.close();

        BufferedWriter outAnswer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/answer.xml"), "UTF-8"));
        for (byte i=0; i<addQuestion.get(1).size(); i++) {
            if (addQuestion.get(1).get(i)!=null){
                if ((addQuestion.get(1).size() - 1)!=i) {
                    outAnswer.write(addQuestion.get(1).get(i) + "\n");
                }else{
                    outAnswer.write(addQuestion.get(1).get(i));
                }
            }
        }
        outAnswer.close();

        BufferedWriter outCorrectAnswer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/correctAnswer.xml"), "UTF-8"));
        for (byte i=0; i<addQuestion.get(2).size(); i++) {
            if (addQuestion.get(2).get(i)!=null){
                if ((addQuestion.get(2).size() - 1)!=i) {
                    outCorrectAnswer.write(addQuestion.get(2).get(i) + "\n");
                }else{
                    outCorrectAnswer.write(addQuestion.get(2).get(i));
                }
            }
        }
        outCorrectAnswer.close();

        BufferedWriter outImg = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/img.xml"), "UTF-8"));
        for (byte i=0; i<addQuestion.get(3).size(); i++) {
            if (addQuestion.get(3).get(i)!=null){
                if ((addQuestion.get(3).size() - 1)!=i) {
                    outImg.write(addQuestion.get(3).get(i) + "\n");
                }else{
                    outImg.write(addQuestion.get(3).get(i));
                }
            }
        }
        outImg.close();
    }

    /**МЕТОД УДАЛЯЕТ НОВЫЙ ВОПРОС*/

    public void deleteQuestion(int index) throws IOException{
        ArrayList<ArrayList<String>> delQuestion = new ArrayList<>();
        delQuestion.add(new ArrayList<String>());// Question                index 0
        delQuestion.add(new ArrayList<String>());// Answer                  index 1
        delQuestion.add(new ArrayList<String>());// CorrectAnswer           index 2
        delQuestion.add(new ArrayList<String>());// Img                     index 3

        String question;
        BufferedReader inQuestion = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/question.xml" ), "UTF-8"));
        while ((question = inQuestion.readLine()) != null) {
            delQuestion.get(0).add(question);
        }
        inQuestion.close();

        String answer;
        BufferedReader inAnswer = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/answer.xml" ), "UTF-8"));
        while ((answer = inAnswer.readLine()) != null) {
            delQuestion.get(1).add(answer);
        }
        inAnswer.close();

        String correctAnswer;
        BufferedReader inCorrectAnswer = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/correctAnswer.xml" ), "UTF-8"));
        while ((correctAnswer = inCorrectAnswer.readLine()) != null) {
            delQuestion.get(2).add(correctAnswer);
        }
        inCorrectAnswer.close();

        String img;
        BufferedReader inImg = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/helpdesk/database/testing/img.xml" ), "UTF-8"));
        while ((img = inImg.readLine()) != null) {
            delQuestion.get(3).add(img);
        }
        inImg.close();

        delQuestion.get(0).remove(index);
        delQuestion.get(1).remove(index);
        delQuestion.get(2).remove(index);
        delQuestion.get(3).remove(index);

        BufferedWriter outQuestion = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/question.xml"), "UTF-8"));
        for (byte i=0; i<delQuestion.get(0).size(); i++) {
            if (delQuestion.get(0).get(i)!=null){
                if ((delQuestion.get(0).size() - 1)!=i) {
                    outQuestion.write(delQuestion.get(0).get(i) + "\n");
                }else{
                    outQuestion.write(delQuestion.get(0).get(i));
                }
            }
        }
        outQuestion.close();

        BufferedWriter outAnswer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/answer.xml"), "UTF-8"));
        for (byte i=0; i<delQuestion.get(1).size(); i++) {
            if (delQuestion.get(1).get(i)!=null){
                if ((delQuestion.get(1).size() - 1)!=i) {
                    outAnswer.write(delQuestion.get(1).get(i) + "\n");
                }else{
                    outAnswer.write(delQuestion.get(1).get(i));
                }
            }
        }
        outAnswer.close();

        BufferedWriter outCorrectAnswer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/correctAnswer.xml"), "UTF-8"));
        for (byte i=0; i<delQuestion.get(2).size(); i++) {
            if (delQuestion.get(2).get(i)!=null){
                if ((delQuestion.get(2).size() - 1)!=i) {
                    outCorrectAnswer.write(delQuestion.get(2).get(i) + "\n");
                }else{
                    outCorrectAnswer.write(delQuestion.get(2).get(i));
                }
            }
        }
        outCorrectAnswer.close();

        BufferedWriter outImg = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/com/helpdesk/database/testing/img.xml"), "UTF-8"));
        for (byte i=0; i<delQuestion.get(3).size(); i++) {
            if (delQuestion.get(3).get(i)!=null){
                if ((delQuestion.get(3).size() - 1)!=i) {
                    outImg.write(delQuestion.get(3).get(i) + "\n");
                }else{
                    outImg.write(delQuestion.get(3).get(i));
                }
            }
        }
        outImg.close();
    }
}