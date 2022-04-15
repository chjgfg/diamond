<template>
  <v-card>
    <v-card-title class="indigo white--text headline">
      User Directory
    </v-card-title>
    <v-row class="pa-4" justify="space-between">
      <v-col cols="10">
        <!-- :load-children="fetchUsers"
        :open.sync="open"
        :active.sync="active"

        -->
        <v-treeview :items="items" activatable item-key="id" open-on-click>
          <!-- <template v-slot:prepend="{ item, active }">
              <v-icon v-if="!item.children">mdi-account</v-icon>
          </template> -->
          <template v-slot:prepend="{ item, open }">
            <!-- false是全选 -->
            <v-icon v-if="item.selected === false" @click.stop="selectFn(item)">
              {{ 'mdi-shield-outline' }}
            </v-icon>
            <!-- 2是半选 -->
            <v-icon v-else-if="item.selected === '2'">
              {{ 'mdi-shield-half-full' }}
            </v-icon>
            <!-- true是不选 -->
            <v-icon v-else @click.stop="selectFn(item)">
              {{ 'mdi-shield-check' }}
            </v-icon>
            <!-- <v-icon v-if="!item.file">
            {{ open ? 'mdi-folder-open' : 'mdi-folder' }}
            </v-icon>
            <v-icon v-else>
            {{ files[item.file] }}
            </v-icon> -->
          </template>
        </v-treeview>
      </v-col>
      <!-- 加一个高度为0，宽度为100%的有border:1px solid rgba(0, 0, 0, 0.12)的div vertical改变纵向 -->
      <v-divider vertical/>
    </v-row>
  </v-card>
</template>

<script>


  //递归循环遍历
  //ob为一个对象 返回的也是一个对象或数组
  //当bools为1 返回父节点 和 子节点与自身 两者组合的 数组 (默认)
  //当bools为2 返回父节点 对象类型
  //当bools为3 返回子节点与自身 对象类型
  function getLeafLoop(ob, id, bools = 1) {
    var resultOb = [];//先定义个返回值 为空数组
    for (let j = 0; j < ob['children'].length; j++) {
      if (ob['children'][j].id !== id) {
        resultOb = getLeafLoop(ob['children'][j], id);
        //必须要有中断的解决办法，不然整个循环会继续下去，结果会变成underfind
        if (resultOb) {
          break
        }
      } else {
        if (bools === 1) {
          //返回父节点 子节点和自身
          resultOb = [ob, ob['children'][j]];
        } else if (bools === 2) {
          //返回父节点
          resultOb = ob;
        } else {
          //返回子节点和自身
          resultOb = ob['children'][j];
        }
        break;
      }
    }
    //因为返回结果根据bools有三种情况，所以要排除掉 空数组的返回
    //当没有合适的匹配时，返回的是 underfind，即没有返回
    if ((!Array.isArray(resultOb)) || resultOb.length > 0) {
      return resultOb;
    }
  }

  //itemsArr为一个数组, 默认情况下，传入的是所有渲染的数据 items
  //返回一对象或者数组，
  //找到与id值一致的节点
  //当bools为1 返回父节点 和 子节点与自身 两者组合的 数组 (默认)
  //当bools为2 返回父节点 对象类型
  //当bools为3 返回子节点与自身 对象类型
  function getLeaf(itemsArr, id, bools = 1) {
    var returnOb = [];
    for (let i = 0; i < itemsArr.length; i++) {
      if (itemsArr[i].id !== id) {
        returnOb = getLeafLoop(itemsArr[i], id, bools)
        if (returnOb) {
          //如果返回的不是underfind，则停止循环
          break
        }
      } else {
        //id为根节点时，返回自身
        if (bools === 1) {
          //特殊情况，如果点击的节点，是根节点，则 父节点和 子节点与自身 都是自己
          returnOb = [itemsArr[i], itemsArr[i]];
        } else {
          returnOb = itemsArr[i];
        }
        break;//结束循环
      }
    }
    //因为返回结果根据bools有三种情况，所以要排除掉 空数组的返回
    //当没有合适的匹配时，返回的是 underfind，即没有返回
    if ((!Array.isArray(returnOb)) || returnOb.length > 0) {
      return returnOb;
    }

  }

  //修改节点下的所有selected 的状态;
  //属性的selected 为 判断标准
  //arr为一个数组
  function changeSelect(arr, bools) {
    for (let i = 0; i < arr.length; i++) {
      arr[i].selected = bools;
      if (arr[i].children.length > 0) {
        changeSelect(arr[i].children, bools)
      }
    }
  }

  //返回父节点下的子节点的状态
  //返回一个二维数组，第一项为选中的项， 第二项为没有被选中的项
  //arr为一个数组
  function leafSelectStatus(arr) {
    var seleTrue = [];
    var seleFalse = [];
    for (let i = 0; i < arr.length; i++) {
      if (arr[i].selected) {//将 true 和 2 的状态 归为一组（如果有需要可以分开）
        seleTrue.push(arr[i].id)//这里设置返回的选中的节点 数据内容
      } else {
        seleFalse.push(arr[i].id)//这里设置返回的未选中的节点 数据内容
      }
      if (arr[i].children.length > 0) {
        //递归，继续查找子节点下的子节点
        var resultOb = leafSelectStatus(arr[i].children);
        //拼接上 子节点下的子节点 返回的数据
        seleTrue = seleTrue.concat(resultOb[0]);
        seleFalse = seleFalse.concat(resultOb[1]);
      }
    }
    return [seleTrue, seleFalse]//返回一个数组
  }

  //修改爷爷辈的节点selected
  function changeGraFatherStatus(itemArr, id) {
    var resultob = getLeaf(itemArr, id);
    var sonStatus = leafSelectStatus(resultob[0].children);
    if (sonStatus[0].length > 0 && sonStatus[1].length > 0) {
      //此时parent变为 半选状态
      resultob[0].selected = 2;
      console.log("有未选中和选中的");
      //这时候需要继续向根节点 做进一步修改
      if (resultob[0].id !== resultob[1].id) {
        // this.selectFn(parent, true);
        changeGraFatherStatus(itemArr, resultob[0].id);
      }
    } else if (sonStatus[0].length === 0 && sonStatus[1].length > 0) {
      //此时parent应该变成未选中
      resultob[0].selected = false;
      console.log("都是未选中的")
      //这时候需要继续向根节点 做进一步修改
      if (resultob[0].id !== resultob[1].id) {
        // this.selectFn(parent, true);
        changeGraFatherStatus(itemArr, resultob[0].id);
      }
    } else if (sonStatus[0].length > 0 && sonStatus[1].length === 0) {
      //此时parent应该变成选中
      resultob[0].selected = true;
      console.log("都是选中的");
      //这时候需要继续向根节点 做进一步修改
      //如果该节点就是根节点，不在进行循环
      if (resultob[0].id !== resultob[1].id) {
        changeGraFatherStatus(itemArr, resultob[0].id);
      }
    } else {
      //没有子节点 并且自身还是根节点的时候会出现
      console.log("我就是孤独的根号3");
    }

  }

  export default {
    data: () => ({
      items: [//模拟数据，这一部分数据，一般是url请求过来的
        {
          id: '1',
          name: '第一层01',
          selected: false,
          children: [
            {
              id: '11',
              selected: false,
              name: '第二层0101',
              children: [
                {
                  id: '11',
                  selected: false,
                  name: '第二层010101',
                  children: [],
                }
              ],
            }
          ],
        },
        {
          id: '2',
          name: '第一层02',
          selected: false,
          children: [
            {
              id: '10',
              selected: false,
              name: '第二层0201',
              children: [],
            },
          ],
        },
        {
          id: '3',
          selected: false,
          name: '第一层03',
          children: [
            {
              id: '4',
              selected: false,
              name: '第二层0301',
              children: [
                {
                  id: '5',
                  selected: false,
                  name: '第三层030101',
                  children: [],
                },
                {
                  id: '9',
                  selected: false,
                  name: '第三层030102',
                  children: [],
                },
              ],
            },
            {
              id: '6',
              selected: false,
              name: '第二层0302',
              children: [],
            },
            {
              id: '7',
              selected: false,
              name: '第二层0303',
              children: [],
            },
          ],
        },
        {
          id: '8',
          selected: false,
          name: '第一层04',
          children: [],
        },
      ],
      selectSet: new Set(),//定义一个存储 选中状态的 节点容器
    }),
    watch: {
      items: {
        deep: true,
        handler: function (value) {
          for (var i = 0; i < value.length; i++) {
            if (value[i].selected) {//将半选和全选的节点 添加数据到set中
              this.selectSet.add(value[i].id)
            } else {
              this.selectSet.delete(value[i].id)//将数据从set中删除
            }
          }
        }
      }
    },
    methods: {
      //点击节点的时候 会触发该函数
      selectFn(item) {
        var leafArr = [];
        var parent = "";
        var son = "";
        //改变自身的selected， 该事件是由点击事件造成，而不是循环事件
        item.selected ? item.selected = false : item.selected = true;

        //获取父节点（包含父节点的对象）  和 子节点与自身（自身的对象） 的数组
        leafArr = getLeaf(this.items, item.id);

        parent = leafArr[0];
        //其实此处的 son（子节点与自身）和 selectFn(item)的item是一个对象
        son = leafArr[1];

        //修改子节点的所有select 将子节点中的 selected 都变为item.selected状态
        changeSelect(son.children, item.selected);

        //获取父节点下的所有children中 选中、半选中状态 和 未选中 的节点数组
        var sonStatus = leafSelectStatus(parent.children);

        //判断sonStatus中 选中、半选中状态 和 未选中 的节点的数量
        if (sonStatus[0].length > 0 && sonStatus[1].length > 0) {
          //此时parent变为 半选状态
          parent.selected = 2;
          console.log("有未选中和选中的");
          //这时候需要继续向根节点 做进一步修改
          if (parent.id !== son.id) {
            // this.selectFn(parent, true);
            changeGraFatherStatus(this.items, parent.id);
          }
        } else if (sonStatus[0].length === 0 && sonStatus[1].length > 0) {
          //此时parent应该变成未选中
          parent.selected = false;
          console.log("都是未选中的");
          //这时候需要继续向根节点 做进一步修改
          if (parent.id !== son.id) {
            // this.selectFn(parent, true);
            changeGraFatherStatus(this.items, parent.id);
          }
        } else if (sonStatus[0].length > 0 && sonStatus[1].length === 0) {
          //此时parent应该变成选中
          parent.selected = true;
          console.log("都是选中的");
          //这时候需要继续向根节点 做进一步修改
          if (parent.id !== son.id) {
            changeGraFatherStatus(this.items, parent.id);
          }
        } else {
          //没有子节点 并且自身还是根节点的时候会出现
          console.log("我就是孤独的根号3");
        }

      }
    }
  }
</script>

<style>

</style>
