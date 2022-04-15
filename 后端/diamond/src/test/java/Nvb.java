import org.lyf.diamond.core.entity.data.Field;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-03 20:14:42
 */
@SuppressWarnings("all")
public class Nvb {
  public static void main(String[] args) {
    Field a = new Field("a", "aa", "int", "1");
    Field b = new Field("b", "ss", "varchar", "2");
    Field c = new Field("c", "dd", "int", "1");
    Field d = new Field("d", "ff", "varchar", "1");
    Field e = new Field("e", "aa", "int", "4");
    Field f = new Field("f", "dd", "double", "3");
    Field g = new Field("f", "aa", "varchar", "2");
    Field h = new Field("f", "dd", "double", "1");
    Field i = new Field("a", "aa", "varchar", "2");
    Field j = new Field("d", "dd", "double", "1");
    Field k = new Field("f", "dd", "double", "3");
    Field l = new Field("g", "ff", "double", "2");
    Field m = new Field("s", "aa", "int", "2");
    Field n = new Field("s", "aa", "varchar", "1");
    Field o = new Field("s", "ff", "int", "3");
    Field p = new Field("a", "aa", "varchar", "1");
    Field q = new Field("v", "ss", "int", "3");
    Field r = new Field("v", "ff", "varchar", "2");
    Field s = new Field("v", "ss", "varchar", "2");
    Field t = new Field("v", "ff", "int", "3");
    Field u = new Field("v", "dd", "varchar", "4");
    Field v = new Field("b", "ss", "double", "4");
    Field w = new Field("b", "dd", "int", "2");
    Field x = new Field("b", "ss", "varchar", "4");
    Field y = new Field("b", "dd", "double", "4");
    Field z = new Field("b", "ff", "double", "3");
    List<Field> sa = new ArrayList<>();
    sa.add(a);
    sa.add(b);
    sa.add(c);
    sa.add(d);
    sa.add(e);
    sa.add(f);
    sa.add(g);
    sa.add(h);
    sa.add(i);
    sa.add(j);
    sa.add(k);
    sa.add(l);
    sa.add(m);
    sa.add(n);
    sa.add(o);
    sa.add(p);
    sa.add(q);
    sa.add(r);
    sa.add(s);
    sa.add(t);
    sa.add(u);
    sa.add(v);
    sa.add(w);
    sa.add(x);
    sa.add(y);
    sa.add(z);

    Map<String, Map<String, List<Field>>> collect1 = sa.stream().collect(Collectors.groupingBy(Field::getConcern, Collectors.groupingBy(Field::getIs_key)));
    for (String s1 : collect1.keySet()) {
      Map<String, List<Field>> stringListMap = collect1.get(s1);
      System.out.println(stringListMap);
    }

//    List<List<Field>> fs = new ArrayList<>();
//    for (int i1 = 0; i1 < sa.size(); i1++) {
//      for (int i2 = i1; i2 < sa.size(); i2++) {
//        if (sa.get(i1).getConcern().equals(sa.get(i2).getConcern())&&sa.get(i1).getIs_key().equals(sa.get(i2).getIs_key())){
//
//        }
//      }
//    }


  }
}
