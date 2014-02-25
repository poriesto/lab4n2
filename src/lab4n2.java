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
import java.util.Random;

class Char implements Runnable{
    final private static String alphabetSogl = "Б В Г Д Ж З Й К Л М Н П Р С Т Ф Х Ц Ч Ш Щ";
    final private static String alphabetGlasn = " А Е Ё И О У Ы Э Ю Я";
    final private String[] sogl = alphabetSogl.split(" ");
    final private String[] glas = alphabetGlasn.split(" ");
    final private Random rnd = new Random();
    final private int current;
    public Char(int c){
        this.current = c;
    }
    public void run(){
        if(current % 2 == 0){
            System.out.print(glas[rnd.nextInt(glas.length-1)].toLowerCase());
        }
        else{
            System.out.print(sogl[rnd.nextInt(sogl.length-1)].toLowerCase());
        }
    }
}
class CheckChar implements Runnable{
    final private int current, last;
    public CheckChar(int current, int last){
        this.current = current;
        this.last = last;
    }
    public void run(){
        if(this.current == this.last){
            System.out.print("@");
        }
    }
}
class Word implements Runnable{
    final private int count;
    final private Object monitor = new Object();
    public Word(int count){
        this.count = count;
    }
    public void run(){
        for(int i = 0; i <= this.count; i++){
            Runnable wr = new Char(i);
            Runnable chk = new CheckChar(i, this.count);
            synchronized (this.monitor){
                new Thread(wr).start();
            }
            synchronized (this.monitor){
                new Thread(chk).start();
            }
        }
    }
}
class CheckWords implements Runnable{
    final private int current, last;
    public CheckWords(int current, int last){
        this.current = current;
        this.last = last;
    }
    public void run(){
        if(this.current == this.last - 1){
            System.out.println("." + "\n");
        }
    }
}
class Words implements Runnable{
    final private int count, char_count;
    final private Object monitor = new Object();

    public Words(int count, int char_count){
        this.count = count;
        this.char_count = char_count;
    }
    public void run(){
        for(int i = 0; i < this.count; i++){
            Runnable wrd = new Word(this.char_count);
            Runnable chk = new CheckWords(i, this.char_count - 1);
            synchronized (this.monitor){
                new Thread(wrd).start();
            }
            synchronized (this.monitor){
                new Thread(chk).start();
            }
        }
    }
}

public class lab4n2{
    private static void rus(int count, int chars){
        Runnable words = new Words(count, chars);
        new Thread(words).start();
    }
    public static void main(String argv[]){
        rus(Integer.parseInt(argv[0]), Integer.parseInt(argv[1]));
    }
}
