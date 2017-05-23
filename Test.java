import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hanrong on 2017/5/4.
 */
public class Test {
    private static final boolean debug = false;

    public static void main(String[] args) {
        Test test = new Test();
        if (!debug) {
            File file = new File("C:\\Users\\Hanrong\\AndroidStudioProjects\\Pieces\\layout");
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files.length == 0) {
                    System.out.println("文件夹是空的!");
                    return;
                } else {
                    int i = 0;
                    for (File file2 : files) {
                        if (file2.isDirectory()) {
                            System.out.println("文件夹:" + file2.getAbsolutePath());
                        } else {
                            System.out.println("文件:" + file2.getAbsolutePath());

                            try {
                                test.string2File(test.update(test.file2String(file2.getAbsolutePath())), file2.getAbsolutePath());
                            } catch (IOException e) {
                                System.err.println(file2.getAbsolutePath() + "异常");
                            }
                        }
                    }
                }
            } else {
                System.out.println("文件不存在!");
            }
        } else {
            test.update("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<ImageView xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                    "    android:layout_width=\"1px\"\n" +
                    "    android:layout_height=\"0px\"\n" +
                    "    android:layout_centerVertical=\"true\"\n" +
                    "    android:layout_marginLeft=\"20px\"\n" +
                    "    android:adjustViewBounds=\"true\"\n" +
                    "    android:background=\"@android:color/transparent\" />");
        }
    }

    private String update(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        //生成Pattern对象并且编译一个简单的正则表达式"Kelvin"
//        Pattern p = Pattern.compile("(?<=\")(\\s)*\\d(\\s)*[px|sp|dp](\\s)*(?=\")");
        Pattern p1 = Pattern.compile("(?<=\")(\\s)*(([1-9]\\d+)|[2-9])(?=(\\s)*(px|dp|sp)(\\s)*\")");
        Pattern p2 = Pattern.compile("(?<=\")(\\s)*(([1-9]\\d+)|[2-9])(\\s)*(px|dp|sp)(?=(\\s)*\")");
//用Pattern类的matcher()方法生成一个Matcher对象
        Matcher m1 = p1.matcher(str);
        while (m1.find()) {
            int temp = Integer.valueOf(m1.group());
            Matcher m2 = p2.matcher(stringBuilder);
            if (m2.find()) {
                stringBuilder.replace(m2.start(), m2.end(), "@dimen/x" + temp);
            }
        }
        if (debug) {
            System.out.println(stringBuilder);
        }
        return stringBuilder.toString();
    }

    private String file2String(String fileName) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));

        String content = "";
        StringBuilder sb = new StringBuilder();

        while (content != null) {
            content = bf.readLine();

            if (content == null) {
                break;
            }

            sb.append(content);
        }

        bf.close();
        return sb.toString();

    }

    public static File string2File(String fileContent, String path) {
        byte[] b = fileContent.getBytes();
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(path);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }
}
