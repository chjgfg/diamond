import org.junit.Test;
import org.junit.runner.RunWith;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.utile.TimeUtils;
import org.lyf.diamond.inter.InterApplication;
import org.lyf.diamond.inter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-03-14 18:39:29
 */
@SuppressWarnings("all")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterApplication.class)
public class Test2 {
  @Autowired
  private DataService ds;

  @Test
  public void main() {
    DataService ds = new DataService();
//    ds.select("select * from dw left join uu on dw.uu_id = uu.id , inner join uuk on uuk.uu_id = uu.id");
    ds.select("select * from dw left join uu on dw.uu_id = uu.id , left join uuk on uuk.uu_id = uu.id where uu.id <= 2");
  }



  @Test
  public void test2() {
    Field f = new Field();
    f.setValue(TimeUtils.timestampToDate("1615342283000"));
    System.out.println(f);

  }

}
