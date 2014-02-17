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
class wrd{
    protected Object monitor = new Object();
    protected Random rnd = new Random();
    private String alphabetSogl = "Б В Г Д Ж З Й К Л М Н П Р С Т Ф Х Ц Ч Ш Щ";
    private String alphabetGlasn = " А Е Ё И О У Ы Э Ю Я";
    protected String[] glas, sogl;
    public String str = " ", final_str = "";
    private int words_count, word_leghnt;
    Thread[] threads;

    public void init(int words, int lengt_word){
        words_count = words;
        word_leghnt = lengt_word;
        threads = new Thread[lengt_word];
        sogl = alphabetSogl.split(" ");
        glas = alphabetGlasn.split(" ");
        for(int i = 0; i < glas.length; i ++) glas[i].toLowerCase();
        for(int j = 0; j < sogl.length; j ++) sogl[j].toLowerCase();

    }
    public void start(){
        for(int i = 0; i <= words_count -1; i++){
            System.out.println(i + "\n");
        for(int j = 0; j <= threads.length-2; j++){
                threads[j] = new Thread(){
                    public void run(){
                        synchronized (monitor){
                            if((this.getId() + rnd.nextInt() - rnd.nextInt(sogl.length)) % 2 == 0){
                                str += glas[rnd.nextInt(glas.length)];
                                System.out.println(this.getName() + " ID = " + this.getId() + " g "+ str);
                                monitor.notify();
                            }
                            else{
                                str += sogl[rnd.nextInt(sogl.length)];
                                System.out.println(this.getName() + " ID = " + this.getId() + " s " + str);
                                monitor.notify();
                            }
                        }
                    }
                };
                threads[j+1] = new Thread() {
                    public void run() {
                        synchronized (monitor){
                            System.out.println(getName() + " ID = " + this.getId() +" check " + str);
                            monitor.notify();
                        }
                    }
                };
                 System.out.println(j + "\n");
                threads[j].start();
                threads[j+1].start();
                threads[j].interrupt();
                threads[j+1].interrupt();
            }
            str += " ";
     }
        str+= ".";
    }
}

public class lab4n2 {

    public static void main(String argv[]) throws InterruptedException{
        wrd words = new wrd();
        words.init(Integer.parseInt(argv[0]), Integer.parseInt(argv[1]));
        words.start();
        System.out.println("Result of program:\n" + words.str.toLowerCase());
    }

}
