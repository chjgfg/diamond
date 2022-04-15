import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-03-31 14:04:11
 */
@SuppressWarnings("all")
public class Tewt {

  public static void main(String[] args) {
    String s = "dw.uu_id = uu.id;";
    check(s);
  }

  private static boolean check(String s) {
    Pattern other = Pattern.compile("([^\\s]+\\s+\\=+\\s+[^\\s]+(\\;))");
    Matcher mcd = other.matcher(s);
    return mcd.find();
  }
}
