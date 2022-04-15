import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-03-20 16:06:14
 */
public class test {

  public static int[] s(int[] a, int i, int j) {
    int k = 0;
    if (a[i] > a[j]) {
      k = a[i];
      a[i] = a[j];
      a[j] = k;
    }
    return a;
  }

  public static int f(int[] a, int l, int r) {
    int m = a[l];
    int c = l;
    int d = r;
    while (c < d) {
      while (c < d && a[d] > m) {
        d--;
      }
      while (c < d && a[c] <= m) {
        c++;
      }
      s(a, c, d);
    }
    s(a, l, d);
    return d;
  }

  public static int[] r(int[] a, int i, int j) {
    if (i >= j) {
      return new int[0];
    }
    int l = f(a, i, j);
    r(a, i, l - 1);
    r(a, l + 1, j);
    return a;
  }

  public static int[] sortedSquares(int[] nums) {
    int[] m = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      m[i] = nums[i] * nums[i];
    }
    for (int i = 0; i < nums.length; i++) {
      System.out.println(m[i]);
    }
    r(m, 0, m.length - 1);
    for (int i = 0; i < nums.length; i++) {
      System.out.println(m[i]);
    }
    return m;
  }

  public static void main(String[] args) {
    int[] nums = {-1, 0, 1, 2, -1, -4};
    int k = 0;
    fourSum(nums, k);

  }


  public static List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> lists = new ArrayList<>();
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;//跳过重复值
      }
      for (int j = i + 1; j < nums.length; j++) {
        if (j > i + 1 && nums[j] == nums[j - 1]) {
          continue;//跳过重复值
        }
        int left = j + 1;
        int right = nums.length - 1;
        while (right > left) {
          int sum = nums[i] + nums[j] + nums[left] + nums[right];
          if (sum > target) {
            right--;
          } else if (sum < target) {
            left++;
          } else {
            lists.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
            while (right > left && nums[right] == nums[right - 1]) {
              right--;//继续跳过重复值
            }
            while (right > left && nums[left] == nums[left + 1]) {
              left++;//继续跳过重复值
            }
            right--;
            left++;
          }
        }
      }
    }
    return lists;
  }


  public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode listNode = new ListNode(-1);
    listNode.next = head;
    ListNode left = listNode, right = listNode;
    while (n-- > 0) {
      right = head.next;//先走
    }
    ListNode pre = null;
    while (right != null) {
      pre = left;//记住上一个
      left = left.next;
      right = right.next;
    }
    pre.next = left.next;
    left.next = null;
    return head;
  }


  public static void reverseString(char[] s) {
    char l = ' ';
    for (int i = 0; i < s.length / 2; i++) {
      l = s[i];
      s[i] = s[s.length - 1 - i];
      s[s.length - 1 - i] = l;
    }
    System.out.println(s);

  }


  public static int reverse(int x) {
    if (x == 0) {
      return 0;
    } else {
      String s = x + "";
      if (s.startsWith("-")) {
        String[] split = s.split("-");
        return -f(split[1]);
      } else {
        return f(s);
      }
    }
  }

  public static int f(String k) {
    long l = Long.parseLong(new StringBuilder(k).reverse().toString());
    System.out.println(l);
    return (l > Integer.MAX_VALUE) || (l < Integer.MIN_VALUE) ? 0 : (int) l;
  }


}
