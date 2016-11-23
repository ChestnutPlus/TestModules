package chestnut.utils;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2016年10月21日15:02:31
 *     desc  : Math相关工具类
 *     thanks To:
 *     dependent on:
 * </pre>
 */
public class MathUtils {

    private MathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final int OnlyCapital = 0;      //只有大写字母
    private static final int OnlyLowerCase = 1;    //只有小写字母
    private static final int Both = 2;             //包括大小写

    /**
     *      随机 生成区间 [m，n) 的整数 , 可以是负数
     * @param begin 起
     * @param end 始
     * @return 随机值
     */
    public static int randomInt(int begin ,int end ) {
        return (int)(Math.random() * ( end - begin + 1 )) + begin ;
    }

    /**
     *      传入一个 List，随机返回 List 里面的数 并返回
     * @param list
     * @return
     */
    public static int randomInt(ArrayList<Integer>  list) {
        return list.get(randomInt(0,list.size()-1));
    }

    /**
     *      输入 begin 和 end 和 间隔 space，随机返回这些数字。
     *      for example , begin = 2 , end = 9 , space = 2 ,
     *          so , return one of these numbers ' 2,4,6,8 '
     * @param begin
     * @param end
     * @param space
     * @return
     */
    public static int randomInt(int begin, int end , int space) {
        ArrayList<Integer> temp = new ArrayList<>();
        while (true) {
            temp.add(begin);
            begin+=space;
            if (begin >= end) {
                break;
            }
        }
        return randomInt(temp);
    }

    /**
     *      65 - 90 （十进制） 大写字母
     *      97 - 122 （十进制） 小写字母
     *      A + 32 = a
     *      随机返回 ； 小写，大写字母
     * @param type
     * @return char型的字母。
     */
    public static char randomCharLetter(int type) {
        switch (type) {
            case OnlyCapital : return (char) randomInt(65,90);
            case OnlyLowerCase : return (char) randomInt(97,122);
            case Both :
                int temp = randomInt(65,90);
                if ( temp % 2 == 0 ) {
                    return (char) temp ;
                }
                else {
                    return (char) (temp + 32) ;
                }
        }
        return 0;
    }

    /**
     *      随机返回 字符 数字 0 - 9
     *      ascll : 48 - 59 (十进制)
     *
     * @return
     */
    public static char randomCharNumber() {
        return (char) randomInt(48,59);
    }

//    public static String randomStr(int num) {
//
//    }
}
