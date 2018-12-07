import com.shoppingmall.servicefeign.ServiceFeignApplication;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

public class Md5Salt {
    /*MD5加密*/
    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { '0','v','9','J','4','c','c','E','d','v', 'R','3','h','7','I','7' };// 用来将字节转换成16进制表示的字符
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
            // 进制需要 32 个字符
            int k = 0;// 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
                // 进制字符的转换
                byte byte0 = tmp[i];// 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
                // 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换
            }
            s = new String(str);// 换后的结果转换为字符串
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println(e);
        }
        return s;
    }
    /*Base64编码*/
    public static String encodeBase64(byte[]input) throws Exception{
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod= clazz.getMethod("encode", byte[].class);
        mainMethod.setAccessible(true);
        Object retObj=mainMethod.invoke(null, new Object[]{input});
        return (String)retObj;
    }

    /*Test*/
    public static void main(String[] args) throws Exception{
        /*混淆MD5加密 前字符串+用户名+密码+后字符串*/
        String name = "abc";
        String keya="@#$&*1029we";
        String keyb="})*vbks!1214";
        String password = "a1234563333434aaa";
        String str =keya+name+password+keyb;
        /*为了更加安全，在混洗后的MD5加密前后再添加相应的混淆字符串将32位字符变成52位*/
        String test="$a&m|:p"+ Md5Salt.getMD5(str.getBytes())+"4r0gf~!*^@j86";
        System.out.println(str);
        System.out.println("混淆后："+test);
        System.out.println("未混淆的password MD5:"+ Md5Salt.getMD5(password.getBytes()));
        /*混淆加密后，再进行base64加密得到最终加密结果*/
        System.out.println("base64编码后:"+ Md5Salt.encodeBase64(test.getBytes()));
        System.out.println("base64编码后，再对其相关字母数字进行替换后的结果："+ Md5Salt.encodeBase64(test.getBytes()).replaceAll("X","8").replaceAll("=","k").replaceAll("R","i").replaceAll("2","e").replaceAll("m","c").replaceAll("J","Es"));
    }
}
