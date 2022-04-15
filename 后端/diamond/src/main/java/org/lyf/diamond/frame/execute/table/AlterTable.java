package org.lyf.diamond.frame.execute.table;


import org.lyf.diamond.frame.execute.Base;

public class AlterTable implements Base<AlterTable> {
  //alter table test add c1 char(1),add c2 char(1);		--正确
  //alter table test drop c1,drop c2;
  //alter table test change c1 c3 char(1),change c2 c4 char(1);		--正确
  //alter table people modify s int(10) , modify w int(10) , modify d int(10);
//alter table people modify f int(10) ,add dt char(1);
  private String name = "alter table {0}";

  @Override
  public AlterTable build(String name) {
    this.name = this.name.replace("{0}", name);
    return this;
  }

  @Override
  public AlterTable of() {
    int i = this.name.lastIndexOf(" ,");
    this.name = this.name.substring(0, i) + " ;";
    return this;
  }

  public AlterTable alter(String option, String list) {
    this.name = this.name + " " + option + " " + list + " ,";
    return this;
  }

  @Override
  public String toString() {
    return "AlterTable{" +
        "name='" + name + '\'' +
        '}';
  }


}
