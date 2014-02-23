/**
 * Created by poriesto on 2/17/14.
 4. Количество потоков задано параметром. Потоки генерируют случайным образом : нечетный поток -согласную букву, четный поток -гласную.
 Последний по номеру поток добавляет к букве пробел (или точку, если это последнее слово).
 Потоки , генерируя буквы, по очереди выводят их на консоль (вместе с уже сгенеренными буквами и словами).
 Слово состоит из количества букв равного заданному количеству потоков.
 Работа потоков заканчивается после того, как число, заданное 2-ым параметром, будет равно числу выведенных слов.
 Использовать ограничения из задания 3.
 Выводить на дисплей результаты работы каждого потока.
 */
import java.util.Objects;
import java.util.Random;

class Char implements Runnable{
    private static String alphabetSogl = "Б В Г Д Ж З Й К Л М Н П Р С Т Ф Х Ц Ч Ш Щ";
    private static String alphabetGlasn = " А Е Ё И О У Ы Э Ю Я";
    protected static String[] sogl = alphabetSogl.split(" ");
    protected static String[] glas = alphabetGlasn.split(" ");
    private Random rnd = new Random();
    private String str = "";
    private int current;
    public Char(int c){
        this.current = c;
    }
    public void run(){
        if(current % 2 == 0){
            System.out.print(glas[rnd.nextInt(glas.length-1)]);
        }
        else{
            System.out.print(sogl[rnd.nextInt(sogl.length-1)]);
        }
    }
}
class CheckChar implements Runnable{
    private int current, last;
    public CheckChar(int i, int c){
        this.current = i;
        this.last = c;
    }
    public void run(){
        if(this.current == this.last){
            System.out.print("@");
        }
    }
}
class CheckWord implements Runnable{
    private int current, last;
    public CheckWord(int c, int l){
        this.current = c;
        this.last = l;
    }
    public void run(){
        if(this.current == this.last){
            System.out.print(".");
        }
    }
}
class Word implements Runnable{
    int c;
    private Object monitor = new Object();
    public Word(int count){
        this.c = count;
    }
    public void run(){
        for(int i = 0; i <= this.c; i++){
            Runnable wr = new Char(i);
            Runnable chk = new CheckChar(i, this.c);
            synchronized (this.monitor){
                new Thread(wr).start();
            }
            synchronized (this.monitor){
                new Thread(chk).start();
            }
        }
    }
}
class Words implements Runnable{
    private int last, chars;
    private Object monitor = new Object();

    public Words(int l, int ch){
        this.last = l;
        this.chars = ch;
    }
    public void run(){
        Runnable[] words = new Word[this.last];
        for(int i = 0; i < words.length; i++){
            words[i] = new Word(this.chars);
            Runnable checker = new CheckWord(i, this.last);
            synchronized (monitor){
                new Thread(words[i]).start();
            }
            synchronized (monitor){
                new Thread(checker).start();
            }
        }
    }
}

public class lab4n2{
    public static void main(String argv[]){
    }

}
