package org.lyf.diamond.core.utile;

import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: diamond
 * @description: 深复制
 * @author: GG-lyf
 * @create: 2022-03-28 20:45:54
 */
@SuppressWarnings("all")
public class CloneUtils {

  public static Data deepClone(Data o) {
    Data d = new Data();
    d.setKey(o.getKey());
    d.setName(o.getName());
    d.setValue(o.getValue());
    List<Field> h = new ArrayList<>();
    o.getFields().forEach(vo -> {
      h.add(vo.clone());
    });
    d.setFields(h);
    d.getFields().forEach(vo -> {vo.setValue("");});
    return d;
  }


  public static List<Field> deepCloneField(Data o) {
    Data data = deepClone( o);
    return data.getFields();
  }


  public static Data deepCloneByFalseCartesianProduct(Data o) {
    Data d = new Data();
    d.setKey(o.getKey());
    d.setName(o.getName());
    d.setValue(o.getValue());
    List<Field> h = new ArrayList<>();
    o.getFields().forEach(vo -> {
      h.add(vo.clone());
    });
    d.setFields(h);
    return d;
  }

}
