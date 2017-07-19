import java.io.*;
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
                                test.string2File(test.update1(test.file2String(file2.getAbsolutePath())), file2.getAbsolutePath());
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
            test.update1(Test.debugString);
        }
    }

    private String update1(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        Pattern p1 = Pattern.compile("<layout([\\S|\\s])+</layout>");
        Pattern p2 = Pattern.compile("\\<\\?xml(\\s)+version(\\s)*=(\\s)*\"(\\s)*1.0(\\s)*\"(\\s)+encoding(\\s)*=(\\s)*\"(\\S)+\"(\\s)*\\?\\>");
        Matcher m1 = p1.matcher(str);
        Matcher m2 = p2.matcher(stringBuilder);
        if (!m1.find()) {
            if (m2.find()) {
                stringBuilder.insert(m2.end(), "\n<layout>\n");
            } else {
                stringBuilder.insert(0, "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<layout>\n");

            }
            stringBuilder.insert(stringBuilder.length(), "\n</layout>");

        }
        return update1_2_pre(stringBuilder.toString());
    }

    private String update1_2_pre(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        Pattern p1 = Pattern.compile("xmlns:(\\S+)\\s*=\\s*\"http://schemas.android.com/apk/res-auto\"");
        Matcher m1 = p1.matcher(str);
        int findCnt = 0;
        int pos = 0;
        while (m1.find()) {
            if (findCnt++ == 0) {
                continue;
            }
            int startIndex = m1.start() + pos;
            int endIndex = m1.end() + pos;

            stringBuilder.replace(startIndex, endIndex, "");

            pos += 0 - m1.group().length();
        }
        return update1_2(stringBuilder.toString());
    }


    private String update1_2(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        Pattern p1 = Pattern.compile("xmlns:(\\S+)\\s*=\\s*\"http://schemas.android.com/apk/res-auto\"");
        Matcher m1 = p1.matcher(str);
        if (!m1.find()) {
            Pattern pattern = Pattern.compile("\\<\\?xml\\s+version\\s*=\\s*\"\\s*1.0\\s*\"\\s+encoding\\s*=\\s*\"\\S+\"\\s*\\?\\>\\s*(\\<\\S[^\\>]+\\s*)");
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                stringBuilder.insert(matcher.end(1), " xmlns:bind=\"http://schemas.android.com/apk/res-auto\"");
            } else {
                System.err.println("error");
            }
        } else {
            String name = m1.group(1);

            stringBuilder.replace(m1.start(1), m1.end(1), "bind");
            stringBuilder = new StringBuilder(replaceApp2Bind(stringBuilder.toString(), name));

        }
        return update2(stringBuilder.toString());
    }


    private String replaceApp2Bind(String str, String name) {
        StringBuilder stringBuilder = new StringBuilder(str);
        Pattern p1 = Pattern.compile("(" + name + "):\\S+\\s*=\\s*\"\\S+[^\"]\"");
        Matcher m1 = p1.matcher(str);
        int fefew = 0;
        while (m1.find()) {
            int startIndex = m1.start(1);
            int endIndex = m1.end(1);
            startIndex += fefew;
            endIndex += fefew;

            stringBuilder.replace(startIndex, endIndex, "bind");
            fefew += 4 - m1.group(1).length();
        }

        return stringBuilder.toString();
    }

    private String update2(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        Pattern p1 = Pattern.compile("(?<=android:layout_width=\"@dimen/[xy])(\\d)+(?=\")");
        Matcher m1 = p1.matcher(str);
        int offset = 0;
        int addedLength = 0;

        while (m1.find()) {

            int temp = Integer.valueOf(m1.group());
            String tempString = "\nbind:layout_width=\"@{" + temp + "}\"\n";

            offset = m1.end() + addedLength;
            stringBuilder.insert(offset + 1, tempString);
            addedLength += tempString.length();
        }

        return update3(stringBuilder.toString());
    }

    private String update3(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        Pattern p1 = Pattern.compile("(?<=android:layout_height=\"@dimen/[xy])(\\d)+(?=\")");
        Matcher m1 = p1.matcher(str);
        int addedLength = 0;
        int offset = 0;
        while (m1.find()) {

            int temp = Integer.valueOf(m1.group());
            String tempString = "\nbind:layout_height=\"@{" + temp + "}\"";
            offset = m1.end() + addedLength;
            stringBuilder.insert(offset + 1, tempString);
            addedLength += tempString.length();
        }
        return update(stringBuilder.toString());
    }

    private String update(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        //生成Pattern对象并且编译一个简单的正则表达式"Kelvin"
//        Pattern p = Pattern.compile("(?<=\")(\\s)*\\d(\\s)*[px|sp|dp](\\s)*(?=\")");
        Pattern p1 = Pattern.compile("(?<!(android:layout_height=\"|android:layout_width=\"))@dimen/[xy](\\d+)");
        Pattern p2 = Pattern.compile("(?<!(android:layout_height=\"|android:layout_width=\"))@dimen/[xy](\\d)+");
//用Pattern类的matcher()方法生成一个Matcher对象
        Matcher m1 = p1.matcher(str);
        while (m1.find()) {

            String m1Group = m1.group(2);
            int temp = Integer.valueOf(m1Group);
            Matcher m2 = p2.matcher(stringBuilder);
            if (m2.find()) {

                stringBuilder.replace(m2.start(), m2.end(), "@{" + temp + "}");
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

    public static final String debugString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<layout>\n" +
            "\n" +
            "    <LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "        android:id=\"@+id/text_info\"\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:background=\"@android:color/transparent\"\n" +
            "        android:orientation=\"vertical\">\n" +
            "\n" +
            "        <TextView\n" +
            "            android:layout_width=\"wrap_content\"\n" +
            "            android:layout_height=\"wrap_content\"\n" +
            "            android:layout_marginLeft=\"@dimen/x35\"\n" +
            "            android:layout_marginTop=\"@dimen/y10\"\n" +
            "            android:background=\"@android:color/transparent\"\n" +
            "            android:text=\"@string/rules_lz_1\"\n" +
            "            android:textColor=\"@color/btn_text\"\n" +
            "            android:textSize=\"@dimen/x11\" />\n" +
            "\n" +
            "        <TextView\n" +
            "            android:layout_width=\"wrap_content\"\n" +
            "            android:layout_height=\"wrap_content\"\n" +
            "            android:layout_marginLeft=\"@dimen/x35\"\n" +
            "            android:background=\"@android:color/transparent\"\n" +
            "            android:text=\"@string/rules_lz_2\"\n" +
            "            android:textColor=\"@color/btn_text\"\n" +
            "            android:textSize=\"@dimen/x11\" />\n" +
            "\n" +
            "\n" +
            "    </LinearLayout>\n" +
            "</layout>";
}